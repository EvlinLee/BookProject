package com.zhangwan.app.ui.mine.recharge;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.RechargeHistoryBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RechargeHistoryRepository extends BaseRepository implements RechargeHistoryContract.Source {
    @Override
    public void getResult(String token, int start, int type, ApiCallBack<List<RechargeHistoryBean>> callBack) {
        Subscription sub = MineApiService
                .getInstance()
                .apiRechargeHistory(token, start, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<RechargeHistoryBean>>>(callBack));
        addSub(sub);
    }
}
