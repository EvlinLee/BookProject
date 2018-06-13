package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.CountWelfareBean;
import com.zhangwan.app.bean.SignBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.http.service.MineApiService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class MineRepository extends BaseRepository implements MineContract.Source {
    @Override
    public void getUserInfo(String token, ApiCallBack<UserInfoBean> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<UserInfoBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void getSign(String token, ApiCallBack<SignBean> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getSign(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<SignBean>(callBack));
        addSub(sub);
    }

    @Override
    public void getCountWelfare(String token, ApiCallBack<CountWelfareBean> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getCountWelfare(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<CountWelfareBean>>(callBack));
        addSub(sub);
    }
}
