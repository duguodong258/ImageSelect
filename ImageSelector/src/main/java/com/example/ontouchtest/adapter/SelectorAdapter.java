package com.example.ontouchtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.ontouchtest.widget.CustomDialog;
import com.example.ontouchtest.MyItemClickListener;
import com.example.ontouchtest.R;
import com.example.ontouchtest.activity.DetailPhotoActivity;

import java.io.File;
import java.util.List;

/**
 * @author 咸鱼
 * @date 2016/11/29 0029
 * @des 选照片页面的recycleview的 adapter
 */

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.MyHolder> {

    private MyItemClickListener mListener;//在activity设置 给此接口实例化 然后在viewholder里面调用接口 在activity里实现
    private List <String>mDatas;//存放图片路径的集合
    private Context mContext;
    private int count;//限制最多可选9张照片
    private int screenWidth;
    private int screenHeight;

    public void setmListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }

    private LayoutInflater mInflater;

    public SelectorAdapter(List mDatas, Context context) {
        mContext = context;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }


    //返回holder 把view传过去
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_item_image_selector, null);
        MyHolder holder = new MyHolder(view,mListener);
        return holder;
    }


    //绑定数据
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String uri = mDatas.get(position);
        Glide.with(mContext).load(new File(mDatas.get(position))).into(holder.iv);
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }




    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MyItemClickListener clickListener;
        RelativeLayout rl_root;//根布局
        ImageView iv;
        View viewTouming;//半透明的view 选中时让他显示
        ImageView mis_default_select;//绿色的对勾 跟透明的view同生死
        ImageView iv_square;//绿色的对勾所在的透明矩形框
        CardView cardview;


        public MyHolder(View itemView, final MyItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            iv = (ImageView)itemView.findViewById(R.id.id_index_gallery_item_image);
            iv_square = (ImageView)itemView.findViewById(R.id.iv_square);
            mis_default_select = (ImageView)itemView.findViewById(R.id.mis_default_select);
            rl_root = (RelativeLayout)itemView.findViewById(R.id.rl_root);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
            viewTouming = itemView.findViewById(R.id.v_touming);
            rl_root.setOnClickListener(this);
            iv_square.setOnClickListener(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth / 4, screenWidth / 4);
            cardview.setLayoutParams(layoutParams);

        }



        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_root ://点击根布局是预览当前图片
                    String path = mDatas.get(getPosition());
                    Intent intent = new Intent(mContext,DetailPhotoActivity.class);
                    intent.putExtra("path",path);
                    mContext.startActivity(intent);
                    break;
                case R.id.iv_square ://点击右上角的小方块是选图
                    imgSelected();
                    break;
            }
        }


        //判断viewTouming是否可见 可见说明已经被选中，这里点击时候就说明取消了选中，
        // 取消了选中，我就不回调onItem的点击事件, ImageSelectorActivity中的selectDatas集合就不会把该item的图片的地址add进去
        //因此我只在 viewTouming不可见时 调用onItemClick()方法。。。。可以吗？  有bug
        //比如我对同一张图点了6次 那么他就往集合里添加了三次 因此 此处还是需要把boolean值传过去 在那边判断是否选中 决定是否添加到集合
        private void imgSelected() {
            if(viewTouming.getVisibility() == View.VISIBLE){
                count--;
                viewTouming.setVisibility(View.GONE);
                mis_default_select.setVisibility(View.GONE);
                clickListener.onItemClick(false,getPosition());//false 说明未选中
            }else{
                if(count == 9){
                    //大于9的时候显示dialog
                    showDialog();
                    return;
                }
                count++;
                viewTouming.setVisibility(View.VISIBLE);
                mis_default_select.setVisibility(View.VISIBLE);
                clickListener.onItemClick(true,getPosition());//true 说明选中
            }
            Log.i("TAG", "count------------------:"+count);
        }



        private void showDialog() {
            final CustomDialog customDialog = new CustomDialog(mContext, R.style.CustomDialog);
            Window window = customDialog.getWindow();
            customDialog.setCancelable(false);//点击外部不关闭对话框
            window.setGravity(Gravity.CENTER);
            customDialog.show();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = 600;
            layoutParams.height = 240;
            layoutParams.alpha = 0.8f;
            window.setAttributes(layoutParams);
        }
    }
}
