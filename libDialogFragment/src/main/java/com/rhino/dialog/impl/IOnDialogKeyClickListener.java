package com.rhino.dialog.impl;

import androidx.fragment.app.DialogFragment;

/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public interface IOnDialogKeyClickListener<T extends DialogFragment> {
    void onClick(T dialogFragment);
}
