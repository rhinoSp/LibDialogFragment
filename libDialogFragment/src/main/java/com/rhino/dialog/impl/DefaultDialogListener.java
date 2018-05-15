package com.rhino.dialog.impl;

import android.util.Log;

import com.rhino.dialog.base.BaseDialogFragment;

/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class DefaultDialogListener implements IOnDialogListener {

    private static final String TAG = "DefaultDialogListener";

    @Override
    public void onStart(BaseDialogFragment dialogFragment) {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume(BaseDialogFragment dialogFragment) {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause(BaseDialogFragment dialogFragment) {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop(BaseDialogFragment dialogFragment) {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDismiss(BaseDialogFragment dialogFragment) {
        Log.d(TAG, "onDismiss");
    }

}
