package com.rhino.dialog;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.widget.TextView;

import com.rhino.dialog.base.BaseSimpleDialogFragment;


/**
 * <p>The custom message dialog</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class MsgDialogFragment extends BaseSimpleDialogFragment {

    /**
     * The default left padding of TextView.
     */
    private static final int DEFAULT_EDIT_PADDING_LEFT = 20;
    /**
     * The default top padding of TextView.
     */
    private static final int DEFAULT_EDIT_PADDING_TOP = 20;
    /**
     * The default right padding of TextView.
     */
    private static final int DEFAULT_EDIT_PADDING_RIGHT = 20;
    /**
     * The default bottom padding of TextView.
     */
    private static final int DEFAULT_EDIT_PADDING_BOTTOM = 20;

    /**
     * The message TextView.
     */
    private TextView mTextView;
    /**
     * The color of TextView.
     */
    @ColorInt
    private int mTextColor = Color.BLACK;
    /**
     * The size of TextView.
     */
    private int mTextSize = 16;
    /**
     * The message text.
     */
    private String mText = "message";
    /**
     * The left padding of TextView.
     */
    private int mPaddingLeft = DEFAULT_EDIT_PADDING_LEFT;
    /**
     * The top padding of TextView.
     */
    private int mPaddingTop = DEFAULT_EDIT_PADDING_TOP;
    /**
     * The right padding of TextView.
     */
    private int mPaddingRight = DEFAULT_EDIT_PADDING_RIGHT;
    /**
     * The bottom padding of TextView.
     */
    private int mPaddingBottom = DEFAULT_EDIT_PADDING_BOTTOM;


    @Override
    protected void setContent() {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(mTextSize);
        mTextView.setText(mText);
        mTextView.setTextColor(mTextColor);

        mTextView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        setContentView(mTextView);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
    }

    /**
     * Set the padding of TextView.
     */
    public MsgDialogFragment setPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingTop = top;
        mPaddingRight = right;
        mPaddingBottom = bottom;
        if (null != mTextView) {
            mTextView.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * Set the size of TextView.
     *
     * @param size size
     */
    public MsgDialogFragment setTextSize(int size) {
        this.mTextSize = size;
        if (null != mTextView) {
            mTextView.setTextSize(size);
        }
        return this;
    }

    /**
     * Set the color of TextView.
     *
     * @param color color
     */
    public MsgDialogFragment setTextColor(@ColorInt int color) {
        this.mTextColor = color;
        if (null != mTextView) {
            mTextView.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the text.
     *
     * @param text text
     */
    public MsgDialogFragment setText(String text) {
        this.mText = text;
        if (null != mTextView) {
            mTextView.setText(text);
        }
        return this;
    }


}
