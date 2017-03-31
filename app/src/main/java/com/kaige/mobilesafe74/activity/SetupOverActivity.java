package com.kaige.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.utils.ConstantValue;
import com.kaige.mobilesafe74.utils.SpUtil;

/**
 * Created by dell on 2017/3/17.
 */

public class SetupOverActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if(setup_over){
            setContentView(R.layout.activity_setup_over);
            initUI();
        }else {
            Intent intent=new Intent(this,Setup1Activity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initUI() {
        TextView tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
