package com.rhino.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.dialog.impl.DefaultDialogListener;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;
import com.rhino.dialog.picker.DatePickerDialogFragment;
import com.rhino.dialog.picker.SingleWheelPickerDialogFragment;
import com.rhino.dialog.utils.CopyboardUtils;
import com.rhino.dialog.utils.FileUtils;
import com.rhino.dialog.utils.TimeUtils;
import com.rhino.log.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author LuoLin
 * @since Create on 2021/4/8.
 **/
public class DialogFactory {

    private static Context context;

    private DialogFactory() {
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DialogFactory.context = context.getApplicationContext();
    }

    /**
     * 时间选择
     */
    public static DatePickerDialogFragment buildDatePickerDialogFragment(int style, long timestamp, IOnDialogKeyClickListener<DatePickerDialogFragment> onCancelClickListener, IOnDialogKeyClickListener<DatePickerDialogFragment> onOkClickListener) {
        DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
        dialogFragment.setTitle("请选择时间");
        dialogFragment.setCurrentYear(Integer.parseInt(TimeUtils.formatTime(timestamp, "yyyy")));
        dialogFragment.setCurrentMonth(Integer.parseInt(TimeUtils.formatTime(timestamp, "MM")));
        dialogFragment.setCurrentDay(Integer.parseInt(TimeUtils.formatTime(timestamp, "dd")));
        dialogFragment.setCurrentHour(Integer.parseInt(TimeUtils.formatTime(timestamp, "HH")));
        dialogFragment.setCurrentMinute(Integer.parseInt(TimeUtils.formatTime(timestamp, "mm")));
        dialogFragment.setCurrentSecond(Integer.parseInt(TimeUtils.formatTime(timestamp, "ss")));
        dialogFragment.setStyle(style);
        dialogFragment.setYearCount(60);
        dialogFragment.setNegativeKeyColor(getContext().getResources().getColor(R.color.color_nor_888888_pre_aaaaaa));
        dialogFragment.setPositiveKeyColor(getContext().getResources().getColor(R.color.color_nor_333333_pre_aaaaaa));
        dialogFragment.setNegativeKeyClickListener(onCancelClickListener);
        dialogFragment.setPositiveKeyClickListener(onOkClickListener);
        return dialogFragment;
    }

    /**
     * 单个滚轮选择
     */
    public static SingleWheelPickerDialogFragment buildSingleWheelPickerDialogFragment(String title, String[] values, String currentValue, String label, IOnDialogKeyClickListener<SingleWheelPickerDialogFragment> onPositiveKeyClickListener) {
        SingleWheelPickerDialogFragment dialogFragment = new SingleWheelPickerDialogFragment();
        dialogFragment.setTitle(title);
        dialogFragment.setValues(values);
        dialogFragment.setCurrentValue(currentValue);
        dialogFragment.setLabel(label);
        dialogFragment.setPositiveKeyClickListener(onPositiveKeyClickListener);
        return dialogFragment;
    }

    /**
     * 构建系统提示框
     */
    public static AlertDialog buildSysMessageDialog(Activity activity,
                                                    boolean cancelAble,
                                                    String message,
                                                    String positiveButtonText,
                                                    DialogInterface.OnClickListener positiveButtonClickListener) {
        return buildSysMessageDialog(activity, cancelAble, message, activity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, positiveButtonText, positiveButtonClickListener);
    }

    /**
     * 构建系统提示框
     */
    public static AlertDialog buildSysMessageDialog(Activity activity,
                                                    boolean cancelAble,
                                                    String message,
                                                    DialogInterface.OnClickListener positiveButtonClickListener) {
        return buildSysMessageDialog(activity, cancelAble, message, activity.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, activity.getString(android.R.string.ok), positiveButtonClickListener);
    }

    /**
     * 构建系统提示框
     */
    public static AlertDialog buildSysMessageDialog(Activity activity,
                                                    String message,
                                                    DialogInterface.OnClickListener positiveButtonClickListener) {
        return buildSysMessageDialog(activity, false, message, null, null, activity.getString(android.R.string.ok), positiveButtonClickListener);
    }

