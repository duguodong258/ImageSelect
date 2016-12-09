package com.example.ontouchtest.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.ontouchtest.R;

/**
 * @author 咸鱼
 * @date 2016/12/1 0001
 * @des ${TODO}
 */

public class PhotoPopupWindow extends PopupWindow {

    private final Button mTakePhotoBtn;
    private final Button mPickPhotoBtn;
    private final Button mCancelBtn;
    private final Button btn_pick_video;
    private final View mMenuView;
    private final View mVideoline;
    private final int DURATION = 300;


    //构造器传入一个点击监听器
    public PhotoPopupWindow(Context context, View.OnClickListener onClickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.photo_popup_layout,null);
        btn_pick_video = (Button) mMenuView.findViewById(R.id.btn_pick_video);
        mTakePhotoBtn = (Button) mMenuView.findViewById(R.id.btn_take_photo);
        mPickPhotoBtn = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        mCancelBtn = (Button) mMenuView.findViewById(R.id.btn_cancel);
        mVideoline = mMenuView.findViewById(R.id.video_line);
        //取消按钮
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消popupwindow
                dismiss();
            }
        });

        mPickPhotoBtn.setOnClickListener(onClickListener);//点击相册
        mTakePhotoBtn.setOnClickListener(onClickListener);//点击拍照
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow的View
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体的背景
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y > height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        show();
    }

    //用来显示popupwindow
    private void show() {
        mMenuView.startAnimation(getAlphaAnimation(DURATION,0f,1f));
        mMenuView.startAnimation(getTranslateAnimationY(DURATION,1f,0f,true));
    }


    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        show();
        super.showAtLocation(parent, gravity, x, y);
    }



    public void setTopButton(String content) {
        if (mTakePhotoBtn != null) {
            mTakePhotoBtn.setText(content);
        }
    }

    public void setSecondButton(String content) {
        if (mPickPhotoBtn != null) {
            mPickPhotoBtn.setText(content);
        }
    }




    /**
     * 制定 AlphaAnimation
     * @return
     */
    public static AlphaAnimation getAlphaAnimation(long durationMillis,float fromAlpha, float toAlpha) {
        AlphaAnimation aa = new AlphaAnimation(fromAlpha, toAlpha);
        aa.setDuration(durationMillis);
        aa.setFillAfter(true);
        aa.setFillEnabled(true);
        return aa;
    }


    /**
     * 制定 TranslateAnimation
     *
     * @return
     */
    public static TranslateAnimation getTranslateAnimationY(long durationMillis,
                                                            float fromYValue, float toYValue, boolean isKeepEnd) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue,
                Animation.RELATIVE_TO_SELF, toYValue);
        ta.setDuration(durationMillis);
        if (isKeepEnd) {
            ta.setFillAfter(true);
            ta.setFillEnabled(true);
        }
        return ta;
    }
}
