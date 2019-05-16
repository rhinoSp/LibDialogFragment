package com.rhino.dialog.base;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rhino.dialog.R;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;


/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
@SuppressWarnings("unchecked")
public abstract class BaseSimpleDialogFragment extends BaseDialogFragment {

    /**
     * The dialog view.
     */
    protected LinearLayout mLlDialog;
    /**
     * The content view layout id.
     */
    protected int mContentId;
    /**
     * The content view.
     */
    protected View mContentView;

    /**
     * The title view.
     */
    protected View mVTitle;
    /**
     * The title TextView.
     */
    protected TextView mTvTitle;
    /**
     * The title ImageView.
     */
    protected ImageView mIvTitleClose;
    /**
     * The line bottom title view.
     */
    protected View mViewTitleBottomLine;
    /**
     * The content container.
     */
    protected LinearLayout mLlContentContainer;

    /**
     * The line above key.
     */
    protected View mViewKeyTopLine;
    /**
     * The TextView of negative key.
     */
    protected TextView mTvNegativeKey;
    /**
     * The TextView of positive key.
     */
    protected TextView mTvPositiveKey;
    /**
     * The line center key.
     */
    protected View mViewKeyCenterLine;

    /**
     * The close key listener.
     */
    protected IOnDialogKeyClickListener mCloseListener;
    /**
     * The negative key listener.
     */
    protected IOnDialogKeyClickListener mNegativeListener;
    /**
     * The positive key listener.
     */
    protected IOnDialogKeyClickListener mPositiveListener;

    /**
     * The drawable of dialog background.
     */
    protected Drawable mBackgroundDrawable;
    /**
     * The drawable of title background.
     */
    protected Drawable mTitleBackgroundDrawable;
    /**
     * The visibility state of tile.
     */
    protected int mTitleVisibility = View.VISIBLE;
    /**
     * The visibility state of tile close icon.
     */
    protected int mTitleCloseIconVisibility = View.GONE;
    /**
     * The color of tile close icon.
     */
    @ColorInt
    protected int mTitleCloseIconColor = 0xFF000000;
    /**
     * The title text.
     */
    protected String mTitleText = "title";
    /**
     * The size of title text.
     */
    protected int mTitleTextSize = 18;
    /**
     * The color of title text.
     */
    @ColorInt
    protected int mTitleTextColor = 0xFF000000;

    /**
     * The visibility state of line bottom title.
     */
    protected int mTitleBottomLineVisibility = View.VISIBLE;
    /**
     * The color of line bottom title.
     */
    @ColorInt
    protected int mTitleBottomLineColor = 0x2A000000;
    /**
     * The visibility state of line top key.
     */
    protected int mKeyTopLineVisibility = View.VISIBLE;

    /**
     * The color of line top key.
     */
    @ColorInt
    protected int mKeyTopLineColor = 0x2A000000;
    /**
     * The visibility state of line center key.
     */
    protected int mKeyCenterLineVisibility = View.VISIBLE;
    /**
     * The color of line center key.
     */
    @ColorInt
    protected int mKeyCenterLineColor = 0x2A000000;

    /**
     * The visibility state of negative key.
     */
    protected int mNegativeKeyVisibility = View.VISIBLE;
    /**
     * The drawable of negative key background.
     */
    protected Drawable mNegativeKeyBackgroundDrawable = null;
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
     * The drawable of positive key background.
     */
    protected Drawable mPositiveKeyBackgroundDrawable = null;
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
    protected void setContentView(int layoutId) {
        mContentId = layoutId;
    }

