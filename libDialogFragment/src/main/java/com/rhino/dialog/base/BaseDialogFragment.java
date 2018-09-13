package com.rhino.dialog.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.rhino.dialog.R;
import com.rhino.dialog.impl.IOnDialogListener;


/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
@SuppressWarnings("unchecked")
public abstract class BaseDialogFragment extends DialogFragment {

    /**
     * The top or bottom margin of popup window.
     **/
    public static final int DF_POPUP_WINDOW_MARGIN_TOP_OR_BOTTOM = 13;
    /**
     * the popup window margin top or bottom.
     **/
    public int mMarginTopOrBottom = DF_POPUP_WINDOW_MARGIN_TOP_OR_BOTTOM;
    /**
     * The left or right margin of popup window.
     **/
    public static final int DF_POPUP_WINDOW_MARGIN_RIGHT_OR_LEFT = 13;
    /**
     * The right or left margin of popup window.
     **/
    public int mMarginRightOrLeft = DF_POPUP_WINDOW_MARGIN_RIGHT_OR_LEFT;

    /**
     * Align this view bottom and align window left.
     **/
    public static final int ALIGN_TYPE_THIS_BOTTOM_WINDOW_LEFT = 0x1;
    /**
     * Align this view bottom and center.
     **/
    public static final int ALIGN_TYPE_THIS_BOTTOM_CENTER = 0x2;
    /**
     * Align this view bottom and align window right.
     **/
    public static final int ALIGN_TYPE_THIS_BOTTOM_WINDOW_RIGHT = 0x3;
    /**
     * Align this view top and align window left.
     **/
    public static final int ALIGN_TYPE_THIS_TOP_WINDOW_LEFT = 0x4;
    /**
     * Align this view top and align window center.
     **/
    public static final int ALIGN_TYPE_THIS_TOP_CENTER = 0x5;
    /**
     * Align this view top and align window right.
     **/
    public static final int ALIGN_TYPE_THIS_TOP_WINDOW_RIGHT = 0x6;
    /**
     * The type of align, ALIGN_TYPE_XXX.
     **/
    public int mAlignType;
    /**
     * Align this view.
     */
    public View mAlignView;

    /**
     * The parent view.
     */
    public View mParentView;
    /**
     * The parent view layout id.
     */
    public int mParentLayoutId;
    /**
     * The parent view width.
     **/
    public int mParentViewWidth;

    /**
     * The parent view height.
     **/
    public int mParentViewHeight;
    /**
     * The IOnDialogListener.
     */
    public IOnDialogListener mIOnDialogListener;
    /**
     * The OnClickListener.
     */
    public View.OnClickListener mBaseOnClickListener;
    /**
     * The OnLongClickListener.
     */
    public View.OnLongClickListener mBaseOnLongClickListener;
    /**
     * The gravity of window.
     */
    public int mWindowGravity = Gravity.CENTER;
    /**
     * The window view width.
     */
    public int mWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
    /**
     * The window view height.
     */
    public int mWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    /**
     * Whether can be canceled when touch outside dialog.
     */
    public boolean mOutsideCancelable = true;
    /**
     * The Window.
     */
    public Window mWindow;


    /**
     * Set the parent view.
     * {@link #setContentView(int)}
     * {@link #setContentView(View)}}
     */
    protected abstract void setContent();

    /**
     * Init the data.
     *
     * @return true success, false failed
     */
    protected abstract boolean initData();

    /**
     * Init the view.
     */
    protected abstract void initView();

