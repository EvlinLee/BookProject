package com.zhangwan.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.zhangwan.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sjr on 2017/9/11.
 */

public class LaunchActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //无title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);
//        gotoActivity();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                LaunchActivity.this.finish();
            }
        }, 1000);
    }

//    private void gotoActivity() {
////        String token = SpUtil.getToken(this);
////        String loginId = SpUtil.getLoginId(this);
////        if ("".equals(token) && "".equals(loginId)) {
//////            waitGo(LoginActivity.class);
////        } else {
////            waitGo(MainActivity.class);
////        }
//        waitGo(MainActivity.class);
//    }
//
//    private void waitGo(Class cls) {
//        final Intent intent = new Intent(this, cls);
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(intent);
//                LaunchActivity.this.finish();
//            }
//        };
////        timer.schedule(task, 1000 * 1);
//        timer.schedule(task, 500);
//    }
}

