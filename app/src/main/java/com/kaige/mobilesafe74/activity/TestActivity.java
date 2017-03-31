package com.kaige.mobilesafe74.activity;

/**
 * Created by dell on 2017/3/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("TestActivity");

        setContentView(textView);
    }
}
