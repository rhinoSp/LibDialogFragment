package com.rhino.dialog;


import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.rhino.dialog.base.BaseDialogFragment;


/**
 * <p>The custom popup menu dialog</p>
 *
 * @author LuoLin
 * @since Create on 2018/9/4.
 */
public class PopupMenuDialogFragment extends BaseDialogFragment {

    /**
     * The container.
     */
    private LinearLayout mLlContainer;

    public PopupMenuDialogFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationAlphaDialogNoDim);
    }

    @Override
    protected void setContent() {
        setContentView(R.layout.widget_popup_menu_dialog);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
        mLlContainer = findSubViewById(R.id.popup_menu_dialog_ll);
    }


}
