package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class HistoryRepository extends BaseRepository implements HistoryContract.Source{
    @Override
    public void getHistory(int start, String token, ApiCallBack<HistoryBean> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiHistory(start, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<HistoryBean>>(callBack));
        addSub(sub);
    }
}
