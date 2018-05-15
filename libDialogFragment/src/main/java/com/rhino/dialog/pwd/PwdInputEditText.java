package com.rhino.dialog.pwd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;

import java.lang.reflect.Field;


/**
 * <p>The custom password view extends EditText.</p>
 *
 * @author LuoLin
 * @since Create on 2017/9/27.
 **/
public class PwdInputEditText extends AppCompatEditText {

    /**
     * The anim time when input or delete.
     **/
    private static final int DEFAULT_PAINT_LAST_ARC_DURATION = 200;
    /**
     * The default color of rim stroke.
     **/
    private static final int DEFAULT_RIM_RECT_COLOR = 0x66000000;
    /**
     * The default rim corner(dp).
     **/
    private static final float DEFAULT_RIM_CORNER = 2;
    /**
     * The default width of rim stroke(dp).
     **/
    private static final float DEFAULT_RIM_STROKE_WIDTH = 0.5f;
    /**
     * The default max input length.
     **/
    private static final int DEFAULT_MAX_LENGTH = 6;

    /**
     * The height of view.
     **/
    private int mViewHeight;
    /**
     * The width of view.
     **/
    private int mViewWidth;

    /**
     * The paint of background.
     **/
    private Paint mBackgroundPaint;
    /**
     * The rect of background.
     **/
    private RectF mBackgroundRect;

    /**
     * The paint of rim.
     **/
    private Paint mRimPaint;
    /**
     * The rect of rim.
     **/
    private RectF mRimRect;
    /**
     * The width of rim stroke.
     **/
    private float mRimWidth;
    /**
     * The corner of rim stroke.
     **/
    private float mRimCorner;

    /**
     * The paint of input.
     **/
    private Paint mInputPaint;
    /**
     * The picture show when password input.
     **/
    private Bitmap mInputBitmap = null;

    /**
     * The anim when input or delete.
     **/
    private PaintLastArcAnim mPaintLastArcAnim;
    /**
     * The percent of anim when input or delete.
     **/
    private float mInterpolatedTime;

    /**
     * The max length of password.
     **/
    private int mMaxLength = DEFAULT_MAX_LENGTH;
    /**
     * The current input length of password.
     **/
    private int mCurrentLength;
    /**
     * Whether input change.
     **/
    private boolean mIsInput = true;
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


    public PwdInputEditText(Context context) {
        this(context, null);
    }

