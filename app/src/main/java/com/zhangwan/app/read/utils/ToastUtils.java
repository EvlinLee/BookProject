package com.zhangwan.app.read.utils;

import android.widget.Toast;

import com.zhangwan.app.MyApplication;

/**
 * Created by newbiechen on 17-5-11.
 */

public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
