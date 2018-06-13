package com.zhangwan.app.ui;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/4/3.
 */

public class UpLoadFileRespstory extends BaseRepository implements UpLoadFileSource{


    @Override
    public void UpLoadFile(RequestBody body, ApiCallBack<Object> callBack) {
        Subscription sub = MineApiService
                .getInstance().uploadFile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<Object>(callBack));
        addSub(sub);
    }
}
