package com.rhino.dialog.picker;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseDialogFragment;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;
import com.rhino.wheel.WheelView;

import java.util.Arrays;

/**
 * @author rhino
 * @since Create on 2019/10/29.
 **/
public class SingleWheelPickerDialogFragment extends BaseDialogFragment {

    /**
     * The title text.
     */
    private String mTitleText;
    /**
     * The size of title text.
     */
    protected int mTitleTextSize = 16;
    /**
     * The color of title text.
     */
    @ColorInt
    protected int mTitleTextColor = 0xFF000000;
    /**
     * The negative key listener.
     */
    protected IOnDialogKeyClickListener mNegativeListener;
    /**
     * The text of negative key.
     */
    protected String mNegativeKeyText = "取消";
    /**
     * The size of negative key text.
     */
    protected int mNegativeKeyTextSize = 14;
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
     * The positive key listener.
     */
    protected IOnDialogKeyClickListener mPositiveListener;
    /**
     * The text of positive key.
     */
    protected String mPositiveKeyText = "确认";
    /**
     * The size of positive key text.
     */
    protected int mPositiveKeyTextSize = 14;
    /**
     * The color of positive key text.
     */
    @ColorInt
    protected int mPositiveKeyTextColor = 0xFF000000;
    /**
     * The colorStateList of positive key text.
     */
    protected ColorStateList mPositiveKeyTextColors = null;

    public TextView mTvNegativeKey;
    public TextView mTvPositiveKey;
    public TextView mTvTitle;
    public WheelView mWheelView;

    private String mLabel;
    private String[] mValues;
    private String mCurrentValue;

    public SingleWheelPickerDialogFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationTranBottomDialog);
        setWindowGravity(Gravity.BOTTOM);
        setWindowWidth(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void setContent() {
        setContentView(R.layout.widget_single_wheel_picker_dialog);
    }

    @Override
    protected void initView() {
        mTvTitle = findSubViewById(R.id.tv_title);
        mTvNegativeKey = findSubViewById(R.id.bt_cancel);
        mTvPositiveKey = findSubViewById(R.id.bt_ok);
        mWheelView = findSubViewById(R.id.wheel_view);
        mWheelView.setDisplayedValues(mValues);
        mWheelView.setLabel(mLabel);
        mWheelView.setValue(Arrays.asList(mValues).indexOf(mCurrentValue) + 1);
        setTitle(mTitleText);
        setTitleTextSize(mTitleTextSize);
        setTitleTextColor(mTitleTextColor);
        setNegativeKeyText(mNegativeKeyText);
        setNegativeKeyTextSize(mNegativeKeyTextSize);
        if (null != mNegativeKeyTextColors) {
            setNegativeKeyColor(mNegativeKeyTextColors);
        } else {
            setNegativeKeyColor(mNegativeKeyTextColor);
        }
        setPositiveKeyText(mPositiveKeyText);
        setPositiveKeyTextSize(mPositiveKeyTextSize);
        if (null != mPositiveKeyTextColors) {
            setPositiveKeyColor(mPositiveKeyTextColors);
        } else {
            setPositiveKeyColor(mPositiveKeyTextColor);
        }
        setBaseOnClickListener(mTvNegativeKey, mTvPositiveKey);
    }

    @Override
    protected void baseOnClickListener(View v) {
        super.baseOnClickListener(v);
        if (v == mTvNegativeKey) {
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
     * Set the title text.
     *
     * @param text text
     */
    public SingleWheelPickerDialogFragment setTitle(String text) {
        this.mTitleText = text;
        if (null != mTvTitle) {
            mTvTitle.setText(text);
        }
        return this;
    }

    /**
     * Set size of the title.
     *
     * @param size size
     */
    public SingleWheelPickerDialogFragment setTitleTextSize(int size) {
        this.mTitleTextSize = size;
        if (null != mTvTitle) {
            mTvTitle.setTextSize(size);
        }
        return this;
    }

    /**
     * Set the title text color.
     *
     * @param color the color
     */
    public SingleWheelPickerDialogFragment setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        if (null != mTvTitle) {
            mTvTitle.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the negative key click listener.
     *
     * @param listener the listener
     */
    public SingleWheelPickerDialogFragment setNegativeKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mNegativeListener = listener;
        return this;
    }

    /**
     * Set the positive key click listener.
     *
     * @param listener the listener
     */
    public SingleWheelPickerDialogFragment setPositiveKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mPositiveListener = listener;
        return this;
    }

    /**
     * Set negative key text.
     *
     * @param text text
     */
    public SingleWheelPickerDialogFragment setNegativeKeyText(String text) {
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
    public SingleWheelPickerDialogFragment setNegativeKeyTextSize(int size) {
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
    public SingleWheelPickerDialogFragment setNegativeKeyColor(@ColorInt int color) {
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
    public SingleWheelPickerDialogFragment setNegativeKeyColor(ColorStateList colors) {
        this.mNegativeKeyTextColors = colors;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setTextColor(colors);
        }
        return this;
    }

    /**
     * Set positive key text.
     *
     * @param text text
     */
    public SingleWheelPickerDialogFragment setPositiveKeyText(String text) {
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
    public SingleWheelPickerDialogFragment setPositiveKeyTextSize(int size) {
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
    public SingleWheelPickerDialogFragment setPositiveKeyColor(@ColorInt int color) {
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
    public SingleWheelPickerDialogFragment setPositiveKeyColor(ColorStateList colors) {
        this.mPositiveKeyTextColors = colors;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextColor(colors);
        }
        return this;
    }

    public SingleWheelPickerDialogFragment setLabel(String label) {
        this.mLabel = label;
        return this;
    }

    public SingleWheelPickerDialogFragment setCurrentValue(String currentValue) {
        this.mCurrentValue = currentValue;
        return this;
    }

    public SingleWheelPickerDialogFragment setValues(String[] values) {
        this.mValues = values;
        return this;
    }

    public int getSelectedIndex() {
        return mWheelView.getValue() - 1;
    }

    public String getSelectedValue() {
        return mValues[mWheelView.getValue() - 1];
    }

}
