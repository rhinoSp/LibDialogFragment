package com.rhino.dialog;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.rhino.dialog.base.BaseDialogFragment;

import java.lang.ref.WeakReference;


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
    public int mLoadingIconResId = R.mipmap.ic_dialog_loading;

    public LoadingDialogFragment() {
        setOutsideCancelable(false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            dismiss();
        }
    }

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
        if (mActivity == null || mActivity.isFinishing()) {
            return this;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mLoadingDesc) {
                    mLoadingDesc.setText(text);
                }
            }
        });
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
        if (mActivity == null || mActivity.isFinishing()) {
            return this;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mLoadingIcon) {
                    mLoadingIcon.setImageResource(mLoadingIconResId);
                }
            }
        });
        return this;
    }

    /**
     * Start rotate anim.
     */
    public void startRotateAnim() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(1000);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatMode(Animation.RESTART);
                anim.setRepeatCount(-1);
                mLoadingIcon.startAnimation(anim);
            }
        });
    }

    /**
     * Stop rotate anim.
     */
    public void stopRotateAnim() {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingIcon.clearAnimation();
            }
        });
    }

    public static LoadingDialogHelper getHelper(FragmentActivity activity) {
        return new LoadingDialogHelper(activity);
    }

    public static class LoadingDialogHelper {
        private WeakReference<FragmentActivity> activity;
        private LoadingDialogFragment dialogFragment;

        public LoadingDialogHelper(FragmentActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        public void showLoadingDialog() {
            if (dialogFragment != null) {
                dialogFragment.dismiss();
                dialogFragment = null;
            }
            dialogFragment = new LoadingDialogFragment();
            dialogFragment.show(activity.get());
        }

        public void dismissLoadingDialog() {
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
        }

        public void updateText(String text) {
            if (dialogFragment != null) {
                dialogFragment.setText(text);
            }
        }

        public LoadingDialogFragment getLoadingDialogFragment() {
            return dialogFragment;
        }
    }

}