    @Override
    protected void setContentView(@NonNull View contentView) {
        mContentView = contentView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.widget_base_simple_dialog, container, false);
        if (0 != mContentId) {
            mContentView = inflater.inflate(mContentId, container, false);
        }
        initBaseView(mParentView);
        return mParentView;
    }

    @Override
    protected void baseOnClickListener(View v) {
        super.baseOnClickListener(v);
        if (v == mIvTitleClose) {
            onClickCloseKey();
        } else if (v == mTvNegativeKey) {
            onClickNegativeKey();
        } else if (v == mTvPositiveKey) {
            onClickPositiveKey();
        }
    }

    /**
     * Init the base view.
     *
     * @param parent view
     */
    protected void initBaseView(View parent) {
        mLlDialog = findSubViewById(R.id.base_simple_dialog_ll, parent);
        mVTitle = findSubViewById(R.id.base_simple_dialog_title_container, parent);
        mTvTitle = findSubViewById(R.id.base_simple_dialog_title, parent);
        mIvTitleClose = findSubViewById(R.id.base_simple_dialog_title_close, parent);
        mViewTitleBottomLine = findSubViewById(R.id.base_simple_dialog_title_bottom_line, parent);
        mViewKeyTopLine = findSubViewById(R.id.base_simple_dialog_key_top_line, parent);
        mViewKeyCenterLine = findSubViewById(R.id.base_simple_dialog_key_center_line, parent);
        mTvNegativeKey = findSubViewById(R.id.base_simple_dialog_key_cancel, parent);
        mTvPositiveKey = findSubViewById(R.id.base_simple_dialog_key_ok, parent);

        mLlContentContainer = findSubViewById(R.id.base_simple_dialog_container_ll, parent);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mLlContentContainer.addView(mContentView, lp);

        if (null != mBackgroundDrawable) {
            mLlDialog.setBackgroundDrawable(mBackgroundDrawable);
        }

        if (null != mTitleBackgroundDrawable) {
            mVTitle.setBackgroundDrawable(mTitleBackgroundDrawable);
        }

        setTitleVisibility(mTitleVisibility);
        setTitleCloseIconVisibility(mTitleCloseIconVisibility);
        setTitleCloseIconColor(mTitleCloseIconColor);
        setTitle(mTitleText);
        setTitleTextSize(mTitleTextSize);
        setTitleTextColor(mTitleTextColor);
        setTitleBottomLineVisibility(mTitleBottomLineVisibility);
        setTitleBottomLineColor(mTitleBottomLineColor);

        setKeyTopLineVisibility(mKeyTopLineVisibility);
        setKeyTopLineColor(mKeyTopLineColor);
        setKeyCenterLineVisibility(mKeyCenterLineVisibility);
        setKeyCenterLineColor(mKeyCenterLineColor);

        setNegativeKeyVisibility(mNegativeKeyVisibility);
        setNegativeKeyBackground(mNegativeKeyBackgroundDrawable);
        setNegativeKeyText(mNegativeKeyText);
        setNegativeKeyTextSize(mNegativeKeyTextSize);
        if (null != mNegativeKeyTextColors) {
            setNegativeKeyColor(mNegativeKeyTextColors);
        } else {
            setNegativeKeyColor(mNegativeKeyTextColor);
        }

        setPositiveKeyVisibility(mPositiveKeyVisibility);
        setPositiveKeyBackground(mPositiveKeyBackgroundDrawable);
        setPositiveKeyText(mPositiveKeyText);
        setPositiveKeyTextSize(mPositiveKeyTextSize);
        if (null != mPositiveKeyTextColors) {
            setPositiveKeyColor(mPositiveKeyTextColors);
        } else {
            setPositiveKeyColor(mPositiveKeyTextColor);
        }

        setBaseOnClickListener(mIvTitleClose, mTvNegativeKey, mTvPositiveKey);
    }


    /**
     * On click close key.
     */
    protected void onClickCloseKey() {
        if (null != mCloseListener) {
            mCloseListener.onClick(this);
        } else {
            dismiss();
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
     * Set the close key click listener.
     *
     * @param listener the listener
     */
    public BaseSimpleDialogFragment setCloseKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mCloseListener = listener;
        return this;
    }

    /**
     * Set the negative key click listener.
     *
     * @param listener the listener
     */
    public BaseSimpleDialogFragment setNegativeKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mNegativeListener = listener;
        return this;
    }

    /**
     * Set the positive key click listener.
     *
     * @param listener the listener
     */
    public BaseSimpleDialogFragment setPositiveKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mPositiveListener = listener;
        return this;
    }

    /**
     * Set the drawable of dialog background.
     *
     * @param drawable drawable
     */
    public BaseSimpleDialogFragment setBackground(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        if (null != mLlDialog) {
            mLlDialog.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set the drawable of title background.
     *
     * @param drawable drawable
     */
    public BaseSimpleDialogFragment setTitleBackground(Drawable drawable) {
        this.mTitleBackgroundDrawable = drawable;
        if (null != mVTitle) {
            mVTitle.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set the visibility state of title.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setTitleVisibility(int visibility) {
        this.mTitleVisibility = visibility;
        if (null != mVTitle) {
            mVTitle.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set the visibility state of close icon.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setTitleCloseIconVisibility(int visibility) {
        this.mTitleCloseIconVisibility = visibility;
        if (null != mIvTitleClose) {
            mIvTitleClose.setVisibility(visibility);
            if (visibility == View.VISIBLE) {
                mTvTitle.setPadding((int) getResources().getDimension(R.dimen.base_simple_dialog_title_icon_width),
                        mTvTitle.getPaddingTop(),
                        (int) getResources().getDimension(R.dimen.base_simple_dialog_title_icon_width),
                        mTvTitle.getPaddingBottom());
            } else {
                mTvTitle.setPadding((int) getResources().getDimension(R.dimen.base_simple_dialog_title_text_padding_left),
                        mTvTitle.getPaddingTop(),
                        (int) getResources().getDimension(R.dimen.base_simple_dialog_title_text_padding_right),
                        mTvTitle.getPaddingBottom());
            }
        }
        return this;
    }

    /**
     * Set the title text color.
     *
     * @param color the color
     */
    public BaseSimpleDialogFragment setTitleCloseIconColor(@ColorInt int color) {
        this.mTitleCloseIconColor = color;
        if (null != mIvTitleClose) {
            mIvTitleClose.clearColorFilter();
            mIvTitleClose.setColorFilter(mTitleCloseIconColor);
        }
        return this;
    }

    /**
     * Set the title text.
     *
     * @param text text
     */
    public BaseSimpleDialogFragment setTitle(String text) {
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
    public BaseSimpleDialogFragment setTitleTextSize(int size) {
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
    public BaseSimpleDialogFragment setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        if (null != mTvTitle) {
            mTvTitle.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setTitleBottomLineVisibility(int visibility) {
        this.mTitleBottomLineVisibility = visibility;
        if (null != mViewTitleBottomLine) {
            mViewTitleBottomLine.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set color of the line bottom title view.
     *
     * @param color the color
     */
    public BaseSimpleDialogFragment setTitleBottomLineColor(@ColorInt int color) {
        this.mTitleBottomLineColor = color;
        if (null != mViewTitleBottomLine) {
            mViewTitleBottomLine.setBackgroundColor(color);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setKeyTopLineVisibility(int visibility) {
        this.mKeyTopLineVisibility = visibility;
        if (null != mViewKeyTopLine) {
            mViewKeyTopLine.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set color of the line top key.
     *
     * @param color the color
     */
    public BaseSimpleDialogFragment setKeyTopLineColor(@ColorInt int color) {
        this.mKeyTopLineColor = color;
        if (null != mViewKeyTopLine) {
            mViewKeyTopLine.setBackgroundColor(color);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setKeyCenterLineVisibility(int visibility) {
        this.mKeyCenterLineVisibility = visibility;
        if (null != mViewKeyCenterLine) {
            mViewKeyCenterLine.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set color of the line center key.
     *
     * @param color the color
     */
    public BaseSimpleDialogFragment setKeyCenterLineColor(@ColorInt int color) {
        this.mKeyCenterLineColor = color;
        if (null != mViewKeyCenterLine) {
            mViewKeyCenterLine.setBackgroundColor(color);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BaseSimpleDialogFragment setNegativeKeyVisibility(int visibility) {
        this.mNegativeKeyVisibility = visibility;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set the drawable of negative key background.
     *
     * @param drawable drawable
     */
    public BaseSimpleDialogFragment setNegativeKeyBackground(Drawable drawable) {
        this.mNegativeKeyBackgroundDrawable = drawable;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set negative key text.
     *
     * @param text text
     */
    public BaseSimpleDialogFragment setNegativeKeyText(String text) {
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
    public BaseSimpleDialogFragment setNegativeKeyTextSize(int size) {
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
    public BaseSimpleDialogFragment setNegativeKeyColor(@ColorInt int color) {
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
    public BaseSimpleDialogFragment setNegativeKeyColor(ColorStateList colors) {
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
    public BaseSimpleDialogFragment setPositiveKeyVisibility(int visibility) {
        this.mPositiveKeyVisibility = visibility;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setVisibility(visibility);
        }
        return this;
    }

    /**
     * Set the drawable of positive key background.
     *
     * @param drawable drawable
     */
    public BaseSimpleDialogFragment setPositiveKeyBackground(Drawable drawable) {
        this.mPositiveKeyBackgroundDrawable = drawable;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setBackgroundDrawable(drawable);
        }
        return this;
    }

    /**
     * Set positive key text.
     *
     * @param text text
     */
    public BaseSimpleDialogFragment setPositiveKeyText(String text) {
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
    public BaseSimpleDialogFragment setPositiveKeyTextSize(int size) {
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
    public BaseSimpleDialogFragment setPositiveKeyColor(@ColorInt int color) {
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
    public BaseSimpleDialogFragment setPositiveKeyColor(ColorStateList colors) {
        this.mPositiveKeyTextColors = colors;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextColor(colors);
        }
        return this;
    }

}
