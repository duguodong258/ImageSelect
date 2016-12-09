package com.example.ontouchtest.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ontouchtest.adapter.MyAdapter;
import com.example.ontouchtest.widget.PhotoPopupWindow;
import com.example.ontouchtest.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int screenWidth = 0;
    int screenHeight = 0;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 2;//相机
    private static final int REQUEST_CODE_PICK_IMAGE = 1;//相册
    private RelativeLayout root_layout;//根布局
    private List <String> mDatas;
    private Context mContext;
    private ImageView ivHai;//最上面那个女的坐在海边
    private TextView tv_dianwo;//点我呀
    private PhotoPopupWindow popupWindow;
    private LinearLayout main_view;//根布局
    private RecyclerView recyclerView;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initDevice();
        initDatas();
        initView();
        initPopupWindow();
    }


    private void initView() {
        main_view = (LinearLayout) findViewById(R.id.main_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_photo);
        tv_dianwo = (TextView) findViewById(R.id.tv_dianwo);
        ivHai = (ImageView) findViewById(R.id.iv_hai);
        //点击跳转到图片选择器
        ivHai.setOnClickListener(this);
        Glide.with(this).load(R.drawable.hai).into(ivHai);

        String kaitifonts = "fonts/kaiti.ttf";//楷体字
        Typeface typeface = Typeface.createFromAsset(getAssets(), kaitifonts);
        tv_dianwo.setTypeface(typeface);

        //设置布局管理器 1对1  一个layoutManager只能给recyclerView用
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridlayoutManager);
        //设置适配器
        adapter = new MyAdapter(mDatas,this);
        recyclerView.setAdapter(adapter);
    }


    private void initPopupWindow() {
        popupWindow = new PhotoPopupWindow(mContext, this);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for(int i = 0; i < 20; i++) {
            mDatas.add("www"+i);
        }
    }


    @Override
    public void onClick(View view) {
        popupWindow.dismiss();
        switch (view.getId()) {
            case R.id.iv_hai :
                popupWindow.showAtLocation(main_view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.btn_pick_photo :
                Intent intent = new Intent(mContext,ImageSelectorActivity.class);
                startActivityForResult(intent,REQUEST_CODE_PICK_IMAGE);
                break;
            case R.id.btn_take_photo :
                getImageFromCamera();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == REQUEST_CODE_PICK_IMAGE){//相册
                mDatas = data.getStringArrayListExtra("data");
                Log.i("TAG", "mDatas:--------"+mDatas.toString());
                adapter = new MyAdapter(mDatas,this);
                recyclerView.setAdapter(adapter);
            }else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA ) {//相机
                /*********** 这个你寄几要用就写，不用就当没看到 **********/
            }
        }
    }









    /********************************************华腻的分割线,下面都是废代码 没用***********************************************/
    //单张照片选择
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }


    //拍照
    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        }
        else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }


    //获取到照片的uri ---->bitmap---->byte[]
    private byte[] getBitmapFromUri(Uri uri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void initDevice() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;
    }
}
