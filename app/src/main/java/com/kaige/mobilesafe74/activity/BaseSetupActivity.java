package com.kaige.mobilesafe74.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dell on 2017/3/25.
 */

public abstract class BaseSetupActivity extends Activity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() - e2.getX() > 0) {
                    showNextPage();
                }
                if (e1.getX() - e2.getX() < 0) {
                    showPrePage();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //3,通过手势处理类,接收多种类型的事件,用作处理
//        gestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }
//


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //下一页的抽象方法,由子类决定具体跳转到那个界面
    protected abstract void showNextPage();
    //上一页的抽象方法,由子类决定具体跳转到那个界面
    protected abstract void showPrePage();


    //点击下一页按钮的时候,根据子类的showNextPage方法做相应跳转
    public void nextPage(View view){
        showNextPage();
    }

    //点击上一页按钮的时候,根据子类的showPrePage方法做相应跳转
    public void prePage(View view){
        showPrePage();
    }
}
