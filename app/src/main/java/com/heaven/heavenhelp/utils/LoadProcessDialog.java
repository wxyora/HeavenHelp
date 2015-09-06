package com.heaven.heavenhelp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * Created by Acer-002 on 2015/9/6.
 */
public  class LoadProcessDialog {


    public static Dialog showRoundProcessDialog(Context mContext, int layout)
    {
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH)
                {
                    return true;
                }
                return false;
            }
        };

        Dialog mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.setOnKeyListener(keyListener);
        mDialog.show();
        mDialog.setContentView(layout);
        return mDialog;
    }
}
