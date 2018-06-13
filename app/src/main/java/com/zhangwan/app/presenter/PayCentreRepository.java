package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.http.service.ReadApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjr on 2018/3/31 .
 */

public class PayCentreRepository extends BaseRepository implements PayCentreContract.Source {


    @Override
    public void getPayList(String type, ApiCallBack<List<PayListBean>> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getPayList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<PayListBean>>>(callBack));
        addSub(sub);
    }
    @Override
    public void getPayList2(String type, ApiCallBack<List<PayListBean>> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getPayList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<PayListBean>>>(callBack));
        addSub(sub);
    }
}
