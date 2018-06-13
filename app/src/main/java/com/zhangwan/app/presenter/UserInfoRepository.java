package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.ModifyInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class UserInfoRepository extends BaseRepository implements UserInfoContract.Source {
    @Override
    public void getHotData(Map<String, String> map, ApiCallBack<Object> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .apiModifyInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }

    @Override
    public void getUserInfo(String token, ApiCallBack<ModifyInfoBean> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<ModifyInfoBean>>(callBack));
        addSub(sub);
    }
}
