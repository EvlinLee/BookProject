package com.zhangwan.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.bean.event.MessageEvent;
import com.zhangwan.app.read.NovelReadActivity;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MyJPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        //用户收到通知
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String chapterId = (String) GsonUtil.getJsonValue(extra, "chapterId");
            //  {"chapterId":"2883104","bookName":"巅峰人生","total":"0","bookPic":"http:\/\/img.17k.com\/images\/bookcover\/2013\/3168\/15\/633684-1378050061000.jpg","bookId":"4046"}
            LogUtil.i("extra  :用户收到通知  " + extra + "---chapterId--=" + chapterId);
            EventBusUtil.postStickyEvent(new MessageEvent());
        }

        //用户点击打开了通知
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String bookId = "";
            String readId = "";
            String picUrl = "";
            String bookName = "";
            String total = "";

            if (GsonUtil.getJsonValue(extra, "bookId") != null) {
                bookId = (String) GsonUtil.getJsonValue(extra, "bookId");
            }
            if (GsonUtil.getJsonValue(extra, "chapterId") != null) {
                readId = (String) GsonUtil.getJsonValue(extra, "chapterId");
            }
            if (GsonUtil.getJsonValue(extra, "total") != null) {
                total = (String) GsonUtil.getJsonValue(extra, "total");
            }
            if (GsonUtil.getJsonValue(extra, "bookPic") != null) {
                picUrl = (String) GsonUtil.getJsonValue(extra, "bookPic");
            }
            if (GsonUtil.getJsonValue(extra, "bookName") != null) {
                bookName = (String) GsonUtil.getJsonValue(extra, "bookName");
            }

            startReadActivity(bookId, readId, Integer.parseInt(total), picUrl, bookName, context);
        }

        //自定义推送消息
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.i("extra  :  自定义推送消息");        //
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            int type = Integer.valueOf((String) GsonUtil.getJsonValue(extra, "type"));
            String bizId = (String) GsonUtil.getJsonValue(extra, "bizId");
        }

    }

    /**
     * @param bookId   书籍id
     * @param readId   上次阅读章节id
     * @param total    章节总数
     * @param picUrl   封面
     * @param bookName 书名
     * @param context
     */
    public void startReadActivity(String bookId, String readId, int total, String picUrl, String bookName, Context context) {
        if (SpUtil.getIsLogin(context)) {
            Intent intent = new Intent(context, NovelReadActivity.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("readId", readId);
            intent.putExtra("total", total);
            intent.putExtra("picUrl", picUrl);
            intent.putExtra("bookName", bookName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            ToastUtil.showShort(MyApplication.getInstance(), "请先登录");
        }
    }

    private void startActivity(Context context, List<Intent> intents) {
        Intent intentArr[] = new Intent[intents.size()];
        for (int i = 0; i < intents.size(); i++) {
            intentArr[i] = intents.get(i);
        }
        context.startActivities(intentArr);
    }

}