    /**
     * 构建系统提示框
     */
    public static AlertDialog buildSysMessageDialog(Activity activity,
                                                    boolean cancelAble,
                                                    String message,
                                                    String negativeButtonText,
                                                    DialogInterface.OnClickListener negativeButtonClickListener,
                                                    String positiveButtonText,
                                                    DialogInterface.OnClickListener positiveButtonClickListener) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(cancelAble)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveButtonClickListener)
                .setNegativeButton(negativeButtonText, negativeButtonClickListener)
                .create();
        return dialog;
    }

    /**
     * 构建自定义提示框
     */
    public static MsgDialogFragment buildCustomMessageDialog(boolean cancelAble,
                                                             String title,
                                                             String message,
                                                             String negativeButtonText,
                                                             IOnDialogKeyClickListener<MsgDialogFragment> negativeButtonClickListener,
                                                             String positiveButtonText,
                                                             IOnDialogKeyClickListener<MsgDialogFragment> positiveButtonClickListener) {
        MsgDialogFragment dialogFragment = new MsgDialogFragment();
        dialogFragment.setCancelable(cancelAble);
        dialogFragment.setTitle(title);
        dialogFragment.setText(message);
        dialogFragment.setNegativeKeyColor(getContext().getResources().getColorStateList(R.color.white));
        dialogFragment.setNegativeKeyText(negativeButtonText);
        dialogFragment.setNegativeKeyBackground(TextUtils.isEmpty(positiveButtonText)
                ? getContext().getResources().getDrawable(R.drawable.selector_rect_nor_solffff0f8e_pre_sol88ff0f8e_bottom_cor10)
                : getContext().getResources().getDrawable(R.drawable.selector_rect_nor_solffff0f8e_pre_sol88ff0f8e_leftbottom_cor10));
        dialogFragment.setNegativeKeyVisibility(TextUtils.isEmpty(negativeButtonText)
                ? View.GONE
                : View.VISIBLE);
        dialogFragment.setNegativeKeyClickListener(negativeButtonClickListener);
        dialogFragment.setPositiveKeyColor(getContext().getResources().getColorStateList(R.color.white));
        dialogFragment.setPositiveKeyText(positiveButtonText);
        dialogFragment.setPositiveKeyBackground(TextUtils.isEmpty(negativeButtonText)
                ? getContext().getResources().getDrawable(R.drawable.selector_rect_nor_solffff0f8e_pre_sol88ff0f8e_bottom_cor10)
                : getContext().getResources().getDrawable(R.drawable.selector_rect_nor_solffff0f8e_pre_sol88ff0f8e_rightbottom_cor20));
        dialogFragment.setPositiveKeyVisibility(TextUtils.isEmpty(positiveButtonText)
                ? View.GONE
                : View.VISIBLE);
        dialogFragment.setPositiveKeyClickListener(positiveButtonClickListener);
        return dialogFragment;
    }

    /**
     * 构建数字输入对话框
     */
    public static EditDialogFragment buildCustomNumberEditDialog(String title,
                                                                 String text,
                                                                 String hintText,
                                                                 String negativeButtonText,
                                                                 IOnDialogKeyClickListener<EditDialogFragment> negativeButtonClickListener,
                                                                 String positiveButtonText,
                                                                 IOnDialogKeyClickListener<EditDialogFragment> positiveButtonClickListener) {
        return buildCustomEditDialog(title, InputType.TYPE_CLASS_NUMBER, text, hintText, negativeButtonText, negativeButtonClickListener, positiveButtonText, positiveButtonClickListener);
    }

    /**
     * 构建文字输入对话框
     */
    public static EditDialogFragment buildCustomTextEditDialog(String title,
                                                               String text,
                                                               String hintText,
                                                               String negativeButtonText,
                                                               IOnDialogKeyClickListener<EditDialogFragment> negativeButtonClickListener,
                                                               String positiveButtonText,
                                                               IOnDialogKeyClickListener<EditDialogFragment> positiveButtonClickListener) {
        return buildCustomEditDialog(title, InputType.TYPE_CLASS_TEXT, text, hintText, negativeButtonText, negativeButtonClickListener, positiveButtonText, positiveButtonClickListener);
    }

    /**
     * 构建输入对话框
     */
    public static EditDialogFragment buildCustomEditDialog(String title,
                                                           int type,
                                                           String text,
                                                           String hintText,
                                                           String negativeButtonText,
                                                           IOnDialogKeyClickListener<EditDialogFragment> negativeButtonClickListener,
                                                           String positiveButtonText,
                                                           IOnDialogKeyClickListener<EditDialogFragment> positiveButtonClickListener) {
        EditDialogFragment dialogFragment = new EditDialogFragment();
        dialogFragment.setTitle(title);
        dialogFragment.setText(text);
        dialogFragment.setHintText(hintText);
        dialogFragment.setInputType(type);
        dialogFragment.setMaxLength(100);
        dialogFragment.setHintTextColor(0xffaaaaaa);
        dialogFragment.setEditTextBackground(getContext().getResources().getDrawable(R.drawable.shape_rect_sol_00000000_str1_black10_cor5));
        dialogFragment.setNegativeKeyColor(getContext().getResources().getColorStateList(R.color.color_nor_888888_pre_aaaaaa));
        dialogFragment.setNegativeKeyText(negativeButtonText);
        dialogFragment.setNegativeKeyClickListener(negativeButtonClickListener);
        dialogFragment.setPositiveKeyColor(getContext().getResources().getColorStateList(R.color.color_nor_themecolor_pre_themecolorlight));
        dialogFragment.setPositiveKeyText(positiveButtonText);
        dialogFragment.setPositiveKeyClickListener(positiveButtonClickListener);
        return dialogFragment;
    }

    /**
     * 构建底部菜单对话框
     */
    public static BottomSelectDialogFragment buildBottomSheetSelectDialog(String title,
                                                                          List<BottomSelectDialogFragment.Item> itemList,
                                                                          BottomSelectDialogFragment.IOnItemClickListener listener) {
        BottomSelectDialogFragment dialogFragment = new BottomSelectDialogFragment();
        dialogFragment.setTitleText(title);
        dialogFragment.setCancelable(true);
        dialogFragment.setIOnDialogListener(new DefaultDialogListener());
        dialogFragment.setItems(itemList);
        dialogFragment.setOnItemClickListener(listener);
        dialogFragment.setItemCancelText("取消");
        return dialogFragment;
    }


    /**
     * 显示文件列表
     */
    public static void showFileListDialog(Activity activity, String dirPath) {
        String[] logFileNameArr = null;
        final List<File> logFilesList = new ArrayList<>();
        try {
            File logDirFile = new File(dirPath);
            File[] logFiles = logDirFile.listFiles();
            for (File file : logFiles) {
                if (!TextUtils.isEmpty(file.getName())) {
                    logFilesList.add(file);
                }
            }
            Collections.sort(logFilesList, new Comparator<File>() {
                @Override
                public int compare(File t, File t1) {
                    return t1.getAbsolutePath().compareTo(t.getAbsolutePath());
                }
            });
            logFileNameArr = new String[logFilesList.size()];
            int index = 0;
            for (File file : logFilesList) {
                logFileNameArr[index] = file.getName();
                index++;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        if (logFileNameArr == null) {
            Toast.makeText(activity, "获取文件列表失败!", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder listDialog = new AlertDialog.Builder(activity);
        listDialog.setTitle("选择文件");
        listDialog.setItems(logFileNameArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showFileContentDialog(activity, logFilesList.get(which).getAbsolutePath());
            }
        });
        listDialog.setPositiveButton("关闭", null);
        listDialog.setNegativeButton("清空文件", (dialog1, which) -> {
            buildSysMessageDialog(activity, true, "即将删除所有文件，确认删除？", "删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    LogUtils.endCurrentLogFileWrite();
                    FileUtils.deleteDirectory(dirPath);
                }
            }).show();
        });
        listDialog.show();
    }

    /**
     * 显示文件详情
     */
    public static void showFileContentDialog(Activity activity,
                                             String filePath) {
        String logText = FileUtils.readFile(filePath);
        AlertDialog dialog = (new AlertDialog.Builder(activity))
                .setTitle("文件详情")
                .setMessage(logText)
                .setNegativeButton("复制路径", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            CopyboardUtils.copyTextToClipboard(activity, filePath);
                            Toast.makeText(activity, "复制到剪贴板", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(activity, "复制失败", Toast.LENGTH_SHORT).show();
                            LogUtils.e(e);
                        }
                    }
                })
                .setPositiveButton("关闭", null)
                .setNeutralButton("复制文本", (dialog1, which) -> {
                    try {
                        CopyboardUtils.copyTextToClipboard(activity, logText);
                        Toast.makeText(activity, "复制到剪贴板", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        LogUtils.e(e);
                        Toast.makeText(activity, "复制失败", Toast.LENGTH_SHORT).show();
                    }
                }).show();
        TextView textView = dialog.findViewById(android.R.id.message);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    }

}
