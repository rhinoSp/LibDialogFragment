package com.rhino.dialog;

import android.widget.TextView;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseDialogFragment;


/**
 * <p>The custom loading dialog</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class LoadingDialogFragment extends BaseDialogFragment {

    /**
     * The TextView of loading.
     */
    private TextView mLoadingDesc;
    /**
     * The message of loading.
     */
    private String mLoadingText = "Loading...";


    @Override
    protected void setContent() {
        setContentView(R.layout.widget_loading_dialog);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
        mLoadingDesc = findSubViewById(R.id.tv);
        mLoadingDesc.setText(mLoadingText);
    }

    /**
     * Set the text of loading.
     *
     * @param text the text of loading
     * @return this
     */
    public LoadingDialogFragment setText(String text) {
        this.mLoadingText = text;
        if (null != mLoadingDesc) {
            mLoadingDesc.setText(text);
        }
        return this;
    }


}
