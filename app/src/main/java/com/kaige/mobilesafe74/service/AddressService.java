package com.kaige.mobilesafe74.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaige.mobilesafe74.R;
import com.kaige.mobilesafe74.engine.AddressDao;

/**
 * Created by dell on 2017/3/27.
 */

public class AddressService extends Service {

    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;
    private WindowManager mWM;
    private WindowManager.LayoutParams mParams;
    private TextView tv_toast;
    private View mViewToast;
    public String mAddress;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_toast.setText(mAddress);
        }
    };

    @Override
    public void onCreate() {
        mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneStateListener = new MyPhoneStateListener();
        mTM.listen(mPhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
        //获取窗体对象
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        super.onCreate();
    }
     class MyPhoneStateListener extends PhoneStateListener{
         @Override
         public void onCallStateChanged(int state, String incomingNumber) {
             switch (state){
                 case TelephonyManager.CALL_STATE_RINGING:
                     showToast(incomingNumber);
                     break;
                 case TelephonyManager.CALL_STATE_IDLE:
                     break;
                 case TelephonyManager.CALL_STATE_OFFHOOK:
                     break;
             }
             super.onCallStateChanged(state, incomingNumber);
         }
     }
    public void showToast(String incomingNumber){
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        //指定位置
        params.gravity= Gravity.LEFT+Gravity.TOP;
        //加载布局
        mViewToast=View.inflate(this, R.layout.toast_view,null);
        tv_toast = (TextView) mViewToast.findViewById(R.id.tv_toast);
        mWM.addView(mViewToast,params);
        query(incomingNumber);
    }

    private void query(final String incomingNumber) {
        new Thread(){


            @Override
            public void run() {
                mAddress = AddressDao.getAddress(incomingNumber);
                mHandler.sendEmptyMessage(0);

            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