    public BaseDialogFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationScaleAlphaDialog);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!initData()) {
            dismiss();
        } else {
            setContent();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mParentLayoutId != 0) {
            mParentView = inflater.inflate(mParentLayoutId, container, false);
        }
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onParentClick();
            }
        });
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
        if (mWindow != null) {
            WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
            layoutParams.gravity = mWindowGravity;
            layoutParams.width = mWindowWidth;
            layoutParams.height = mWindowHeight;
            mWindow.setAttributes(layoutParams);
        }
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onStart(this);
        }
        layoutParentViewWhenNeedAlign();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onResume(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onPause(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onStop(this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onDismiss(this);
        }
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        this.mOutsideCancelable = cancelable;
    }

    /**
     * Parent click.
     */
    public void onParentClick() {

    }

    /**
     * Init Window.
     */
    public void initWindow() {
        mWindow = getDialog().getWindow();
        if (mWindow != null) {
            mWindow.getDecorView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mOutsideCancelable && event.getAction() == MotionEvent.ACTION_UP) {
                        dismiss();
                        return true;
                    }
                    return true;
                }
            });
        }
    }

    /**
     * Set the align view.
     * @param view view
     */
    public void setAlignView(View view) {
        this.mAlignView = view;
    }

    /**
     * Set the align type.
     * @param type type ALIGN_TYPE_XXX
     */
    public void setAlignType(int type) {
        this.mAlignType = type;
    }

    /**
     * Layout the parent view when need align.
     */
    public void layoutParentViewWhenNeedAlign() {
        if (mAlignView == null) {
            return;
        }
        if (mParentViewWidth <= 0 || mParentViewHeight <= 0) {
            mParentViewWidth = mParentView.getWidth();
            mParentViewHeight = mParentView.getHeight();
            if ((mParentViewWidth <= 0 || mParentViewHeight <= 0)
                    && mParentView instanceof ViewGroup
                    && ((ViewGroup) mParentView).getChildCount() > 0) {
                // use first child width and height
                View v = ((ViewGroup) mParentView).getChildAt(0);
                mParentViewWidth = v.getLayoutParams().width;
                if (mParentViewWidth <= 0) {
                    mParentViewWidth = v.getMeasuredWidth();
                }
                mParentViewHeight = v.getLayoutParams().height;
                if (mParentViewHeight <= 0) {
                    mParentViewHeight = v.getMinimumHeight();
                }
            }
        }
        if (mParentViewWidth <= 0 || mParentViewHeight <= 0) {
            throw new RuntimeException("mParentViewWidth: " + mParentViewWidth + ", mParentViewHeight: " + mParentViewHeight);
        }
        int topMargin = 0;
        int leftMargin = 0;
        int[] alignViewLocation = new int[2];
        mAlignView.getLocationOnScreen(alignViewLocation);
        setWindowGravity(Gravity.TOP | Gravity.START);
        switch (mAlignType) {
            case ALIGN_TYPE_THIS_BOTTOM_WINDOW_LEFT:
                leftMargin = mMarginRightOrLeft + alignViewLocation[0];
                topMargin = mMarginTopOrBottom + alignViewLocation[1] - getStatusBarHeight(getContext()) + mAlignView.getHeight();
                break;
            case ALIGN_TYPE_THIS_BOTTOM_CENTER:
                leftMargin = alignViewLocation[0] + mAlignView.getWidth() / 2 - mParentViewWidth / 2;
                topMargin = mMarginTopOrBottom + alignViewLocation[1] - getStatusBarHeight(getContext()) + mAlignView.getHeight();
                break;
            case ALIGN_TYPE_THIS_BOTTOM_WINDOW_RIGHT:
                leftMargin = alignViewLocation[0] + mAlignView.getWidth() - mMarginRightOrLeft - mParentViewWidth;
                topMargin = mMarginTopOrBottom + alignViewLocation[1] - getStatusBarHeight(getContext()) + mAlignView.getHeight();
                break;
            case ALIGN_TYPE_THIS_TOP_WINDOW_LEFT:
                leftMargin = mMarginRightOrLeft + alignViewLocation[0];
                topMargin = alignViewLocation[1] - getStatusBarHeight(getContext()) - mParentViewHeight - mMarginTopOrBottom;
                break;
            case ALIGN_TYPE_THIS_TOP_CENTER:
                leftMargin = alignViewLocation[0] + mAlignView.getWidth() / 2 - mParentViewWidth / 2;
                topMargin = alignViewLocation[1] - getStatusBarHeight(getContext()) - mParentViewHeight - mMarginTopOrBottom;
                break;
            case ALIGN_TYPE_THIS_TOP_WINDOW_RIGHT:
                leftMargin = alignViewLocation[0] + mAlignView.getWidth() - mMarginRightOrLeft - mParentViewWidth;
                topMargin = alignViewLocation[1] - getStatusBarHeight(getContext()) - mParentViewHeight - mMarginTopOrBottom;
                break;
            default:
                break;
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mParentView.getLayoutParams();
        params.topMargin = topMargin;
        params.leftMargin = leftMargin;
        params.width = mParentViewWidth;
        params.height = mParentViewHeight + topMargin > getScreenHeight(getContext()) - getStatusBarHeight(getContext())
                ? getScreenHeight(getContext()) - getStatusBarHeight(getContext()) - topMargin - mMarginTopOrBottom
                : mParentViewHeight;

    }

    /**
     * Set listener of dialog.
     *
     * @param listener IOnDialogListener
     */
    public void setIOnDialogListener(IOnDialogListener listener) {
        this.mIOnDialogListener = listener;
    }

    /**
     * Set gravity of window content.
     *
     * @param gravity {@link Gravity}
     */
    public void setWindowGravity(int gravity) {
        this.mWindowGravity = gravity;
        if (mWindow != null) {
            WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
            layoutParams.gravity = mWindowGravity;
            mWindow.setAttributes(layoutParams);
        }
    }

    /**
     * Set the window view width.
     * @param width width {@link WindowManager.LayoutParams}
     */
    public void setWindowWidth(int width) {
        this.mWindowWidth = width;
    }

    /**
     * Set the window view height.
     * @param height height {@link WindowManager.LayoutParams}
     */
    public void setWindowHeight(int height) {
        this.mWindowHeight = height;
    }

    /**
     * Set the parent view width.
     * @param width width
     */
    public void setParentViewWidth(int width) {
        this.mParentViewWidth = width;
    }

    /**
     * Set the parent view height.
     * @param height height
     */
    public void setParentViewHeight(int height) {
        this.mParentViewHeight = height;
    }

    /**
     * Hide soft input from window.
     *
     * @param activity FragmentActivity
     */
    public void hideSoftInputFromWindow(FragmentActivity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Show this dialog.
     *
     * @param activity FragmentActivity
     */
    public void show(FragmentActivity activity) {
        hideSoftInputFromWindow(activity);
        show(activity.getSupportFragmentManager(), getClass().getName());
    }

    /**
     * get the status height
     *
     * @param ctx the context
     * @return the status bar height
     */
    public int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * get screen height
     *
     * @param ctx the context
     * @return the screen height
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context ctx) {
        WindowManager manager = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        return display.getHeight();
    }

    /**
     * Set the parent view.
     *
     * @param layoutId the layout id
     */
    protected void setContentView(@LayoutRes int layoutId) {
        mParentLayoutId = layoutId;
    }

    /**
     * Set the parent view.
     *
     * @param contentView the view
     */
    protected void setContentView(@NonNull View contentView) {
        mParentView = contentView;
    }

    /**
     * Find the view by view id.
     *
     * @param id view id
     * @return the view
     * @see View#findViewById(int)
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findSubViewById(@IdRes int id) {
        return (T) mParentView.findViewById(id);
    }

    /**
     * Find the view by id.
     *
     * @param id     view id
     * @param parent the parent view
     * @return the view
     * @see View#findViewById(int)
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findSubViewById(@IdRes int id, View parent) {
        return (T) parent.findViewById(id);
    }

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param views the view
     */
    final protected void setBaseOnClickListener(View... views) {
        if (views != null) {
            for (View v : views) {
                if (null != v) {
                    v.setOnClickListener(getBaseOnClickListener());
                }
            }
        }
    }

    /**
     * Register a callback to be invoked when this view is clicked and held. If this view is not
     * long clickable, it becomes long clickable.
     *
     * @param views the view
     */
    final protected void setBaseOnLongClickListener(View... views) {
        if (views != null) {
            for (View v : views) {
                if (null != v) {
                    v.setOnLongClickListener(getBaseOnLongClickListener());
                }
            }
        }
    }

    /**
     * Get the OnClickListener.
     *
     * @return the OnClickListener
     */
    final protected View.OnClickListener getBaseOnClickListener() {
        if (mBaseOnClickListener == null) {
            mBaseOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseOnClickListener(v);
                }
            };
        }
        return mBaseOnClickListener;
    }

    /**
     * Get the OnLongClickListener.
     *
     * @return the OnLongClickListener
     */
    final protected View.OnLongClickListener getBaseOnLongClickListener() {
        if (mBaseOnLongClickListener == null) {
            mBaseOnLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return baseOnLongClickListener(v);
                }
            };
        }
        return mBaseOnLongClickListener;
    }

    /**
     * Deal the click listener.
     *
     * @param v the click view
     */
    protected void baseOnClickListener(View v) {
    }

    /**
     * Deal the long click listener.
     *
     * @param v the long click view
     */
    protected boolean baseOnLongClickListener(View v) {
        return false;
    }


}
