package com.kaige.mobilesafe74.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import com.kaige.mobilesafe74.utils.ConstantValue;
import com.kaige.mobilesafe74.utils.SpUtil;

/**
 * Created by dell on 2017/3/25.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm= (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber() + "xxx";
        String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, "");
        if(!sim_number.equals(simSerialNumber)){
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("5556", null, "sim change!!!", null, null);
        }
    }
}
