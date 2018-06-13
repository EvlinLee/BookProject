package com.zhangwan.app.utils;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.gxtc.commlibrary.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangwan.app.MyApplication;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zhangwan.app.R;

/**
 * 分享工具类
 * Created by Administrator on 2018/3/28 0028.
 */

public class UMShareUtil {

    private ShareAction mShareAction;

    private static UMShareUtil mInstance;

    public static UMShareUtil getInstance(Activity activity) {
        if (mInstance == null) {
            synchronized (UMShareUtil.class) {
                if (mInstance == null) {
                    mInstance = new UMShareUtil();
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_LOGS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.SET_DEBUG_APP,
                                Manifest.permission.SYSTEM_ALERT_WINDOW,
                                Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.WRITE_APN_SETTINGS};
                        ActivityCompat.requestPermissions(activity, mPermissionList, 123);
                    }
                }
            }
        }
        return mInstance;
    }

    //  不带界面的文本分享
    public void shareText(Activity activity, String text, SHARE_MEDIA shareMedia) {
        new ShareAction(activity)
                .setPlatform(shareMedia)//传入平台
                .withText(text)//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    //  不带界面的链接分享
    public void shareWebSite(Activity activity, String url,String title, String info, SHARE_MEDIA shareMedia) {
        UMWeb web = new UMWeb(url);
        UMImage thumb = new UMImage(activity, R.mipmap.logo);
        web.setTitle(title);//标题
        web.setDescription(info);//描述
        web.setThumb(thumb);//缩略图
        new ShareAction(activity)
                .setPlatform(shareMedia)//传入平台
                .withMedia(web)
                .setCallback(shareListener)
                .share();
    }

    //  分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("UMShareUtil", "onResult: " + "分享成功！");
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "分享成功！", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.d("UMShareUtil", "onError: " + "分享失败！");
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "分享失败！" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.d("UMShareUtil", "onCancel: " + "取消分享！");
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "取消分享！", Toast.LENGTH_LONG).show();
        }
    };

}
