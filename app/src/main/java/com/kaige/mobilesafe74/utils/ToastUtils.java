package com.kaige.mobilesafe74.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dell on 2017/3/17.
 */

public class ToastUtils {
    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx, msg,Toast.LENGTH_SHORT).show();
    }
}
