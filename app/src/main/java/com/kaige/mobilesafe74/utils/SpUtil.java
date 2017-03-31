package com.kaige.mobilesafe74.utils;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 2017/3/17.
 */

public class SpUtil {
    private static SharedPreferences sp;

    public static void putBoolean(Context ctx, String key, boolean value){
        //(存储节点文件名称,读写方式)
        if(sp==null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean getBoolean(Context ctx, String key, boolean defValue){
        //(存储节点文件名称,读写方式)
        if(sp==null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }
    public static void putSting(Context ctx, String key, String value){
        //(存储节点文件名称,读写方式)
        if(sp==null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }
    public static String getString(Context ctx, String key, String defValue){
        //(存储节点文件名称,读写方式)
        if(sp==null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

    public static void remove(Context ctx,String key) {
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();

    }
    /**
     * 写入boolean变量至sp中
     * @param ctx	上下文环境
     * @param key	存储节点名称
     * @param value	存储节点的值string
     */
    public static void putInt(Context ctx,String key,int value){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }
    /**
     * 读取boolean标示从sp中
     * @param ctx	上下文环境
     * @param key	存储节点名称
     * @param defValue	没有此节点默认值
     * @return		默认值或者此节点读取到的结果
     */
    public static int getInt(Context ctx,String key,int defValue){
        //(存储节点文件名称,读写方式)
        if(sp == null){
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }
}
