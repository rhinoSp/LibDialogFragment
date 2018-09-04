package com.rhino.dapp;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rhino.dialog.BottomSelectDialogFragment;
import com.rhino.dialog.EditDialogFragment;
import com.rhino.dialog.LoadingDialogFragment;
import com.rhino.dialog.MsgDialogFragment;
import com.rhino.dialog.PopupMenuDialogFragment;
import com.rhino.dialog.pwd.PwdInputDialogFragment;
import com.rhino.dialog.impl.IOnDialogKeyClickListener;
import com.rhino.dialog.impl.DefaultDialogListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loading_dialog).setOnClickListener(this);
        findViewById(R.id.msg_dialog).setOnClickListener(this);
        findViewById(R.id.edit_dialog).setOnClickListener(this);
        findViewById(R.id.password_dialog).setOnClickListener(this);
        findViewById(R.id.bottom_dialog).setOnClickListener(this);
        findViewById(R.id.popup_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.loading_dialog == id) {
            new LoadingDialogFragment().show(this);
        } else if (R.id.msg_dialog == id) {
            MsgDialogFragment dialogFragment = new MsgDialogFragment();
            dialogFragment.setAlignType(PopupMenuDialogFragment.ALIGN_TYPE_THIS_BOTTOM_WINDOW_RIGHT);
            dialogFragment.setAlignView(view);
            dialogFragment.setTitle("This is title title title title title title title title");
            dialogFragment.setText("This is message message message message message message message " +
                    "message message message message message message message message " +
                    "message message message message message message message");
            dialogFragment.setNegativeKeyText("Cancel");
            dialogFragment.setNegativeKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40));
            dialogFragment.setNegativeKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialogFragment) {
                    showToast("Cancel");
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.setPositiveKeyText("Ok");
            dialogFragment.setPositiveKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40));
            dialogFragment.setPositiveKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialogFragment) {
                    showToast("Ok");
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.show(this);
        } else if (R.id.edit_dialog == id) {
            EditDialogFragment dialogFragment = new EditDialogFragment();
            dialogFragment.setTitle("This is title");
            dialogFragment.setNegativeKeyText("Cancel");
            dialogFragment.setNegativeKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40));
            dialogFragment.setNegativeKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialogFragment) {
                    showToast("Cancel");
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.setPositiveKeyText("Ok");
            dialogFragment.setPositiveKeyColor(getResources().getColorStateList(R.color.color_nor_black_pre_black40));
            dialogFragment.setPositiveKeyClickListener(new IOnDialogKeyClickListener<EditDialogFragment>() {
                @Override
                public void onClick(EditDialogFragment dialogFragment) {
                    showToast(dialogFragment.getText());
                    dialogFragment.dismiss();
                }
            });
            dialogFragment.show(this);
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

            BottomSelectDialogFragment dialogFragment = new BottomSelectDialogFragment();
            dialogFragment.setTitleText("This is title");
            dialogFragment.setCancelable(true);
            dialogFragment.setIOnDialogListener(new DefaultDialogListener());
            dialogFragment.setItems(list);
            dialogFragment.setOnItemClickListener(new BottomSelectDialogFragment.IOnItemClickListener() {
                @Override
                public void onItemClick(BottomSelectDialogFragment dialogFragment, BottomSelectDialogFragment.Item item) {
                    dialogFragment.dismiss();
                    showToast(item.mText);
                }
            });
            dialogFragment.show(this);
        } else if (R.id.popup_dialog == id) {
            PopupMenuDialogFragment dialogFragment = new PopupMenuDialogFragment();
            dialogFragment.setAlignType(PopupMenuDialogFragment.ALIGN_TYPE_THIS_TOP_WINDOW_RIGHT);
            dialogFragment.setAlignView(view);
            dialogFragment.show(this);
        }
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
