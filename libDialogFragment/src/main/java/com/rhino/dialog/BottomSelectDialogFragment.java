package com.rhino.dialog;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseDialogFragment;

import java.util.List;

/**
 * <p>The custom select dialog bottom</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class BottomSelectDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private static final float DEFAULT_CONTENT_PADDING_LEFT = 10; //dp
    private static final float DEFAULT_CONTENT_PADDING_TOP = 10; //dp
    private static final float DEFAULT_CONTENT_PADDING_RIGHT = 10; //dp
    private static final float DEFAULT_CONTENT_PADDING_BOTTOM = 10; //dp
    private static final float DEFAULT_LINE_HEIGHT = 0.5f; //dp
    private static final float DEFAULT_TITLE_HEIGHT = 40; //dp
    private static final float DEFAULT_ITEM_HEIGHT = 45; //dp
    private static final float DEFAULT_ITEM_CANCEL_HEIGHT = 45; //dp
    private static final float DEFAULT_GAP_HEIGHT = 5; //dp

    /**
     * The dialog view.
     */
    private LinearLayout mLlDialog;
    /**
     * The padding of Dialog.
     */
    private int mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom;
    /**
     * The height of title.
     */
    private int mTitleHeight;
    /**
     * The text of title.
     */
    private String mTitleText;
    /**
     * The text size of title.
     */
    private int mTitleTextSize = 16;
    /**
     * The text color of title.
     */
    private int mTitleTextColor = Color.BLACK;
    /**
     * The color of line.
     */
    private int mLineColor = 0x1A000000;
    /**
     * The height of line.
     */
    private int mLineHeight;
    /**
     * The height of item.
     */
    private int mItemHeight;
    /**
     * The text size of item.
     */
    private int mItemTextSize = 16;
    /**
     * The height of gap.
     */
    private int mGapHeight;
    /**
     * Whether show cancel item.
     */
    private boolean mIsShowCancel = true;
    /**
     * The height of cancel item.
     */
    private int mItemCancelHeight;
    /**
     * The text of cancel item.
     */
    private String mItemCancelText = "Cancel";
    /**
     * The text size of cancel item.
     */
    private int mItemCancelTextSize = 16;
    /**
     * The text color of cancel item.
     */
    private int mItemCancelTextColor = Color.RED;
    /**
     * The text ColorStateList of cancel item.
     */
    private ColorStateList mItemCancelTextColorStateList;
    /**
     * The item list.
     */
    private List<Item> mItemList;
    /**
     * The listener for item click.
     */
    private IOnItemClickListener mIOnItemClickListener;

    public BottomSelectDialogFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationTranBottomDialog);
        setWindowGravity(Gravity.BOTTOM);
        setWindowWidth(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void setContent() {
        mLlDialog = new LinearLayout(getActivity());
        mLlDialog.setOrientation(LinearLayout.VERTICAL);
        setContentView(mLlDialog);
    }

    @Override
    protected boolean initData() {
        float scale = getActivity().getResources().getDisplayMetrics().density;
        mPaddingLeft = (int) (DEFAULT_CONTENT_PADDING_LEFT * scale + 0.5f);
        mPaddingRight = (int) (DEFAULT_CONTENT_PADDING_RIGHT * scale + 0.5f);
        mPaddingTop = (int) (DEFAULT_CONTENT_PADDING_TOP * scale + 0.5f);
        mPaddingBottom = (int) (DEFAULT_CONTENT_PADDING_BOTTOM * scale + 0.5f);
        mLineHeight = (int) (DEFAULT_LINE_HEIGHT * scale + 0.5f);
        mTitleHeight = (int) (DEFAULT_TITLE_HEIGHT * scale + 0.5f);
        mItemHeight = (int) (DEFAULT_ITEM_HEIGHT * scale + 0.5f);
        mItemCancelHeight = (int) (DEFAULT_ITEM_CANCEL_HEIGHT * scale + 0.5f);
        mGapHeight = (int) (DEFAULT_GAP_HEIGHT * scale + 0.5f);
        return true;
    }

    @Override
    protected void initView() {
        mLlDialog.removeAllViews();
        // set padding
        mLlDialog.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);

        // content
        LinearLayout contentLl = new LinearLayout(getActivity());
        contentLl.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        contentLl.setOrientation(LinearLayout.VERTICAL);
        contentLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rect_sol_white_cor5));

        // add title
        if (!TextUtils.isEmpty(mTitleText)) {
            TextView textView = buildNormalTextView(mTitleText);
            textView.setTextSize(mTitleTextSize);
            textView.setTextColor(mTitleTextColor);
            contentLl.addView(textView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, mTitleHeight));
            // add line
            contentLl.addView(buildLine(), new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    mLineHeight));
        }

        // add item
        if (null != mItemList && 0 < mItemList.size()) {
            int count = mItemList.size();
            for (int i = 0; i < count; i++) {
                TextView textView = buildItem(mItemList.get(i));
                if (0 == i && 0 == contentLl.getChildCount()) {
                    textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_rect_nor_white_pre_black10_tcor5));
                } else if (0 != i && count - 1 == i) {
                    textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_rect_nor_white_pre_black10_bcor5));
                } else {
                    textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_rect_nor_white_pre_black10));
                }
                contentLl.addView(textView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        mItemHeight));
                if (count - 1 != i) {
                    contentLl.addView(buildLine(), new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            mLineHeight));
                }
            }
        }
        mLlDialog.addView(contentLl);

        // add cancel
        if (mIsShowCancel) {
            View gapView = buildGap();
            gapView.setClickable(true);
            mLlDialog.addView(gapView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    mGapHeight));

            LinearLayout cancelLl = new LinearLayout(getActivity());
            cancelLl.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            cancelLl.setOrientation(LinearLayout.VERTICAL);
            cancelLl.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_rect_sol_white_cor5));

            TextView textView = buildNormalTextView(mItemCancelText);
            textView.setTextSize(mItemCancelTextSize);
            if (null != mItemCancelTextColorStateList) {
                textView.setTextColor(mItemCancelTextColorStateList);
            } else {
                textView.setTextColor(mItemCancelTextColor);
            }
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_rect_nor_white_pre_black10_cor5));
            textView.setOnClickListener(this);
            cancelLl.addView(textView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    mItemCancelHeight));
            mLlDialog.addView(cancelLl);
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Item) {
            if (null != mIOnItemClickListener) {
                mIOnItemClickListener.onItemClick(this, (Item) tag);
            }
        } else {
            // cancel
            dismiss();
        }
    }

    /**
     * Build TextView of item.
     *
     * @param item Item
     * @return TextView
     */
    private TextView buildItem(Item item) {
        TextView textView = buildNormalTextView(item.mText);
        textView.setTextSize(mItemTextSize);
        if (null != item.mTextColorStateList) {
            textView.setTextColor(item.mTextColorStateList);
        } else {
            textView.setTextColor(item.mTextColor);
        }
        textView.setTag(item);
        textView.setOnClickListener(this);
        return textView;
    }

    /**
     * Build View of line.
     *
     * @return View
     */
    private View buildLine() {
        View lineView = new View(getActivity());
        lineView.setClickable(true);
        lineView.setBackgroundColor(mLineColor);
        return lineView;
    }

    /**
     * Build View of gap.
     *
     * @return View
     */
    private View buildGap() {
        View gapView = new View(getActivity());
        gapView.setClickable(true);
        return gapView;
    }

    /**
     * Build TextView.
     *
     * @param text text
     * @return TextView
     */
    private TextView buildNormalTextView(String text) {
        TextView textView = new TextView(getActivity());
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxLines(1);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setText(text);
        return textView;
    }

    public static class Item {
        public String mText;
        public int mTextColor;
        public ColorStateList mTextColorStateList;
        public Object mExtraData;

        public static Item build(String text, int textColor) {
            Item item = new Item();
            item.mText = text;
            item.mTextColor = textColor;
            return item;
        }

        public static Item build(String text, ColorStateList textColorStateList) {
            Item item = new Item();
            item.mText = text;
            item.mTextColorStateList = textColorStateList;
            return item;
        }
    }

    /**
     * The item click listener.
     **/
    public interface IOnItemClickListener {
        void onItemClick(BottomSelectDialogFragment dialog, Item item);
    }

    /**
     * Set the item click listener.
     *
     * @param listener listener
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setOnItemClickListener(IOnItemClickListener listener) {
        this.mIOnItemClickListener = listener;
        return this;
    }

    /**
     * Set list of item.
     *
     * @param items list
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItems(List<Item> items) {
        this.mItemList = items;
        return this;
    }

    /**
     * Set height of title.
     *
     * @param height height
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setTitleHeight(int height) {
        this.mTitleHeight = height;
        return this;
    }

    /**
     * Set text of title.
     *
     * @param text text
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setTitleText(String text) {
        this.mTitleText = text;
        return this;
    }

    /**
     * Set text size of title.
     *
     * @param textSize textSize
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setTitleTextSize(int textSize) {
        this.mTitleTextSize = textSize;
        return this;
    }

    /**
     * Set text color of title.
     *
     * @param color color
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        return this;
    }

    /**
     * Set color of line.
     *
     * @param color color
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setLineColor(@ColorInt int color) {
        this.mLineColor = color;
        return this;
    }

    /**
     * Set height of line.
     *
     * @param height height
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setLineHeight(int height) {
        this.mLineHeight = height;
        return this;
    }

    /**
     * Set height of item.
     *
     * @param height height
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemHeight(int height) {
        this.mItemHeight = height;
        return this;
    }

    /**
     * Set text size of item.
     *
     * @param textSize textSize
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemTextSize(int textSize) {
        this.mItemTextSize = textSize;
        return this;
    }

    /**
     * Set height of gap.
     *
     * @param height height
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setGapHeight(int height) {
        this.mGapHeight = height;
        return this;
    }

    /**
     * Set show cancel.
     *
     * @param show show
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setShowCancel(boolean show) {
        this.mIsShowCancel = show;
        return this;
    }

    /**
     * Set height of cancel item.
     *
     * @param height height
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemCancelHeight(int height) {
        this.mItemCancelHeight = height;
        return this;
    }

    /**
     * Set text size of cancel item.
     *
     * @param text text
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemCancelText(String text) {
        this.mItemCancelText = text;
        return this;
    }

    /**
     * Set text size of cancel item.
     *
     * @param textSize textSize
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemCancelTextSize(int textSize) {
        this.mItemCancelTextSize = textSize;
        return this;
    }

    /**
     * Set text color of cancel item.
     *
     * @param color color
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemCancelTextColor(@ColorInt int color) {
        this.mItemCancelTextColor = color;
        return this;
    }

    /**
     * Set text ColorStateList of cancel item.
     *
     * @param colorStateList ColorStateList
     * @return BottomSelectDialogFragment
     */
    public BottomSelectDialogFragment setItemCancelTextColorStateList(ColorStateList colorStateList) {
        this.mItemCancelTextColorStateList = colorStateList;
        return this;
    }
}
