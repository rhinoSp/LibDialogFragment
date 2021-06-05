package com.rhino.dialog.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.rhino.dialog.BottomSelectDialogFragment;
import com.rhino.dialog.DialogFactory;
import com.rhino.dialog.EditDialogFragment;
import com.rhino.dialog.LoadingDialogFragment;
import com.rhino.dialog.PopupMenuDialogFragment;
import com.rhino.dialog.TipsDialogFragment;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;
import com.rhino.dialog.picker.DatePickerDialogFragment;
import com.rhino.dialog.picker.SingleWheelPickerDialogFragment;
import com.rhino.dialog.pwd.PwdInputDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private LoadingDialogFragment.LoadingDialogHelper loadingDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialogHelper = LoadingDialogFragment.getHelper(this);
        DialogFactory.setContext(this);
    }

    public void onViewClick(View view) {
        int id = view.getId();
        if (R.id.loading_dialog == id) {
            loadingDialogHelper.showLoadingDialog();
        } else if (R.id.tips_dialog == id) {
            TipsDialogFragment dialogFragment = new TipsDialogFragment();
            dialogFragment.setTips("This is message message message message message message message " +
                    "message message message message message message message message " +
                    "message message message message message message message");
//            dialogFragment.setCheckboxVisibility(View.GONE);
            dialogFragment.setNegativeKeyText("Cancel");
            dialogFragment.setNegativeKeyColor(Color.RED);
            dialogFragment.setNegativeKeyClickListener(new IOnDialogKeyClickListener<TipsDialogFragment>() {
                @Override
                public void onClick(TipsDialogFragment dialogFragment) {
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.setPositiveKeyText("Ok");
            dialogFragment.setPositiveKeyColor(Color.BLACK);
            dialogFragment.setPositiveKeyClickListener(new IOnDialogKeyClickListener<TipsDialogFragment>() {
                @Override
                public void onClick(TipsDialogFragment dialogFragment) {
                    showToast("" + dialogFragment.isCheckboxSelected());
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.show(this);
        } else if (R.id.msg_dialog == id) {
            DialogFactory.buildCustomMessageDialog(false,
                    "This is title title title title title title title title",
                    "This is message message message message message message message" +
                            "message message message message message message message message" +
                            "message message message message message message message",
                    "Cancel",
                    new IOnDialogKeyClickListener() {
                        @Override
                        public void onClick(DialogFragment dialogFragment) {
                            showToast("Cancel");
                            dialogFragment.dismiss();
                        }
                    },
                    "Ok",
                    new IOnDialogKeyClickListener() {
                        @Override
                        public void onClick(DialogFragment dialogFragment) {
                            showToast("Ok");
                            dialogFragment.dismiss();
                        }
                    }).show(this);
        } else if (R.id.edit_dialog == id) {
            DialogFactory.buildCustomTextEditDialog("This is title",
                    null,
                    "please input",
                    "Cancel",
                    new IOnDialogKeyClickListener() {
                        @Override
                        public void onClick(DialogFragment dialogFragment) {
                            showToast("Cancel");
                            dialogFragment.dismiss();
                        }
                    },
                    "Ok",
                    new IOnDialogKeyClickListener<EditDialogFragment>() {
                        @Override
                        public void onClick(EditDialogFragment dialogFragment) {
                            showToast(dialogFragment.getText());
                            dialogFragment.dismiss();
                        }
                    })
                    .setNegativeKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40))
                    .setPositiveKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40))
                    .show(this);
        } else if (R.id.password_dialog == id) {
            PwdInputDialogFragment dialogFragment = new PwdInputDialogFragment();
            dialogFragment.setPositiveKeyClickListener(new IOnDialogKeyClickListener<PwdInputDialogFragment>() {
                @Override
                public void onClick(PwdInputDialogFragment dialogFragment) {
                    showToast(dialogFragment.getPasswordString());
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.show(this);
        } else if (R.id.bottom_dialog == id) {
            List<BottomSelectDialogFragment.Item> list = new ArrayList<>();
            list.add(BottomSelectDialogFragment.Item.build("xxx", Color.BLACK));
            list.add(BottomSelectDialogFragment.Item.build("sf322r", Color.GREEN));
            list.add(BottomSelectDialogFragment.Item.build("21czx", Color.RED));
            DialogFactory.buildBottomSheetSelectDialog("This is title",
                    list,
                    new BottomSelectDialogFragment.IOnItemClickListener() {
                        @Override
                        public void onItemClick(BottomSelectDialogFragment dialogFragment, BottomSelectDialogFragment.Item item) {
                            dialogFragment.dismiss();
                            showToast(item.mText);
                        }
                    }).show(this);
        } else if (R.id.popup_dialog == id) {
            PopupMenuDialogFragment dialogFragment = new PopupMenuDialogFragment();
            dialogFragment.setAlignType(PopupMenuDialogFragment.ALIGN_TYPE_THIS_BOTTOM_CENTER);
            dialogFragment.setMarginTop(0);
            dialogFragment.setMarginBottom(0);
            dialogFragment.setAlignView(view);
            dialogFragment.show(this);
        } else if (R.id.date_dialog1 == id) {
            DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
            dialogFragment.setTitle("选择时间");
            dialogFragment.setCurrentYear(1999);
            dialogFragment.setCurrentMonth(2);
            dialogFragment.setCurrentDay(29);
            dialogFragment.setCurrentHour(13);
            dialogFragment.setCurrentMinute(56);
            dialogFragment.setCurrentSecond(45);
            dialogFragment.setStyle(DatePickerDialogFragment.STYLE_YYYY_MM_DD_HH_MM_SS);
            dialogFragment.setYearCount(60);
            dialogFragment.setYearOnlyCurrentAfter(true);
            dialogFragment.setPositiveKeyClickListener(new IOnDialogKeyClickListener<DatePickerDialogFragment>() {
                @Override
                public void onClick(DatePickerDialogFragment dialogFragment) {
                    dialogFragment.dismiss();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.getDefault());
                    showToast(sdf.format(dialogFragment.getCalendar().getTime()));
                }
            });
            dialogFragment.show(this);
        } else if (R.id.date_dialog2 == id) {
            DialogFactory.buildDatePickerDialogFragment(DatePickerDialogFragment.STYLE_YYYY_MM_DD_HH_MM,
                    System.currentTimeMillis(),
                    null,
                    new IOnDialogKeyClickListener<DatePickerDialogFragment>() {
                        @Override
                        public void onClick(DatePickerDialogFragment dialogFragment) {
                            dialogFragment.dismiss();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分", Locale.getDefault());
                            showToast(sdf.format(dialogFragment.getCalendar().getTime()));
                        }
                    }).show(this);
        } else if (R.id.date_dialog3 == id) {
            DialogFactory.buildDatePickerDialogFragment(DatePickerDialogFragment.STYLE_YYYY_MM_DD,
                    System.currentTimeMillis(),
                    null,
                    new IOnDialogKeyClickListener<DatePickerDialogFragment>() {
                        @Override
                        public void onClick(DatePickerDialogFragment dialogFragment) {
                            dialogFragment.dismiss();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
                            showToast(sdf.format(dialogFragment.getCalendar().getTime()));
                        }
                    }).show(this);
        } else if (R.id.date_dialog4 == id) {
            DialogFactory.buildDatePickerDialogFragment(DatePickerDialogFragment.STYLE_HH_MM_SS,
                    System.currentTimeMillis(),
                    null,
                    new IOnDialogKeyClickListener<DatePickerDialogFragment>() {
                        @Override
                        public void onClick(DatePickerDialogFragment dialogFragment) {
                            dialogFragment.dismiss();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒", Locale.getDefault());
                            showToast(sdf.format(dialogFragment.getCalendar().getTime()));
                        }
                    }).show(this);
        } else if (R.id.date_dialog5 == id) {
            DialogFactory.buildDatePickerDialogFragment(DatePickerDialogFragment.STYLE_HH_MM,
                    System.currentTimeMillis(),
                    null,
                    new IOnDialogKeyClickListener<DatePickerDialogFragment>() {
                        @Override
                        public void onClick(DatePickerDialogFragment dialogFragment) {
                            dialogFragment.dismiss();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分", Locale.getDefault());
                            showToast(sdf.format(dialogFragment.getCalendar().getTime()));
                        }
                    }).show(this);
        } else if (R.id.single_picker_dialog == id) {
            DialogFactory.buildSingleWheelPickerDialogFragment("选择周期",
                    new String[]{"三天", "一周", "一个月", "一年", "三年"},
                    "一个",
                    "期",
                    new IOnDialogKeyClickListener<SingleWheelPickerDialogFragment>() {
                        @Override
                        public void onClick(SingleWheelPickerDialogFragment dialogFragment) {

                            dialogFragment.dismiss();
                            showToast(dialogFragment.getSelectedValue());
                        }
                    }).show(this);
        }
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
