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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (R.id.loading_dialog == id) {
            new LoadingDialogFragment().show(this);
        } else if (R.id.msg_dialog == id) {
            MsgDialogFragment dialog = new MsgDialogFragment();
            dialog.setTitle("This is title title title title title title title title");
            dialog.setText("This is message message message message message message message " +
                    "message message message message message message message message " +
                    "message message message message message message message");
            dialog.setNegativeKeyText("Cancel");
            dialog.setNegativeKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialog) {
                    showToast("Cancel");
                    dialog.dismiss();
                }
            });
            dialog.setPositiveKeyText("Ok");
            dialog.setPositiveKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialog) {
                    showToast("Ok");
                    dialog.dismiss();
                }
            });
            dialog.show(this);
        } else if (R.id.edit_dialog == id) {
            EditDialogFragment dialog = new EditDialogFragment();
            dialog.setTitle("This is title");
            dialog.setNegativeKeyText("Cancel");
            dialog.setNegativeKeyClickListener(new IOnDialogKeyClickListener() {
                @Override
                public void onClick(DialogFragment dialog) {
                    showToast("Cancel");
                    dialog.dismiss();
                }
            });
            dialog.setPositiveKeyText("Ok");
            dialog.setPositiveKeyClickListener(new IOnDialogKeyClickListener<EditDialogFragment>() {
                @Override
                public void onClick(EditDialogFragment dialog) {
                    showToast(dialog.getText());
                    dialog.dismiss();
                }
            });
            dialog.show(this);
        } else if (R.id.password_dialog == id) {
            PwdInputDialogFragment dialog = new PwdInputDialogFragment();
            dialog.setPositiveKeyClickListener(new IOnDialogKeyClickListener<PwdInputDialogFragment>() {
                @Override
                public void onClick(PwdInputDialogFragment dialog) {
                    showToast(dialog.getPasswordString());
                    dialog.dismiss();
                }
            });
            dialog.show(this);
        } else if (R.id.bottom_dialog == id) {
            List<BottomSelectDialogFragment.Item> list = new ArrayList<>();
            list.add(BottomSelectDialogFragment.Item.build("xxx", Color.BLACK));
            list.add(BottomSelectDialogFragment.Item.build("sf322r", Color.GREEN));
            list.add(BottomSelectDialogFragment.Item.build("21czx", Color.RED));

            BottomSelectDialogFragment dialog = new BottomSelectDialogFragment();
            dialog.setTitleText("This is title");
            dialog.setCancelable(true);
            dialog.setIOnDialogListener(new DefaultDialogListener());
            dialog.setItems(list);
            dialog.setOnItemClickListener(new BottomSelectDialogFragment.IOnItemClickListener() {
                @Override
                public void onItemClick(BottomSelectDialogFragment dialog, BottomSelectDialogFragment.Item item) {
                    dialog.dismiss();
                    showToast(item.mText);
                }
            });
            dialog.show(this);
        }
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
