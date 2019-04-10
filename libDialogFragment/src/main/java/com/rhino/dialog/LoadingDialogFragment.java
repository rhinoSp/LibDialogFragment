package com.rhino.dialog;

import android.support.annotation.DrawableRes;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
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
    public TextView mLoadingDesc;
    /**
     * The ImageView of loading.
     */
    public ImageView mLoadingIcon;
    /**
     * The message of loading.
     */
    private String mLoadingText = "Loading...";
    /**
     * The icon of loading.
     */
    public int mLoadingIconResId = R.mipmap.ic_loading;


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
        mLoadingIcon = findSubViewById(R.id.iv);
        mLoadingDesc.setText(mLoadingText);
        mLoadingIcon.setImageResource(mLoadingIconResId);
    }

    @Override
    public void onStart() {
        super.onStart();
        startRotateAnim();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopRotateAnim();
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

    /**
     * Set the icon of loading.
     *
     * @param resId the icon of loading
     * @return this
     */
    public LoadingDialogFragment setLoadingIconResId(@DrawableRes int resId) {
        this.mLoadingIconResId = resId;
        if (null != mLoadingIcon) {
            mLoadingIcon.setImageResource(mLoadingIconResId);
        }
        return this;
    }

    /**
     * Start rotate anim.
     */
    public void startRotateAnim() {
        Animation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(Animation.RESTART);
        anim.setRepeatCount(-1);
        mLoadingIcon.startAnimation(anim);
    }

    /**
     * Stop rotate anim.
     */
    public void stopRotateAnim() {
        mLoadingIcon.clearAnimation();
    }

}
