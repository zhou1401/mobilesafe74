package com.kaige.mobilesafe74.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import com.kaige.mobilesafe74.dbDao.BlackNumberDao;

/**
 * Created by dell on 2017/3/30.
 */

public class BlackNumberService extends Service{
    private InnerSmsReceiver mInnerSmsReceiver;
    private BlackNumberDao dao;

    @Override
    public void onCreate() {

        //拦截短信
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        mInnerSmsReceiver = new InnerSmsReceiver();
        registerReceiver(mInnerSmsReceiver, intentFilter);
        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class InnerSmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //1,获取短信内容
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            for(Object object:objects){
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                String originatingAddress = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();
                String mode = dao.getMode(originatingAddress);
                int modeType = Integer.parseInt(mode);
                if(modeType==1||modeType==3){
                    //拦截短信
                    abortBroadcast();
                }
                    //TODO 拦截短信服务未开启

            }

        }
    }

    @Override
    public void onDestroy() {
        if(mInnerSmsReceiver!=null){
            unregisterReceiver(mInnerSmsReceiver);
        }
        super.onDestroy();
    }
}
