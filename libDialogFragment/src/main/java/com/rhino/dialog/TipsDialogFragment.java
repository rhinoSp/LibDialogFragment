package com.rhino.dialog;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhino.dialog.base.BaseDialogFragment;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;


/**
 * <p>The custom tips dialog</p>
 *
 * @author LuoLin
 * @since Create on 2018/12/6.
 */
public class TipsDialogFragment extends BaseDialogFragment {

    /**
     * The tips.
     */
    protected TextView mTvTips;
    /**
     * The checkbox.
     */
    protected LinearLayout mLlCheckbox;
    /**
     * The checkbox.
     */
    protected ImageView mIvCheckbox;
    /**
     * The checkbox.
     */
    protected TextView mTvCheckbox;
    /**
     * The TextView of negative key.
     */
    protected TextView mTvNegativeKey;
    /**
     * The TextView of positive key.
     */
    protected TextView mTvPositiveKey;

    /**
     * The tips text.
     */
    protected String mTipsText = "title";
    /**
     * The size of title text.
     */
    protected int mTipsTextSize = 18;
    /**
     * The color of title text.
     */
    @ColorInt
    protected int mTipsTextColor = 0xFF000000;
    /**
     * The visibility state of checkbox.
     */
    protected int mCheckboxVisibility = View.VISIBLE;
    /**
     * The negative key listener.
     */
    protected IOnDialogKeyClickListener mNegativeListener;
    /**
     * The positive key listener.
     */
    protected IOnDialogKeyClickListener mPositiveListener;
    /**
     * The visibility state of negative key.
     */
    protected int mNegativeKeyVisibility = View.VISIBLE;
    /**
     * The text of negative key.
     */
    protected String mNegativeKeyText = "Cancel";
    /**
     * The size of negative key text.
     */
    protected int mNegativeKeyTextSize = 18;
    /**
     * The color of negative key text.
     */
    @ColorInt
    protected int mNegativeKeyTextColor = 0xFF000000;
    /**
     * The colorStateList of negative key text.
     */
    protected ColorStateList mNegativeKeyTextColors = null;

    /**
     * The visibility state of positive key.
     */
    protected int mPositiveKeyVisibility = View.VISIBLE;
    /**
     * The text of positive key.
     */
    protected String mPositiveKeyText = "Ok";
    /**
     * The size of positive key text.
     */
    protected int mPositiveKeyTextSize = 18;
    /**
     * The color of positive key text.
     */
    @ColorInt
    protected int mPositiveKeyTextColor = 0xFF000000;
    /**
     * The colorStateList of positive key text.
     */
    protected ColorStateList mPositiveKeyTextColors = null;


    @Override
    protected void setContent() {
        setContentView(R.layout.widget_tips_dialog);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
        mTvTips = findSubViewById(R.id.tv_tips);
        mLlCheckbox = findSubViewById(R.id.ll_checkbox);
        mIvCheckbox = findSubViewById(R.id.iv_checkbox);
        mTvCheckbox = findSubViewById(R.id.tv_checkbox);
        mTvNegativeKey = findSubViewById(R.id.bt_cancel);
        mTvPositiveKey = findSubViewById(R.id.bt_ok);

        mTvTips.setText(mTipsText);
        mTvTips.setTextSize(mTipsTextSize);
        mTvTips.setTextColor(mTipsTextColor);
        mLlCheckbox.setVisibility(mCheckboxVisibility);

        mTvNegativeKey.setVisibility(mNegativeKeyVisibility);
        mTvNegativeKey.setText(mNegativeKeyText);
        mTvNegativeKey.setTextSize(mNegativeKeyTextSize);
        if (null != mNegativeKeyTextColors) {
            mTvNegativeKey.setTextColor(mNegativeKeyTextColors);
        } else {
            mTvNegativeKey.setTextColor(mNegativeKeyTextColor);
        }

        mTvPositiveKey.setVisibility(mPositiveKeyVisibility);
        mTvPositiveKey.setText(mPositiveKeyText);
        mTvPositiveKey.setTextSize(mPositiveKeyTextSize);
        if (null != mPositiveKeyTextColors) {
            mTvPositiveKey.setTextColor(mPositiveKeyTextColors);
        } else {
            mTvPositiveKey.setTextColor(mPositiveKeyTextColor);
        }
        setBaseOnClickListener(mLlCheckbox, mTvNegativeKey, mTvPositiveKey);
    }

