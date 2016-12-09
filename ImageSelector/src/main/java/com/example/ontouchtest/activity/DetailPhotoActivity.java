package com.example.ontouchtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ontouchtest.R;

import java.io.File;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

public class DetailPhotoActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_photo);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        initView(path);
    }

    private void initView(String path) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_detail_photo);
        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);
        if(!TextUtils.isEmpty(path)){
            Glide.with(this).load(new File(path)).into(imageView);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_detail_photo :
                finish();
                break;
        }
    }


    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(this,"长按了",Toast.LENGTH_SHORT).show();
        return false;
    }
}
