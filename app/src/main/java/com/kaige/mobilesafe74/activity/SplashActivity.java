package com.kaige.mobilesafe74.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dell on 2017/3/13.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int ENTER_HOME = 100;
    private TextView tv_version_name;
    private RelativeLayout rl_root;
    private Handler mHandler = new Handler() {
        @Override
        //alt+ctrl+向下箭头,向下拷贝相同代码
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case ENTER_HOME:
                    //进入应用程序主界面,activity跳转过程
                    enterHome();
                    break;
            }
        }
    };
    private InputStream stream;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化UI
        initUI();
        //初始化数据
        initData();
        //初始化动画
        initAnimation();
        newThread();
        //初始化数据库
        initDB();
    }

    private void initDB() {
        //1,归属地数据拷贝过程
        initAddressDB("address.db");
    }

    private void initAddressDB(String dbName) {
        File files = getFilesDir();
        File file = new File(files, dbName);
        if (file.exists()) {
            return;
        }
        InputStream stream = null;
        FileOutputStream fos = null;
        try {
            stream = getAssets().open(dbName);
            fos = new FileOutputStream(file);
            byte[] bs = new byte[1024];
            int temp = -1;
            while ((temp=stream.read(bs))!=-1){
                fos.write(bs,0,temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream!=null&&fos!=null){
                try {
                    stream.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        rl_root.startAnimation(alphaAnimation);
    }

    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    private void initData() {
        tv_version_name.setText("版本名称" + getVersionName());
        mHandler.sendEmptyMessageDelayed(ENTER_HOME, 3000);

    }

    private void newThread() {
        final Message msg = Message.obtain();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
