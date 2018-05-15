package com.rhino.dialog.impl;

import com.rhino.dialog.base.BaseDialogFragment;

/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public interface IOnDialogListener<T extends BaseDialogFragment> {

    void onStart(T dialogFragment);

    void onResume(T dialogFragment);

    void onPause(T dialogFragment);

    void onStop(T dialogFragment);

    void onDismiss(T dialogFragment);

}
