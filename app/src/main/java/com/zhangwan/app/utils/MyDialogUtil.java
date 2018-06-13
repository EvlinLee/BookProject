package com.zhangwan.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;

import com.umeng.socialize.bean.SHARE_MEDIA;

import com.zhangwan.app.R;

import com.zhangwan.app.bean.CodeBean;
import com.zhangwan.app.bean.LoginBean;
import com.zhangwan.app.bean.event.LoginEvent;

import com.zhangwan.app.http.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 弹窗
 * Created by Administrator on 2018/3/24 0024.
 */

public class MyDialogUtil {

    private static Dialog myDialog;

    private Context mContext;

    public MyDialogUtil(Context mContext) {
        this.mContext = mContext;
    }

    //关闭窗口
    public static Dialog dissmiss() {
        myDialog.dismiss();
        return myDialog;
    }

    //兑换书券弹窗
    public static Dialog commentDialog(Context context, View view) {
        myDialog = new Dialog(context, R.style.common_dialog);
        myDialog.setContentView(view);
        myDialog.show();
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (WindowUtil.getScreenW(context) * 0.7);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        return myDialog;
    }

    //更改昵称
    public static Dialog modifyDialog(Context context, View view) {
        myDialog = new Dialog(context, R.style.common_dialog);
        myDialog.setContentView(view);
        myDialog.show();
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        return myDialog;
    }

}
