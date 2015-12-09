package com.heaven.heavenhelp.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by waylon on 15/12/3.
 */
public class ClipboardUtil {

    public static void copy(String content, Context context)
    {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        //cmb.setPrimaryClip("213");
        cmb.notify();

    }
    /**
     * 实现粘贴功能
     * add by waylon
     * @param context
     * @return
     */
    public static String paste(Context context)
    {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }
}
