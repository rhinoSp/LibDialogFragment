package com.rhino.dialog.pwd;


import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseSimpleDialogFragment;


/**
 * <p>The custom password input dialog</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class PwdInputDialogFragment extends BaseSimpleDialogFragment {

    /**
     * The default max length of password.
     **/
    private static final int DF_MAX_LENGTH = 6;

    /**
     * The input type of password.
     *
     * @see InputType
     */
    private int mInputType = InputType.TYPE_CLASS_NUMBER;
    /**
     * The max length of password.
     */
    private int mMaxLength = DF_MAX_LENGTH;
    /**
     * The color of password background.
     */
    @ColorInt
    private int mEditBackgroundColor = 0xFFFFFFFF;
    /**
     * The size of password.
     */
    private int mEditTextSize = 18;
    /**
     * The color of password.
     */
    @ColorInt
    private int mEditTextColor = 0xFF000000;
    /**
     * The color of rim stroke.
     */
    @ColorInt
    private int mRimStrokeColor = 0x66000000;
    /**
     * The rim corner.
     */
    @Dimension
    private int mRimCorner = 4;
    /**
     * Whether show password.
     **/
    private boolean mIsShowPwd = false;
    /**
     * The string show when password input.
     **/
    private String mShowStr = null;
    /**
     * The picture show when password input.
     **/
    @DrawableRes
    private int mShowPicId = 0;

    /**
     * The password EditText.
     */
    private PwdInputEditText mPwdInputEditText;
    /**
     * The TextWatcher.
     */
    private TextWatcher mTextWatcher;
    /**
     * the width of pwd
     */
    private int mPwdWidth;
    /**
     * the height of pwd
     */
    private int mPwdHeight;

    public PwdInputDialogFragment() {
        setTitle("Input password");
        setTitleBottomLineColor(0x2A000000);
        setKeyTopLineColor(0x2A000000);
        setKeyCenterLineColor(0x2A000000);
    }

    @Override
    protected void setContent() {
        LinearLayout mLinearLayout = new LinearLayout(getActivity());
        mLinearLayout.setGravity(Gravity.CENTER);

        if (mPwdWidth == 0) {
            mPwdWidth = (int) getActivity().getResources().getDimension(R.dimen.password_input_dialog_container_width);
        }
        if (mPwdHeight == 0) {
            mPwdHeight = (int) getActivity().getResources().getDimension(R.dimen.password_input_dialog_container_height);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(mPwdWidth, mPwdHeight);

        mPwdInputEditText = new PwdInputEditText(getActivity());
        mPwdInputEditText.setLayoutParams(lp);
        if (mTextWatcher != null) {
            mPwdInputEditText.addTextChangedListener(mTextWatcher);
        }

        mLinearLayout.addView(mPwdInputEditText);
        setContentView(mLinearLayout);
    }

    @Override
    protected boolean initData() {
        float scale = getActivity().getResources().getDisplayMetrics().density;
        mRimCorner = (int) (2 * scale + 0.5f);
        return true;
    }

    @Override
    protected void initView() {
        mPwdInputEditText.setInputType(mInputType);
        mPwdInputEditText.setMaxLength(mMaxLength);
        mPwdInputEditText.setTextSize(mEditTextSize);
        mPwdInputEditText.setTextColor(mEditTextColor);
        mPwdInputEditText.setBackgroundColor(mEditBackgroundColor);
        mPwdInputEditText.setRimStrokeColor(mRimStrokeColor);
        mPwdInputEditText.setRimCorner(mRimCorner);
        if (0 < mShowPicId) {
            mPwdInputEditText.setShowPassword(mShowPicId);
        } else if (!TextUtils.isEmpty(mShowStr)) {
            mPwdInputEditText.setShowPassword(mShowStr);
        } else {
            mPwdInputEditText.setShowPassword(mIsShowPwd);
        }
    }

    @Override
    protected void onClickNegativeKey() {
        super.onClickNegativeKey();
        dismiss();
    }

    @Nullable
    public PwdInputEditText getEditText() {
        return mPwdInputEditText;
    }

    /**
     * Set the TextWatcher.
     * @param textWatcher TextWatcher
     */
    public PwdInputDialogFragment setTextWatcher(TextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.addTextChangedListener(textWatcher);
        }
        return this;
    }

    /**
     * Set the width of pwd
     *
     * @param mPwdWidth the width of pwd
     */
    public PwdInputDialogFragment setPwdWidth(int mPwdWidth) {
        this.mPwdWidth = mPwdWidth;
        if (null != mPwdInputEditText) {
            ViewGroup.LayoutParams lp = mPwdInputEditText.getLayoutParams();
            lp.width = mPwdWidth;
        }
        return this;
    }

    /**
     * Set the height of pwd
     *
     * @param mPwdHeight the height of pwd
     */
    public PwdInputDialogFragment setPwdHeight(int mPwdHeight) {
        this.mPwdHeight = mPwdHeight;
        if (null != mPwdInputEditText) {
            ViewGroup.LayoutParams lp = mPwdInputEditText.getLayoutParams();
            lp.height = mPwdHeight;
        }
        return this;
    }

    /**
     * Set the color of password background.
     *
     * @param color color
     */
    public PwdInputDialogFragment setEditBackgroundColor(@ColorInt int color) {
        this.mEditBackgroundColor = color;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setBackgroundColor(mEditBackgroundColor);
        }
        return this;
    }

    /**
     * Set the input type of password.
     *
     * @see InputType
     **/
    public PwdInputDialogFragment setInputType(int type) {
        this.mInputType = type;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setInputType(mInputType);
        }
        return this;
    }

    /**
     * Set the max length of password.
     *
     * @param maxLength the max length of password
     */
    public PwdInputDialogFragment setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setMaxLength(mMaxLength);
        }
        return this;
    }

    /**
     * Get the max length of password.
     *
     * @return the max length of password
     */
    public int getMaxLength() {
        return mMaxLength;
    }

    /**
     * Set the size of password.
     *
     * @param size size
     */
    public PwdInputDialogFragment setTextSize(int size) {
        this.mEditTextSize = size;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setTextSize(mEditTextSize);
        }
        return this;
    }

    /**
     * Set the color of password.
     *
     * @param color color
     */
    public PwdInputDialogFragment setTextColor(@ColorInt int color) {
        this.mEditTextColor = color;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setTextColor(mEditTextColor);
        }
        return this;
    }

    /**
     * Set the color of rim stroke.
     *
     * @param color color
     */
    public PwdInputDialogFragment setRimStrokeColor(@ColorInt int color) {
        this.mRimStrokeColor = color;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setRimStrokeColor(mRimStrokeColor);
        }
        return this;
    }

    /**
     * Set the corner.
     *
     * @param corner corner
     */
    public PwdInputDialogFragment setRimCorner(@Dimension int corner) {
        this.mRimCorner = corner;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setRimCorner(mRimCorner);
        }
        return this;
    }

    /**
     * Set whether show password.
     *
     * @param show True show password
     */
    public PwdInputDialogFragment setShowPassword(boolean show) {
        this.mIsShowPwd = show;
        this.mShowPicId = 0;
        this.mShowStr = null;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setShowPassword(show);
        }
        return this;
    }

    /**
     * Set picture show when password input.
     *
     * @param showPicId the picture show when password input
     */
    public PwdInputDialogFragment setShowPassword(@DrawableRes int showPicId) {
        this.mIsShowPwd = true;
        this.mShowPicId = showPicId;
        this.mShowStr = null;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setShowPassword(mShowPicId);
        }
        return this;
    }

    /**
     * Set string show when password input.
     *
     * @param showStr the string show when password input
     */
    public PwdInputDialogFragment setShowPassword(String showStr) {
        this.mIsShowPwd = true;
        this.mShowPicId = 0;
        this.mShowStr = showStr;
        if (null != mPwdInputEditText) {
            mPwdInputEditText.setShowPassword(mShowStr);
        }
        return this;
    }

    /**
     * Get the password.
     **/
    @NonNull
    public String getPasswordString() {
        if (null == mPwdInputEditText) {
            return "";
        }
        return mPwdInputEditText.getText().toString();
    }

    /**
     * Get the password.
     */
    public int getPasswordNumber() {
        try {
            return Integer.parseInt(getPasswordString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Whether input finished.
     *
     * @return True finished
     */
    public boolean isFinishedInput() {
        return getPasswordString().length() == mMaxLength;
    }
}
