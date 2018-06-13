package com.zhangwan.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.zhangwan.app.utils.JPushUtil;
import com.zhangwan.app.utils.PageFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

/**
 * Created by zzg on 2018/3/29
 */

public class MyApplication extends DefaultApplicationLike {

    //全局上下文
    private static Application app;

    public MyApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                         long applicationStartElapsedTime, long applicationStartMillisTime,
                         Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }


    public static Application getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplication();
        Config.DEBUG = true;
        JPushUtil.init(getApplication());
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(getApplication()).setShareConfig(config);
        initBugly();
        com.zhangwan.app.bean.Config.createConfig(getApplication());
        PageFactory.createPageFactory(getApplication());
        SImagePicker.init(new PickerConfig.Builder().setAppContext(getInstance()).setImageLoader(new GlideImageLoader()).
                setToolbaseColor(getApplication().getResources().getColor(R.color.colorPrimary)).build());
    }

    {
        PlatformConfig.setWeixin("wx3fb941aad426e182", "3b120e5963a5ab2e2b061a1bc65c5657");
        PlatformConfig.setQQZone("1106807148", "3adDhBBjoZjpCZPd");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    private void initBugly() {
        Context context = getApplication();
        // 获取当前包名
        String packageName = getApplication().getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        Bugly.init(getApplication(), "077e161fd2", true,strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