    @Override
    protected void baseOnClickListener(View v) {
        super.baseOnClickListener(v);
        if (v == mLlCheckbox) {
            mLlCheckbox.setSelected(!mLlCheckbox.isSelected());
            mIvCheckbox.setImageDrawable(mLlCheckbox.isSelected() ? getResources().getDrawable(R.mipmap.ic_tick) : new ColorDrawable());
        } else if (v == mTvNegativeKey) {
            onClickNegativeKey();
        } else if (v == mTvPositiveKey) {
            onClickPositiveKey();
        }
    }

    /**
     * On click negative key.
     */
    protected void onClickNegativeKey() {
        if (null != mNegativeListener) {
            mNegativeListener.onClick(this);
        } else {
            dismiss();
        }
    }

    /**
     * On click positive key.
     */
    protected void onClickPositiveKey() {
        if (null != mPositiveListener) {
            mPositiveListener.onClick(this);
        } else {
            dismiss();
        }
    }

    /**
     * Set the negative key click listener.
     *
     * @param listener the listener
     */
    public TipsDialogFragment setNegativeKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mNegativeListener = listener;
        return this;
    }

    /**
     * Set the positive key click listener.
     *
     * @param listener the listener
     */
    public TipsDialogFragment setPositiveKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mPositiveListener = listener;
        return this;
    }

    /**
     * Set the tips text.
     *
     * @param text text
     */
    public TipsDialogFragment setTips(String text) {
        this.mTipsText = text;
        if (null != mTvTips) {
            mTvTips.setText(text);
        }
        return this;
    }

    /**
     * Set size of the tips.
     *
     * @param size size
     */
    public TipsDialogFragment setTipsTextSize(int size) {
        this.mTipsTextSize = size;
        if (null != mTvTips) {
            mTvTips.setTextSize(size);
        }
        return this;
    }

    /**
     * Set the tips text color.
     *
     * @param color the color
     */
    public TipsDialogFragment setTipsTextColor(@ColorInt int color) {
        this.mTipsTextColor = color;
        if (null != mTvTips) {
            mTvTips.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public TipsDialogFragment setNegativeKeyVisibility(int visibility) {
        this.mNegativeKeyVisibility = visibility;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set negative key text.
     *
     * @param text text
     */
    public TipsDialogFragment setNegativeKeyText(String text) {
        this.mNegativeKeyText = text;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setText(text);
        }
        return this;
    }

    /**
     * Set size of the negative key.
     *
     * @param size size
     */
    public TipsDialogFragment setNegativeKeyTextSize(int size) {
        this.mNegativeKeyTextSize = size;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setTextSize(size);
        }
        return this;
    }

    /**
     * Set negative key text color.
     *
     * @param color color
     */
    public TipsDialogFragment setNegativeKeyColor(@ColorInt int color) {
        this.mNegativeKeyTextColor = color;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setTextColor(color);
        }
        return this;
    }

    /**
     * Set negative key text color.
     *
     * @param colors ColorStateList
     */
    public TipsDialogFragment setNegativeKeyColor(ColorStateList colors) {
        this.mNegativeKeyTextColors = colors;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setTextColor(colors);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public TipsDialogFragment setPositiveKeyVisibility(int visibility) {
        this.mPositiveKeyVisibility = visibility;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set positive key text.
     *
     * @param text text
     */
    public TipsDialogFragment setPositiveKeyText(String text) {
        this.mPositiveKeyText = text;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setText(text);
        }
        return this;
    }

    /**
     * Set size of the positive key.
     *
     * @param size size
     */
    public TipsDialogFragment setPositiveKeyTextSize(int size) {
        this.mPositiveKeyTextSize = size;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextSize(size);
        }
        return this;
    }

    /**
     * Set positive key text color.
     *
     * @param color color
     */
    public TipsDialogFragment setPositiveKeyColor(@ColorInt int color) {
        this.mPositiveKeyTextColor = color;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextColor(color);
        }
        return this;
    }

    /**
     * Set positive key text color.
     *
     * @param colors ColorStateList
     */
    public TipsDialogFragment setPositiveKeyColor(ColorStateList colors) {
        this.mPositiveKeyTextColors = colors;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextColor(colors);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public TipsDialogFragment setCheckboxVisibility(int visibility) {
        this.mCheckboxVisibility = visibility;
        if (null != mTvNegativeKey) {
            mLlCheckbox.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Return checkbox selected.
     *
     * @return true selected.
     */
    public boolean isCheckboxSelected() {
        return mLlCheckbox.isSelected();
    }

}
