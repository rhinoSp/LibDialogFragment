package com.rhino.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentActivity;

import com.rhino.dialog.base.BaseSimpleDialogFragment;


/**
 * <p>The custom EditText dialog</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class EditDialogFragment extends BaseSimpleDialogFragment {

    /**
     * The default left padding of EditText.
     */
    private static final int DEFAULT_EDIT_PADDING_LEFT = 12;
    /**
     * The default top padding of EditText.
     */
    private static final int DEFAULT_EDIT_PADDING_TOP = 0;
    /**
     * The default right padding of EditText.
     */
    private static final int DEFAULT_EDIT_PADDING_RIGHT = 12;
    /**
     * The default bottom padding of EditText.
     */
    private static final int DEFAULT_EDIT_PADDING_BOTTOM = 0;

    /**
     * The default max input length.
     **/
    private static final int DEFAULT_MAX_LENGTH = 30;
    /**
     * The EditText.
     */
    private EditText mEditText;
    /**
     * The input type.
     *
     * @see InputType
     */
    private int mInputType = InputType.TYPE_CLASS_TEXT;
    /**
     * The max input length.
     */
    private int mMaxLength = DEFAULT_MAX_LENGTH;
    /**
     * The color of EditText.
     */
    @ColorInt
    private int mEditTextColor = Color.BLACK;
    /**
     * The size of EditText.
     */
    private int mEditTextSize = 16;
    /**
     * The text.
     */
    private String mText = "";
    /**
     * The color of EditText hint.
     */
    @ColorInt
    private int mHintTextColor = 0x1a000000;
    /**
     * The hint text.
     */
    private String mHintText = "";
    /**
     * The drawable of EditText background.
     */
    private Drawable mEditBackgroundDrawable;
    /**
     * The left padding of EditText.
     */
    private int mPaddingLeft = DEFAULT_EDIT_PADDING_LEFT;
    /**
     * The top padding of EditText.
     */
    private int mPaddingTop = DEFAULT_EDIT_PADDING_TOP;
    /**
     * The right padding of EditText.
     */
    private int mPaddingRight = DEFAULT_EDIT_PADDING_RIGHT;
    /**
     * The bottom padding of EditText.
     */
    private int mPaddingBottom = DEFAULT_EDIT_PADDING_BOTTOM;
    /**
     * The width of EditText.
     */
    private int mEditTextWidth;
    /**
     * The height of EditText.
     */
    private int mEditTextHeight;


    @Override
    protected void setContent() {
        LinearLayout mLinearLayout = new LinearLayout(getActivity());
        mLinearLayout.setGravity(Gravity.CENTER);

        if (mEditBackgroundDrawable == null) {
            mEditBackgroundDrawable = getActivity().getResources().getDrawable(R.drawable.shape_rect_sol_tran_str1_black20_cor5);
        }

        if (mEditTextWidth == 0) {
            mEditTextWidth = (int) getActivity().getResources().getDimension(R.dimen.edit_dialog_container_width);
        }
        if (mEditTextHeight == 0) {
            mEditTextHeight = (int) getActivity().getResources().getDimension(R.dimen.edit_dialog_container_height);
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(mEditTextWidth, mEditTextHeight);

        mEditText = new EditText(getActivity());
        mEditText.setLayoutParams(lp);
        mEditText.setInputType(mInputType);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mEditText.setBackgroundDrawable(mEditBackgroundDrawable);
        mEditText.setTextSize(mEditTextSize);
        mEditText.setTextColor(mEditTextColor);
        mEditText.setText(mText);
        mEditText.setHintTextColor(mHintTextColor);
        mEditText.setHint(mHintText);
        mEditText.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);

        mLinearLayout.addView(mEditText);
        setContentView(mLinearLayout);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), getClass().getName());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null && !isDetached()) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }
        }, 100);
    }

    /**
     * Set the width of EditText.
     */
    public EditDialogFragment setEditTextWidth(int width) {
        this.mEditTextWidth = width;
        if (null != mEditText) {
            ViewGroup.LayoutParams lp = mEditText.getLayoutParams();
            lp.height = mEditTextHeight;
        }
        return this;
    }

    /**
     * Set the height of EditText.
     */
    public EditDialogFragment setEditTextHeight(int height) {
        this.mEditTextHeight = height;
        if (null != mEditText) {
            ViewGroup.LayoutParams lp = mEditText.getLayoutParams();
            lp.height = mEditTextHeight;
        }
        return this;
    }

    /**
     * Set the padding of EditText.
     */
    public EditDialogFragment setEditTextPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingTop = top;
        mPaddingRight = right;
        mPaddingBottom = bottom;
        if (null != mEditText) {
            mEditText.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * Set the input type.
     *
     * @see InputType
     **/
    public EditDialogFragment setInputType(int type) {
        this.mInputType = type;
        if (null != mEditText) {
            mEditText.setInputType(mInputType);
        }
        return this;
    }

    /**
     * Set the max input length.
     *
     * @param maxLength the max input length
     */
    public EditDialogFragment setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
        if (null != mEditText) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        }
        return this;
    }

    /**
     * Get the max input length.
     *
     * @return the max input length
     */
    public int getMaxLength() {
        return mMaxLength;
    }

    /**
     * Set drawable of EditText background.
     *
     * @param drawable drawable
     */
    public EditDialogFragment setEditTextBackground(Drawable drawable) {
        this.mEditBackgroundDrawable = drawable;
        if (null != mEditText) {
            mEditText.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set the size of EditText.
     *
     * @param size size
     */
    public EditDialogFragment setEditTextSize(int size) {
        this.mEditTextSize = size;
        if (null != mEditText) {
            mEditText.setTextSize(size);
        }
        return this;
    }

    /**
     * Set the color of EditText.
     *
     * @param color color
     */
    public EditDialogFragment setEditTextColor(@ColorInt int color) {
        this.mEditTextColor = color;
        if (null != mEditText) {
            mEditText.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the color of EditText hint.
     *
     * @param color color
     */
    public EditDialogFragment setHintTextColor(@ColorInt int color) {
        this.mHintTextColor = color;
        if (null != mEditText) {
            mEditText.setHintTextColor(color);
        }
        return this;
    }

    /**
     * Set the hint text.
     *
     * @param text text
     */
    public EditDialogFragment setHintText(String text) {
        this.mHintText = text;
        if (null != mEditText) {
            mEditText.setHint(text);
        }
        return this;
    }

    /**
     * Set the text.
     *
     * @param text text
     */
    public EditDialogFragment setText(String text) {
        this.mText = text;
        if (null != mEditText) {
            mEditText.setText(text);
        }
        return this;
    }

    /**
     * Get the text of EditText.
     *
     * @return the text of EditText
     */
    public String getText() {
        if (null != mEditText) {
            return mEditText.getText().toString();
        }
        return "";
    }
}