    public PwdInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = widthSize;
        } else {
            mViewWidth = getWidth();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = heightSize;
        } else {
            mViewHeight = getHeight();
        }
        initView(mViewWidth, mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mIsInput = text.toString().length() >= this.mCurrentLength;
        this.mCurrentLength = text.toString().length();
        if (mCurrentLength <= mMaxLength) {
            if (mPaintLastArcAnim != null) {
                clearAnimation();
                startAnimation(mPaintLastArcAnim);
            } else {
                invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawBackground(canvas);
        drawRimStroke(canvas);
        drawGridVerLine(canvas);
        drawInputView(canvas);
        canvas.restore();
    }

    /**
     * Draw background
     **/
    private void drawBackground(Canvas canvas) {
        canvas.save();
        canvas.drawRoundRect(mBackgroundRect, 0, 0, mBackgroundPaint);
        canvas.restore();
    }

    /**
     * Draw rim stroke
     **/
    private void drawRimStroke(Canvas canvas) {
        canvas.save();
        canvas.drawRoundRect(mRimRect, mRimCorner, mRimCorner, mRimPaint);
        canvas.restore();
    }

    /**
     * Draw grid vertical line
     **/
    private void drawGridVerLine(Canvas canvas) {
        canvas.save();
        float x;
        for (int i = 1; i < mMaxLength; i++) {
            x = mViewWidth * i / mMaxLength;
            canvas.drawLine(x, mRimWidth, x, mViewHeight - mRimWidth, mRimPaint);
        }
        canvas.restore();
    }

    /**
     * Draw input view
     **/
    private void drawInputView(Canvas canvas) {
        canvas.save();
        float cx, cy = mViewHeight / 2;
        float half = mViewWidth / mMaxLength / 2;

        int color = getTextColors().getDefaultColor();
        mInputPaint.setColor(color);

        if (mIsShowPwd) {
            // show password
            for (int i = 0; i < mCurrentLength; i++) {
                cx = mViewWidth * i / mMaxLength + half;
                if (0 < mShowPicId) {
                    // show custom icon
                    int width = (int) getTextSize();
                    if (mInputBitmap == null || width != mInputBitmap.getWidth()) {
                        mInputBitmap = BitmapFactory.decodeResource(getContext().getResources(), mShowPicId);
                        mInputBitmap = zoomBitmapByWidth(mInputBitmap, width);
                    }
                    if (null != mInputBitmap) {
                        canvas.drawBitmap(mInputBitmap, cx - mInputBitmap.getWidth() / 2, cy - mInputBitmap.getHeight() / 2, mInputPaint);
                    }
                } else {
                    mInputPaint.setTextSize(getTextSize());
                    String text = TextUtils.isEmpty(mShowStr) ? String.valueOf(getText().toString().charAt(i)) : mShowStr;
                    canvas.drawText(text, cx - getFontWidth(mInputPaint, text) / 2.0f, cy + getFontHeight(mInputPaint, text) / 2.0f, mInputPaint);
                }
            }
        } else {
            // not show password
            float radius = getTextSize() / 2;
            for (int i = 0; i < mMaxLength; i++) {
                cx = mViewWidth * i / mMaxLength + half;
                if (mIsInput) {
                    // input
                    if (i < mCurrentLength - 1) {
                        canvas.drawCircle(cx, cy, radius, mInputPaint);
                    } else if (i == mCurrentLength - 1) {
                        canvas.drawCircle(cx, cy, radius * mInterpolatedTime, mInputPaint);
                    }
                } else {
                    // delete
                    if (i < mCurrentLength) {
                        canvas.drawCircle(cx, cy, radius, mInputPaint);
                    } else if (i == mCurrentLength) {
                        canvas.drawCircle(cx, cy, radius - radius * mInterpolatedTime, mInputPaint);
                    }
                }
            }
        }
        canvas.restore();
    }

    private void init() {
        mMaxLength = getMaxLength();

        mPaintLastArcAnim = new PaintLastArcAnim();
        mPaintLastArcAnim.setDuration(DEFAULT_PAINT_LAST_ARC_DURATION);

        float scale = getContext().getResources().getDisplayMetrics().density;
        mRimWidth = (DEFAULT_RIM_STROKE_WIDTH * scale + 0.5f);
        mRimCorner = (DEFAULT_RIM_CORNER * scale + 0.5f);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(Color.WHITE);

        mRimPaint = new Paint();
        mRimPaint.setAntiAlias(true);
        mRimPaint.setStyle(Paint.Style.STROKE);
        mRimPaint.setColor(DEFAULT_RIM_RECT_COLOR);
        mRimPaint.setStrokeWidth(mRimWidth);

        mRimRect = new RectF();

        mInputPaint = new Paint();
        mInputPaint.setAntiAlias(true);
        mInputPaint.setStyle(Paint.Style.FILL);

        setCursorVisible(false);
        setFocusableInTouchMode(true);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    /**
     * Init view size
     **/
    private void initView(int width, int height) {
        if (0 >= width || 0 >= height) {
            return;
        }

        mBackgroundRect = new RectF();
        mBackgroundRect.left = 0;
        mBackgroundRect.right = width;
        mBackgroundRect.top = 0;
        mBackgroundRect.bottom = height;

        mRimRect = new RectF();
        mRimRect.left = mRimWidth;
        mRimRect.right = width - mRimWidth;
        mRimRect.top = mRimWidth;
        mRimRect.bottom = height - mRimWidth;
    }

    /**
     * The anim when input or delete.
     **/
    private class PaintLastArcAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            postInvalidate();
        }
    }

    /**
     * Get the text width.
     **/
    private float getFontWidth(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /**
     * Get the text height.
     **/
    private float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    /**
     * Zoom bitmap
     **/
    private Bitmap zoomBitmapByWidth(@NonNull Bitmap b, int w) {
        float s = b.getHeight() / b.getWidth();
        b = Bitmap.createScaledBitmap(b, w, (int) (w * s), true);
        return b;
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
        mBackgroundPaint.setColor(color);
    }

    /**
     * Set whether show password.
     *
     * @param show True show password
     */
    public void setShowPassword(boolean show) {
        this.mIsShowPwd = show;
        this.mShowPicId = 0;
        this.mShowStr = null;
        postInvalidate();
    }

    /**
     * Set string show when password input.
     *
     * @param showStr the string show when password input
     */
    public void setShowPassword(String showStr) {
        this.mIsShowPwd = true;
        this.mShowPicId = 0;
        this.mShowStr = showStr;
        postInvalidate();
    }

    /**
     * Set picture show when password input.
     *
     * @param showPicId the picture show when password input
     */
    public void setShowPassword(@DrawableRes int showPicId) {
        this.mIsShowPwd = true;
        this.mShowPicId = showPicId;
        this.mShowStr = null;
        postInvalidate();
    }

    /**
     * Set the corner.
     *
     * @param corner corner
     */
    public void setRimCorner(@Dimension int corner) {
        this.mRimCorner = corner;
    }

    /**
     * Set the color of rim stroke.
     *
     * @param color color
     */
    public void setRimStrokeColor(int color) {
        this.mRimPaint.setColor(color);
    }

    /**
     * Set the max length of password.
     *
     * @param maxLength the max length of password
     */
    public void setMaxLength(int maxLength) {
        this.mMaxLength = maxLength;
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * Get max input length.
     **/
    public int getMaxLength() {
        try {
            InputFilter[] inputFilters = getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            return (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMaxLength;
    }

}
