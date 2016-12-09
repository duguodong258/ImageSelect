package com.example.ontouchtest.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.ontouchtest.R;

/**
 * @author 咸鱼
 * @date 2016/12/1 0001
 * @des 图片选择器 图片数量大于9张时 显示的dialog
 */

public class CustomDialog extends AlertDialog {

    private RelativeLayout relativeLayout;

    protected CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_i_know);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
