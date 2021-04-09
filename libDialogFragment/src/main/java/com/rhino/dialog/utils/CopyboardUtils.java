package com.rhino.dialog.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author LuoLin
 * @since Create on 2019/9/4.
 **/
public class CopyboardUtils {

    public static void copyTextToClipboard(Context context, String text) throws Exception {
        copyTextToClipboard(context, "copy", text);
    }

    public static void copyTextToClipboard(Context context, String label, String text) throws Exception {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

}
