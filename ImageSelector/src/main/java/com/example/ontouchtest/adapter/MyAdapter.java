package com.example.ontouchtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ontouchtest.R;

import java.io.File;
import java.util.List;

/**
 * @author 咸鱼
 * @date 2016/11/29 0029
 * @des ${TODO}
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List <String>mDatas;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyAdapter(List <String> mDatas, Context context) {
        this.mDatas = mDatas;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    //返回holder 把view传过去
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_item, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }


    //绑定数据
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Glide.with(mContext).load(new File(mDatas.get(position))).into(holder.iv);
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView)itemView.findViewById(R.id.id_index_gallery_item_image);
            tv = (TextView)itemView.findViewById(R.id.id_index_gallery_item_text);
        }
    }
}
