package com.kaige.mobilesafe74.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.utils.ConstantValue;
import com.kaige.mobilesafe74.utils.SpUtil;
import com.kaige.mobilesafe74.utils.ToastUtils;

/**
 * Created by dell on 2017/3/20.
 */

public class Setup3Activity extends BaseSetupActivity {
    private EditText et_phone_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initUI();
    }

    @Override
    protected void showNextPage() {
        String phone = et_phone_number.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(this, Setup4Activity.class);
            startActivity(intent);
            finish();
            SpUtil.putSting(getApplicationContext(), ConstantValue.CONTACT_PHONE,phone);
        } else {
            ToastUtils.show(this, "请输入电话号码");
        }
        overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
    }

    private void initUI() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        String phone = SpUtil.getString(this, ConstantValue.CONTACT_PHONE, "");
        et_phone_number.setText(phone);

        Button bt_select_number = (Button) findViewById(R.id.bt_select_number);
        bt_select_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                startActivityForResult(intent, 0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String phone = data.getStringExtra("phone");
            phone = phone.replace("-", "").replace(" ", "").trim();
            et_phone_number.setText(phone);
            SpUtil.getString(getApplicationContext(), ConstantValue.CONTACT_PHONE, phone);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
