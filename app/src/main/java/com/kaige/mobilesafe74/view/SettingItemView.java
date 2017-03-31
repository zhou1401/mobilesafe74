package com.kaige.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import com.kaige.mobilesafe74.R;


/**
 * Created by dell on 2017/3/17.
 */

public class SettingItemView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.kaige.mobilesafe74";
    private final TextView tv_des;
    private final CheckBox cb_box;
    private String mDestitle;
    private String mDesoff;
    private String mDeson;

    public SettingItemView(Context context) {
        this(context,null);
    }
    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       View.inflate(context, R.layout.setting_item_view, this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_des = (TextView) findViewById(R.id.tv_des);
        cb_box = (CheckBox) findViewById(R.id.cb_box);
        //获取自定义以及原生属性的操作,写在此处,AttributeSet attrs对象中获取
        initAttrs(attrs);
        //获取布局文件中定义的字符串,赋值给自定义组合控件的标题
        tv_title.setText(mDestitle);

    }

    private void initAttrs(AttributeSet attrs) {
        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
    }

    public boolean isCheck(){
        return cb_box.isChecked();
    }
    public void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if(isCheck){
            tv_des.setText(mDeson);
        }else{
            tv_des.setText(mDesoff);
        }
    }
}
