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

    private static final int DEFAULT_MSG_PADDING_LEFT = 10; //dp
    private static final int DEFAULT_MSG_PADDING_TOP = 10; //dp
    private static final int DEFAULT_MSG_PADDING_RIGHT = 10; //dp
    private static final int DEFAULT_MSG_PADDING_BOTTOM = 10; //dp

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
     * The padding of TextView.
     */
    private int mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom;


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
        float scale = getActivity().getResources().getDisplayMetrics().density;
        mPaddingLeft = (int) (DEFAULT_MSG_PADDING_LEFT * scale + 0.5f);
        mPaddingRight = (int) (DEFAULT_MSG_PADDING_RIGHT * scale + 0.5f);
        mPaddingTop = (int) (DEFAULT_MSG_PADDING_TOP * scale + 0.5f);
        mPaddingBottom = (int) (DEFAULT_MSG_PADDING_BOTTOM * scale + 0.5f);
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
