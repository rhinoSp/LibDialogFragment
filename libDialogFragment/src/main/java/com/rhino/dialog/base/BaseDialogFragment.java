package com.rhino.dialog.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.rhino.dialog.R;
import com.rhino.dialog.impl.IOnDialogListener;


/**
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
@SuppressWarnings("unchecked")
public abstract class BaseDialogFragment extends DialogFragment {

    /**
     * The parent view.
     */
    protected View mParentView;
    /**
     * The parent view layout id.
     */
    protected int mParentLayoutId;
    /**
     * The IOnDialogListener.
     */
    private IOnDialogListener mIOnDialogListener;
    /**
     * The OnClickListener.
     */
    private View.OnClickListener mBaseOnClickListener;
    /**
     * The OnLongClickListener.
     */
    private View.OnLongClickListener mBaseOnLongClickListener;
    /**
     * The gravity of dialog content.
     */
    private int mContentGravity = Gravity.CENTER;
    /**
     * Whether can be canceled when touch outside dialog.
     */
    private boolean mOutsideCancelable = true;


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationScaleAlphaDialog);
        if (!initData()) {
            dismiss();
        } else {
            setContent();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mParentLayoutId != 0) {
            mParentView = inflater.inflate(mParentLayoutId, container, false);
        }
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = mContentGravity;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
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
        if (null != mIOnDialogListener) {
            mIOnDialogListener.onStart(this);
        }
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
     * Set listener of dialog.
     *
     * @param listener IOnDialogListener
     */
    public void setIOnDialogListener(IOnDialogListener listener) {
        this.mIOnDialogListener = listener;
    }

    /**
     * Set gravity of dialog content.
     *
     * @param gravity {@link Gravity}
     */
    public void setContentGravity(int gravity) {
        this.mContentGravity = gravity;
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
