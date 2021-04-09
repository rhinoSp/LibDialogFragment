package com.rhino.dialog.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import com.rhino.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>The utils of file<p>
 *
 * @author LuoLin
 * @since Create on 2018/9/27.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getName();

    /**
     * Whether has SD card.
     *
     * @return true had
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * Get sdcard path.
     *
     * @return sdcard path
     */
    public static String getSdcardPath() {
        if (hasSdcard()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    /**
     * Get external file dir.
     *
     * @return external file dir
     */
    public static String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * Get external file parent dir.
     *
     * @return external file parent dir
     */
    public static String getExternalFilesParentDir(Context context) {
        return context.getExternalFilesDir(null).getParent();
    }

    /**
     * Make directory.
     *
     * @param directoryArray the array of directory path, like /sdcard/Android/temp/
     * @return true success,  false failed
     */
    public static boolean makeDirectory(String... directoryArray) {
        boolean isSuccess = true;
        for (String d : directoryArray) {
            try {
                File dir = new File(d);
                if (!dir.exists()) {
                    isSuccess &= dir.mkdirs();
                }
            } catch (Exception e) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    /**
     * Whether exist this directory.
     *
     * @param directoryPath the path of directory
     * @return true exit,  false not exit
     */
    public static boolean isDirectoryExists(String directoryPath) {
        File directoryFile = new File(directoryPath);
        return directoryFile.isDirectory() && directoryFile.exists();
    }

    /**
     * Whether exist this file.
     *
     * @param filePath the path of file
     * @return true exit,  false not exit
     */
    public static boolean isFileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Delete directory.
     *
     * @param directoryFilePath the path of directory
     * @return true success,  false failed
     */
    public static boolean deleteDirectory(String directoryFilePath) {
        if (directoryFilePath == null) {
            return false;
        }
        boolean isSuccess = true;
        File directoryFile = new File(directoryFilePath);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            return false;
        }
        for (File file : directoryFile.listFiles()) {
            if (file.isFile()) {
                isSuccess &= file.delete();
            } else if (file.isDirectory()) {
                isSuccess &= deleteDirectory(file.getPath());
            }
        }
        isSuccess &= directoryFile.delete();
        return isSuccess;
    }

    /**
     * Delete file.
     *
     * @param filePath the path of file
     * @return true success,  false failed
     */
    public static boolean deleteFile(@Nullable String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Copy the directory.
     *
     * @param srcDirPath  the path of src directory
     * @param destDirPath the path of dest directory
     * @return true success，false failed
     */
    public static boolean copyDirectory(String srcDirPath, String destDirPath) {
        return copyDirectory(srcDirPath, destDirPath, true);
    }

    /**
     * Copy the directory.
     *
     * @param srcDirPath  the path of src directory
     * @param destDirPath the path of dest directory
     * @param isOverlay   overlay ?
     * @return true success，false failed
     */
    public static boolean copyDirectory(String srcDirPath, String destDirPath, boolean isOverlay) {
        File srcDir = new File(srcDirPath);
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        if (!destDirPath.endsWith(File.separator)) {
            destDirPath = destDirPath + File.separator;
        }
        File destDir = new File(destDirPath);
        if (destDir.exists()) {
            if (isOverlay) {
                deleteDirectory(destDirPath);
            } else {
                return true;
            }
        } else if (!destDir.mkdirs()) {
            return false;
        }
        boolean flag = true;
        File[] files = srcDir.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.isFile()) {
                flag = copyFile(file.getAbsolutePath(), destDirPath + file.getName(), isOverlay);
            } else if (file.isDirectory()) {
                flag = copyDirectory(file.getAbsolutePath(), destDirPath + file.getName(), isOverlay);
            }
            if (!flag) {
                break;
            }
        }
        return flag;
    }

    /**
     * Copy the file.
     *
     * @param srcFileName  the path of src file
     * @param destFileName the path of dest file
     * @return true success，false failed
     */
    public static boolean copyFile(String srcFileName, String destFileName) {
        return copyFile(srcFileName, destFileName, true);
    }

    /**
     * Copy the file.
     *
     * @param srcFilePath  the path of src file
     * @param destFilePath the path of dest file
     * @param isOverlay    overlay ?
     * @return true success，false failed
     */
    public static boolean copyFile(String srcFilePath, String destFilePath, boolean isOverlay) {
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isFile()) {
            return false;
        }
        File destFile = new File(destFilePath);
        if (destFile.exists()) {
            if (isOverlay) {
                if (!deleteFile(destFilePath)) {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                if (!destFile.getParentFile().mkdirs()) {
                    return false;
                }
            }
        }
        try (FileInputStream inStream = new FileInputStream(srcFile);
             FileOutputStream outStream = new FileOutputStream(destFile);
             FileChannel in = inStream.getChannel();
             FileChannel out = outStream.getChannel()) {
            FileDescriptor fd = outStream.getFD();
            in.transferTo(0, in.size(), out);
            fd.sync();
        } catch (IOException e) {
            LogUtils.e(TAG, e.toString());
        }
        return true;
    }

    /**
     * Copy the assert file.
     *
     * @param context           context
     * @param assertFileName    assertFileName
     * @param destDirectoryPath destDirectoryPath
     */
    public static boolean copyAssertFileToSdcard(Context context, String assertFileName, String destDirectoryPath, boolean overWrite) {
        return copyAssertFileToSdcard(context, assertFileName, assertFileName, destDirectoryPath, overWrite);
    }

    /**
     * Copy the assert file.
     *
     * @param context           context
     * @param assertFileName    assertFileName
     * @param destFileName      destFileName
     * @param destDirectoryPath destDirectoryPath
     */
    public static boolean copyAssertFileToSdcard(Context context, String assertFileName, String destFileName, String destDirectoryPath, boolean overWrite) {
        File e = new File(destDirectoryPath + "/" + destFileName);
        if (!overWrite && e.exists() && e.length() > 0L) {
            return true;
        }
        try (FileOutputStream fos = new FileOutputStream(e);
             InputStream inputStream = context.getResources().getAssets().open(assertFileName);) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            return true;
        } catch (IOException ex) {
            LogUtils.e(TAG, ex.toString());
        }
        return false;
    }

    /**
     * Read the file content.
     *
     * @param filePath the file path
     * @return the content
     */
    public static String readFile(String filePath) {
        byte[] bytes = readFileToByte(filePath);
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    /**
     * Read the file content.
     *
     * @param filePath the file path
     * @return byte[]
     */
    public static byte[] readFileToByte(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int length;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LogUtils.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * Read the file content from assets file.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the content
     */
    public static String readFileFromAssets(Context context, String fileName) {
        try {
            return new String(readModelBufferFromAssertToByte(context, fileName));
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * 读取assets目录下文件并返回byte[]数据
     *
     * @param context  上下文对象
     * @param fileName 文件名
     * @return 返回读取结果
     */
    public static byte[] readModelBufferFromAssertToByte(Context context, String fileName) {
        try (InputStream inputStream = context.getResources().getAssets().open(fileName);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int length;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return null;
    }


    /**
     * Write data to file.
     *
     * @param filePath the file path
     * @param data     the content
     * @return true write success
     */
    public static boolean writeFile(String filePath, String data) {
        return writeFile(filePath, data.getBytes());
    }


    /**
     * Write data to file.
     *
     * @param filePath the file path
     * @param b        the byte[]
     * @return true write success
     */
    public static boolean writeFile(String filePath, byte[] b) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(b);
        } catch (IOException e) {
            LogUtils.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    /**
     * file to base64
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        try (InputStream in = new FileInputStream(file)) {
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            LogUtils.e(TAG, e.toString());
        }
        return base64;
    }

    /**
     * base64 to file
     */
    public static void base64ToFile(String base64, String outPutFilePath) {
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        writeFile(outPutFilePath, bytes);
    }

    /**
     * Get the files int dir.
     *
     * @param dirPath        the path of dir
     * @param fileNameSuffix the suffix of file name, null will return all
     * @return List
     */
    @NonNull
    public static List<String> getDirFiles(String dirPath, @Nullable String fileNameSuffix) {
        List<String> filePaths = new ArrayList<>();
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    continue;
                }
                if (TextUtils.isEmpty(fileNameSuffix) || f.getName().toLowerCase().endsWith(fileNameSuffix)) {
                    filePaths.add(f.getAbsolutePath());
                }
            }
        }
        Collections.sort(filePaths, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s2 != null) {
                    return s2.compareTo(s1);
                }
                return 0;
            }
        });
        return filePaths;
    }

    /**
     * Get file name in url
     *
     * @param url file url
     * @return file name
     */
    public static String getFileName(String url) {
        if (url == null) {
            return null;
        }
        int index = url.lastIndexOf("/");
        return index >= 0 ? url.substring(index + 1) : url;
    }

}
