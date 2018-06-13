package com.zhangwan.app.presenter;


import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.http.service.RecommendApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjr on 2017/2/15.
 */

public class RecommendRepository extends BaseRepository implements RecommendContract.Source {


    @Override
    public void getData(String type, ApiCallBack<List<RecommendBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiRecommend(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<RecommendBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getBanner(String type, ApiCallBack<List<RecommendBannerBean>> callBack) {
        Subscription sub = RecommendApiService
                .getInstance()
                .getBanner(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<RecommendBannerBean>>>(callBack));
        addSub(sub);
    }
}
