package com.kaige.mobilesafe74.view;

/**
 * Created by dell on 2017/3/13.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
/**
 * @author qbc
 * 能够获取焦点的自定义TextView
 */
public class FocusTextView extends android.support.v7.widget.AppCompatTextView {
    //使用在通过java代码创建控件
    public FocusTextView(Context context) {
        super(context);
    }

    //由系统调用(带属性+上下文环境构造方法)
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //由系统调用(带属性+上下文环境构造方法+布局文件中定义样式文件构造方法)
    public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //重写获取焦点的方法,由系统调用,调用的时候默认就能获取焦点
    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
    }
}

