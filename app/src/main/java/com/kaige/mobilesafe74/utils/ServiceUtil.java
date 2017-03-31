package com.kaige.mobilesafe74.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by dell on 2017/3/27.
 */

public class ServiceUtil {
    private static ActivityManager mAM;

    public static boolean isRunning(Context ctx, String serviceName){
        mAM = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = mAM.getRunningServices(1000);
        for(ActivityManager.RunningServiceInfo runningServiceInfo:runningServices){{
            //获取每一个真正运行应用的名称
            if(serviceName.equals(runningServiceInfo.service.getClassName())){
                return true;
            }
        }}

        return  false;
    }
}
