package com.kaige.mobilesafe74.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.utils.ConstantValue;
import com.kaige.mobilesafe74.utils.SpUtil;

public class ToastLocationActivity extends AppCompatActivity {

    private ImageView iv_drag;
    private Button bt_top;
    private Button bt_bottom;
    private WindowManager mWM;
    private int mScreenHeight;
    private int mScreenWidth;
    private int locationX;
    private int locationY;
    private float startX;
    private float startY;
    private float moveX;
    private float moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_location);
        initUI();

    }

    private void initUI() {
        //可拖拽双击居中的图片控件
        iv_drag = (ImageView) findViewById(R.id.iv_drag);
        bt_top = (Button) findViewById(R.id.bt_top);
        bt_bottom = (Button) findViewById(R.id.bt_bottom);

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
        mScreenHeight = mWM.getDefaultDisplay().getHeight();
        mScreenWidth = mWM.getDefaultDisplay().getWidth();

        int locationX = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_X, 0);
        int locationY = SpUtil.getInt(getApplicationContext(), ConstantValue.LOCATION_Y, 0);

        //左上角坐标作用在iv_drag上
        //iv_drag在相对布局中,所以其所在位置的规则需要由相对布局提供

        //指定宽高都为WRAP_CONTENT
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //将左上角的坐标作用在iv_drag对应规则参数上
        layoutParams.leftMargin = locationX;
        layoutParams.topMargin = locationY;
        //将以上规则作用在iv_drag上
        iv_drag.setLayoutParams(layoutParams);

        if (locationY > mScreenHeight / 2) {
            bt_bottom.setVisibility(View.INVISIBLE);
            bt_top.setVisibility(View.VISIBLE);
        } else {
            bt_bottom.setVisibility(View.VISIBLE);
            bt_top.setVisibility(View.INVISIBLE);
        }


        //监听某一个控件的拖拽过程(按下(1),移动(多次),抬起(1))
        iv_drag.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            //对不同的事件做不同的逻辑处理
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        int disX = moveX - startX;
                        int disY = moveY - startY;

                        //1,当前控件所在屏幕的(左,上)角的位置
                        int left = iv_drag.getLeft() + disX;//左侧坐标
                        int top = iv_drag.getTop() + disY;//顶端坐标
                        int right = iv_drag.getRight() + disX;//右侧坐标
                        int bottom = iv_drag.getBottom() + disY;//底部坐标

                        //容错处理(iv_drag不能拖拽出手机屏幕)
                        //左边缘不能超出屏幕
                        if (left < 0) {
                            return true;
                        }

                        //右边边缘不能超出屏幕
                        if (right > mScreenWidth) {
                            return true;
                        }

                        //上边缘不能超出屏幕可现实区域
                        if (top < 0) {
                            return true;
                        }

                        //下边缘(屏幕的高度-22 = 底边缘显示最大值)
                        if (bottom > mScreenHeight - 22) {
                            return true;
                        }

                        if (top > mScreenHeight / 2) {
                            bt_bottom.setVisibility(View.INVISIBLE);
                            bt_top.setVisibility(View.VISIBLE);
                        } else {
                            bt_bottom.setVisibility(View.VISIBLE);
                            bt_top.setVisibility(View.INVISIBLE);
                        }

                        //2,告知移动的控件,按计算出来的坐标去做展示
                        iv_drag.layout(left, top, right, bottom);

                        //3,重置一次其实坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                        //4,存储移动到的位置
                        SpUtil.putInt(getApplicationContext(), ConstantValue.LOCATION_X, iv_drag.getLeft());
                        SpUtil.putInt(getApplicationContext(), ConstantValue.LOCATION_Y, iv_drag.getTop());
                        break;
                }
                //在当前的情况下返回false不响应事件,返回true才会响应事件
                return true;
            }
        });
    }
}
