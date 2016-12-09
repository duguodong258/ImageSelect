package com.example.ontouchtest;

/**
 * @author 咸鱼
 * @date 2016/11/30 0030
 * @des 点击图片选择器的item的点击监听
 */

public interface MyItemClickListener {

    /**
     *
     * @param isSelect 是否选中了图片
     * @param position 选中图片的id
     */
    void onItemClick(boolean isSelect,int position);

}
