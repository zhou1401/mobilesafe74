package com.kaige.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;

/**
 * Created by dell on 2017/3/27.
 */

public class SettingClickView extends RelativeLayout {
    private TextView tv_des2;
    private TextView tv_title2;

    public SettingClickView(Context context) {
        this(context, null);
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //xml--->view	将设置界面的一个条目转换成view对象,直接添加到了当前SettingItemView对应的view中
        View.inflate(context, R.layout.setting_click_view, this);

        //自定义组合控件中的标题描述
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        tv_des2 = (TextView) findViewById(R.id.tv_des2);
    }

    /**
     * @param title 设置标题内容
     */
    public void setTitle(String title) {
        tv_title2.setText(title);
    }

    /**
     * @param des 设置描述内容
     */
    public void setDes(String des) {
        tv_des2.setText(des);
    }
}
