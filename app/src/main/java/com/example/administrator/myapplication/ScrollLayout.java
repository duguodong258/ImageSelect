package com.example.administrator.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/29 0029.
 */

public class ScrollLayout extends LinearLayout {

    // 手指按下时View的相对坐标。
    private int mDownX;
    private int mDownY;

    private int lastX;
    private int lastY;

    public ScrollLayout(Context context) {
        super(context);
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        int y = (int)event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                mDownX = getScrollX();//获取这两个坐标点 以便up之后让他弹回去
                mDownY = getScrollY();
                lastX = x;//down下去时候把 x 坐标记录
                lastY = y;//down下去时候把 y 坐标记录
                return true;
            case MotionEvent.ACTION_MOVE :
                int disY = (int)event.getRawY() - lastY;//移动的y距离
                int disX = (int)event.getRawX() - lastX;//移动的x距离
                //更新手指的坐标
                lastX = x;
                lastY = y;
                scrollBy(-disX,-disY);
                return true;
            case MotionEvent.ACTION_UP :
                scrollTo(mDownX,mDownY);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
