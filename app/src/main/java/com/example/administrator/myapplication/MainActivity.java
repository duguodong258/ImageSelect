package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    int startX;
    int startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                //获取手指按下去时候的坐标
                startX = (int)event.getRawX();
                startY = (int)event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE :
                //移动过程中不断的获取当前x y坐标 减去起始的坐标就是滑动的距离
                int disX = (int) event.getRawX() - startX;
                int disY = (int) event.getRawY() - startY;

                //在移动过程中动态获取view的上下左右的坐标
                int left = view.getLeft() + disX;
                int top = view.getTop() +disY;
                int right = view.getRight() +disX;
                int bottom = view.getBottom()+disY;

                //动态摆放view的位置 来实现滑动
                view.layout(left, top, right, bottom);
                break;
        }
        return false;
    }
}
