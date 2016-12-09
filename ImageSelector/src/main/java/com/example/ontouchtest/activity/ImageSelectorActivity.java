package com.example.ontouchtest.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ontouchtest.MyItemClickListener;
import com.example.ontouchtest.R;
import com.example.ontouchtest.adapter.SelectorAdapter;

import java.util.ArrayList;

/**
 * @author 咸鱼
 * @date 2016/11/30 0030
 * @des 图片选择器的activity
 */
public class ImageSelectorActivity extends Activity implements MyItemClickListener, View.OnClickListener {

    private ArrayList <String> mDatas;
    private ArrayList <String> selectDatas;
    private Context mContext;
    private TextView tv_max_number;
    private String max_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inmage_selector);
        mContext = this;
        initDatas();
        initView();
        max_number = getResources().getString(R.string.max_number);
    }



    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview_image_selector);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_Back);
        TextView tvFinish = (TextView) findViewById(R.id.tv_finish);
        tv_max_number = (TextView) findViewById(R.id.tv_max_number);
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        SelectorAdapter adapter = new SelectorAdapter(mDatas,this);
        adapter.setmListener(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_Back :
                finish();
                break;
            case R.id.tv_finish :
                Intent intent = new Intent();//点击完成时 把选中的照片的地址集合传过去 在那边通过此地址来设置图片
                intent.putStringArrayListExtra("data",selectDatas);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }




    @Override
    public void onItemClick(boolean isSelected, int position) {
        Log.i("TAG", "position:"+ position);
        //如果那边点击了一下 选中，我就在这里的集合里把地址添加进去,如果那边又点了一下，取消了，我就把该图片地址移除
        if(isSelected){
            if(selectDatas.size()<9){
                selectDatas.add(mDatas.get(position));
                tv_max_number.setText(selectDatas.size()+max_number+"");
            }else{
                Toast.makeText(mContext,"最多只能选9张",Toast.LENGTH_SHORT).show();

                return;
            }

        }else{
            selectDatas.remove(mDatas.get(position));
            tv_max_number.setText(selectDatas.size()+ max_number+"");
        }
        //此处就是打印数组的长度来测试，多次点击同一张图发现数组长度没有继续增加，验证通过
        Log.i("TAG", "selectDatas:"+ selectDatas.toString());
    }





    private void initDatas() {

        mDatas = new ArrayList<String>();
        // 只查询jpeg和png的图片
        Uri ImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(ImagesUri, null
                , MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?"
                , new String[]{"image/jpeg", "image/png"}
                , MediaStore.Images.Media.DATE_MODIFIED);
        while (cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            mDatas.add(path);
        }

        selectDatas = new ArrayList<String>();
    }
}
