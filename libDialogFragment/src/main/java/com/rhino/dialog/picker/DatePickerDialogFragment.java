package com.rhino.dialog.picker;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rhino.dialog.R;
import com.rhino.dialog.base.BaseDialogFragment;
import com.rhino.dialog.base.BaseSimpleDialogFragment;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;
import com.rhino.wheel.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author LuoLin
 * @since Create on 2018/10/26.
 */
public class DatePickerDialogFragment extends BaseDialogFragment {

    public static final long MILLISECONDS_PER_YEAR = 31536000000L;

    public static final int STYLE_YYYY_MM_DD_HH_MM_SS = 1;
    public static final int STYLE_YYYY_MM_DD_HH_MM = 2;
    public static final int STYLE_YYYY_MM_DD = 3;
    public static final int STYLE_HH_MM_SS = 4;
    public static final int STYLE_HH_MM = 5;
    public int mStyle = STYLE_YYYY_MM_DD_HH_MM_SS;
    public int mYearCount = 30;
    public boolean mYearOnlyCurrentBefore = false;
    public boolean mYearOnlyCurrentAfter = false;

    /**
     * The title text.
     */
    private String mTitleText;
    /**
     * The size of title text.
     */
    protected int mTitleTextSize = 16;
    /**
     * The color of title text.
     */
    @ColorInt
    protected int mTitleTextColor = 0xFF000000;
    /**
     * The negative key listener.
     */
    protected IOnDialogKeyClickListener mNegativeListener;
    /**
     * The text of negative key.
     */
    protected String mNegativeKeyText = "取消";
    /**
     * The size of negative key text.
     */
    protected int mNegativeKeyTextSize = 14;
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
     * The positive key listener.
     */
    protected IOnDialogKeyClickListener mPositiveListener;
    /**
     * The text of positive key.
     */
    protected String mPositiveKeyText = "确认";
    /**
     * The size of positive key text.
     */
    protected int mPositiveKeyTextSize = 14;
    /**
     * The color of positive key text.
     */
    @ColorInt
    protected int mPositiveKeyTextColor = 0xFF000000;
    /**
     * The colorStateList of positive key text.
     */
    protected ColorStateList mPositiveKeyTextColors = null;

    public TextView mTvNegativeKey;
    public TextView mTvPositiveKey;
    public TextView mTvTitle;
    public WheelView mWvYear, mWvMonth, mWvDay, mWvHour, mWvMinute, mWvSecond;
    public String[] yearArr;
    public int currentYear = -1;
    public int currentMonth = -1;
    public int currentDay = -1;
    public int currentHour = -1;
    public int currentMinute = -1;
    public int currentSecond = -1;

