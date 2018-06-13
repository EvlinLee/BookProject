package com.zhangwan.app.http;

import com.gxtc.commlibrary.utils.NetworkUtil;
import com.zhangwan.app.MyApplication;


import java.net.SocketTimeoutException;

import rx.Observer;

/**
 * Created by sjr on 17/1/18.
 */

public class ApiObserver<T> implements Observer<T> {

    public static final String NET_ERROR = "400";
    public static final String SERVER_ERROR = "500";
    public static final String SERVER_TIME_OUT = "303";
    public static final String SERVER_SUCCESS = "success";

    private ApiCallBack callBack;

    public ApiObserver(ApiCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onCompleted() {
        callBack.onCompleted();
    }

    @Override
    public void onNext(T t) {
        if (t instanceof ApiResponseBean) {
            ApiResponseBean apiResponseBean = (ApiResponseBean) t;

            if (!"success".equals(apiResponseBean.getStatus())) {
                //书币不足，也算请求成功
                if ("书币不足".equals(apiResponseBean.getMsg())) {
                    callBack.onSuccess(apiResponseBean.getResult());
                    return;
                }
                callBack.onError(apiResponseBean.getStatus(), apiResponseBean.getMsg());
                return;
            }
            callBack.onSuccess(apiResponseBean.getResult());
            return;
        }

        callBack.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //有网情况服务器崩溃
//        if (NetworkUtil.isConnected(MyApplication.getInstance())) {
//            callBack.onError(SERVER_ERROR, "服务器繁忙");
//
//            //无网络错误
//       } else
        if (e instanceof SocketTimeoutException) {
            callBack.onError(SERVER_TIME_OUT, "链接超时，请重新链接");
        } else if (!NetworkUtil.isConnected(MyApplication.getInstance())){
            callBack.onError(NET_ERROR, "暂时没有网络，请检查手机网络");
        }else {
            callBack.onError(NET_ERROR, "请求失败，请稍后再试");
        }

    }


}
