package com.rhino.dialog;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseSimpleDialogFragment;


/**
 * <p>The custom EditText dialog</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class EditDialogFragment extends BaseSimpleDialogFragment {

    private static final int DEFAULT_EDIT_PADDING_LEFT = 6; //dp
    private static final int DEFAULT_EDIT_PADDING_TOP = 0; //dp
    private static final int DEFAULT_EDIT_PADDING_RIGHT = 6; //dp
    private static final int DEFAULT_EDIT_PADDING_BOTTOM = 0; //dp

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
     * The padding of TextView.
     */
    private int mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom;


    @Override
    protected void setContent() {
        LinearLayout mLinearLayout = new LinearLayout(getActivity());
        mLinearLayout.setGravity(Gravity.CENTER);

        int width = (int) getActivity().getResources().getDimension(R.dimen.edit_dialog_container_width);
        int height = (int) getActivity().getResources().getDimension(R.dimen.edit_dialog_container_height);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);

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
        float scale = getActivity().getResources().getDisplayMetrics().density;
        mPaddingLeft = (int) (DEFAULT_EDIT_PADDING_LEFT * scale + 0.5f);
        mPaddingRight = (int) (DEFAULT_EDIT_PADDING_RIGHT * scale + 0.5f);
        mPaddingTop = (int) (DEFAULT_EDIT_PADDING_TOP * scale + 0.5f);
        mPaddingBottom = (int) (DEFAULT_EDIT_PADDING_BOTTOM * scale + 0.5f);
        mEditBackgroundDrawable = getActivity().getResources().getDrawable(R.drawable.shape_rect_sol_tran_str1_black20_cor5);
        return true;
    }

    @Override
    protected void initView() {
    }

    /**
     * Set the padding of TextView.
     */
    public EditDialogFragment setPadding(int left, int top, int right, int bottom) {
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
     * Set the size of TextView.
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
     * Set drawable of EditText background.
     *
     * @param drawable drawable
     */
    public EditDialogFragment setEditBackground(Drawable drawable) {
        this.mEditBackgroundDrawable = drawable;
        if (null != mEditText) {
            mEditText.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set the color of TextView.
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
     * Set the color of TextView hint.
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