    public DatePickerDialogFragment() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AnimationTranBottomDialog);
        setWindowGravity(Gravity.BOTTOM);
        setWindowWidth(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void setContent() {
        setContentView(R.layout.widget_date_picker_dialog);
    }

    @Override
    protected boolean initData() {
        return true;
    }

    @Override
    protected void initView() {
        mTvTitle = findSubViewById(R.id.tv_title);
        mTvNegativeKey = findSubViewById(R.id.bt_cancel);
        mTvPositiveKey = findSubViewById(R.id.bt_ok);
        mWvYear = findSubViewById(R.id.wv_year);
        mWvMonth = findSubViewById(R.id.wv_month);
        mWvDay = findSubViewById(R.id.wv_day);
        mWvHour = findSubViewById(R.id.wv_hour);
        mWvMinute = findSubViewById(R.id.wv_minute);
        mWvSecond = findSubViewById(R.id.wv_second);
        init();
        setTitle(mTitleText);
        setTitleTextSize(mTitleTextSize);
        setTitleTextColor(mTitleTextColor);
        setNegativeKeyText(mNegativeKeyText);
        setNegativeKeyTextSize(mNegativeKeyTextSize);
        if (null != mNegativeKeyTextColors) {
            setNegativeKeyColor(mNegativeKeyTextColors);
        } else {
            setNegativeKeyColor(mNegativeKeyTextColor);
        }
        setPositiveKeyText(mPositiveKeyText);
        setPositiveKeyTextSize(mPositiveKeyTextSize);
        if (null != mPositiveKeyTextColors) {
            setPositiveKeyColor(mPositiveKeyTextColors);
        } else {
            setPositiveKeyColor(mPositiveKeyTextColor);
        }
        setBaseOnClickListener(mTvNegativeKey, mTvPositiveKey);
    }

    @Override
    protected void baseOnClickListener(View v) {
        super.baseOnClickListener(v);
        if (v == mTvNegativeKey) {
            onClickNegativeKey();
        } else if (v == mTvPositiveKey) {
            onClickPositiveKey();
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

    public void init() {
        switch (mStyle) {
            case STYLE_YYYY_MM_DD_HH_MM_SS:
                initDefaultYear();
                initDefaultMoth();
                initDefaultDay();
                initDefaultHour();
                initDefaultMinute();
                initDefaultSecond();
                break;
            case STYLE_YYYY_MM_DD_HH_MM:
                mWvSecond.setVisibility(View.GONE);
                initDefaultYear();
                initDefaultMoth();
                initDefaultDay();
                initDefaultHour();
                initDefaultMinute();
                break;
            case STYLE_YYYY_MM_DD:
                mWvHour.setVisibility(View.GONE);
                mWvMinute.setVisibility(View.GONE);
                mWvSecond.setVisibility(View.GONE);
                initDefaultYear();
                initDefaultMoth();
                initDefaultDay();
                break;
            case STYLE_HH_MM_SS:
                mWvYear.setVisibility(View.GONE);
                mWvMonth.setVisibility(View.GONE);
                mWvDay.setVisibility(View.GONE);
                initDefaultHour();
                initDefaultMinute();
                initDefaultSecond();
                break;
            case STYLE_HH_MM:
                mWvYear.setVisibility(View.GONE);
                mWvMonth.setVisibility(View.GONE);
                mWvDay.setVisibility(View.GONE);
                mWvSecond.setVisibility(View.GONE);
                initDefaultHour();
                initDefaultMinute();
                initDefaultSecond();
                break;
            default:
                break;
        }
    }

    /**
     * Set the title text.
     *
     * @param text text
     */
    public DatePickerDialogFragment setTitle(String text) {
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
    public DatePickerDialogFragment setTitleTextSize(int size) {
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
    public DatePickerDialogFragment setTitleTextColor(@ColorInt int color) {
        this.mTitleTextColor = color;
        if (null != mTvTitle) {
            mTvTitle.setTextColor(color);
        }
        return this;
    }

    /**
     * Set the negative key click listener.
     *
     * @param listener the listener
     */
    public DatePickerDialogFragment setNegativeKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mNegativeListener = listener;
        return this;
    }

    /**
     * Set the positive key click listener.
     *
     * @param listener the listener
     */
    public DatePickerDialogFragment setPositiveKeyClickListener(IOnDialogKeyClickListener listener) {
        this.mPositiveListener = listener;
        return this;
    }

    /**
     * Set negative key text.
     *
     * @param text text
     */
    public DatePickerDialogFragment setNegativeKeyText(String text) {
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
    public DatePickerDialogFragment setNegativeKeyTextSize(int size) {
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
    public DatePickerDialogFragment setNegativeKeyColor(@ColorInt int color) {
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
    public DatePickerDialogFragment setNegativeKeyColor(ColorStateList colors) {
        this.mNegativeKeyTextColors = colors;
        if (null != mTvNegativeKey) {
            mTvNegativeKey.setTextColor(colors);
        }
        return this;
    }

    /**
     * Set positive key text.
     *
     * @param text text
     */
    public DatePickerDialogFragment setPositiveKeyText(String text) {
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
    public DatePickerDialogFragment setPositiveKeyTextSize(int size) {
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
    public DatePickerDialogFragment setPositiveKeyColor(@ColorInt int color) {
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
    public DatePickerDialogFragment setPositiveKeyColor(ColorStateList colors) {
        this.mPositiveKeyTextColors = colors;
        if (null != mTvPositiveKey) {
            mTvPositiveKey.setTextColor(colors);
        }
        return this;
    }

    public void setStyle(int style) {
        this.mStyle = style;
        if (mWvYear != null) {
            init();
        }
    }

    public void setYearCount(int mYearCount) {
        this.mYearCount = mYearCount;
    }

    public void setYearOnlyCurrentBefore(boolean mYearOnlyCurrentBefore) {
        this.mYearOnlyCurrentBefore = mYearOnlyCurrentBefore;
    }

    public void setYearOnlyCurrentAfter(boolean mYearOnlyCurrentAfter) {
        this.mYearOnlyCurrentAfter = mYearOnlyCurrentAfter;
    }

    public void initDefaultYear() {
        int yearCountEnd = mYearOnlyCurrentBefore ? 0 : mYearCount;
        int yearCountStart = mYearOnlyCurrentAfter ? 0 : mYearCount;
        long start = System.currentTimeMillis() - yearCountStart * MILLISECONDS_PER_YEAR;
        long end = System.currentTimeMillis() + yearCountEnd * MILLISECONDS_PER_YEAR;
        initYear(start, end);
    }

    public void initYear(long start, long end) {
        int currentValue = 0;
        int index = 0;
        if (currentYear < 0) {
            currentYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        List<String> list = new ArrayList<>();
        for (long timestamp = start; timestamp <= end; ) {
            String year = formatTime(timestamp, "yyyy");
            if (year.equals(String.valueOf(currentYear))) {
                currentValue = index + 1;
            }
            list.add(year);
            timestamp += MILLISECONDS_PER_YEAR;
            index++;
        }
        yearArr = list.toArray(new String[]{});
        mWvYear.setDisplayedValues(yearArr);
        mWvYear.setValue(currentValue);
        mWvYear.setOnValueChangedListener(new WheelView.OnValueChangeListener() {
            @Override
            public void onValueChange(WheelView wheelView, int oldVal, int newVal) {
                initDay(Integer.parseInt(yearArr[newVal - 1]), mWvMonth.getValue());
            }
        });
    }

    public void initDefaultMoth() {
        initMonth();
        if (currentMonth < 0) {
            currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }
        mWvMonth.setValue(currentMonth);
    }

    public void initMonth() {
        mWvMonth.setMinValue(1);
        mWvMonth.setMaxValue(12);
        mWvMonth.setOnValueChangedListener(new WheelView.OnValueChangeListener() {
            @Override
            public void onValueChange(WheelView wheelView, int oldVal, int newVal) {
                initDay(Integer.parseInt(yearArr[mWvYear.getValue() - 1]), newVal);
            }
        });
    }

    public void initDefaultDay() {
        if (currentYear < 0) {
            currentYear = Calendar.getInstance().get(Calendar.YEAR);
        }
        if (currentMonth < 0) {
            currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }
        if (currentDay < 0) {
            currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        initDay(currentYear, currentMonth);
        mWvDay.setValue(currentDay);
    }

    public void initDay(int year, int month) {
        mWvDay.setMinValue(1);
        int newMaxDay = getMonthOfDay(year, month);
        int lastMaxDay = mWvDay.getMaxValue();
        if (lastMaxDay != newMaxDay) {
            mWvDay.setMaxValue(newMaxDay);
            int value = mWvDay.getValue();
            if (value > newMaxDay) {
                mWvDay.setValue(newMaxDay);
            }
        }
    }

    public void initDefaultHour() {
        if (currentHour < 0) {
            currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        }
        mWvHour.setValue(currentHour + 1);
    }

    public void initDefaultMinute() {
        if (currentMinute < 0) {
            currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        }
        mWvMinute.setValue(currentMinute + 1);
    }

    public void initDefaultSecond() {
        if (currentSecond < 0) {
            currentSecond = Calendar.getInstance().get(Calendar.SECOND);
        }
        mWvSecond.setValue(currentSecond + 1);
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public void setCurrentHour(int currentHour) {
        this.currentHour = currentHour;
    }

    public void setCurrentMinute(int currentMinute) {
        this.currentMinute = currentMinute;
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }

    public static String formatTime(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static int getMonthOfDay(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                int day;
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    day = 29;
                } else {
                    day = 28;
                }
                return day;
            default:
                return 0;
        }
    }

    @NonNull
    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        switch (mStyle) {
            case STYLE_YYYY_MM_DD_HH_MM_SS:
                calendar.set(Calendar.SECOND, mWvSecond.getValue() - 1);
            case STYLE_YYYY_MM_DD_HH_MM:
                calendar.set(Calendar.HOUR_OF_DAY, mWvHour.getValue() - 1);
                calendar.set(Calendar.MINUTE, mWvMinute.getValue() - 1);
            case STYLE_YYYY_MM_DD:
                calendar.set(Calendar.YEAR, Integer.parseInt(yearArr[mWvYear.getValue() - 1]));
                calendar.set(Calendar.MONTH, mWvMonth.getValue() - 1);
                calendar.set(Calendar.DAY_OF_MONTH, mWvDay.getValue());
                break;
            case STYLE_HH_MM_SS:
                calendar.set(Calendar.SECOND, mWvSecond.getValue() - 1);
            case STYLE_HH_MM:
                calendar.set(Calendar.HOUR_OF_DAY, mWvHour.getValue() - 1);
                calendar.set(Calendar.MINUTE, mWvMinute.getValue() - 1);
                break;
            default:
                break;
        }
        return calendar;
    }

}
