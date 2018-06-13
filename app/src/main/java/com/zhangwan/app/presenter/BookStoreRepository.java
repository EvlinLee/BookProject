package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.http.service.BookStoreApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class BookStoreRepository extends BaseRepository implements BookStoreContract.Source {

    @Override
    public void getHotData(int number, int size, ApiCallBack<List<HotBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiHotBook(number, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<HotBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getBannerData(String type, ApiCallBack<List<BannerBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiBanner(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BannerBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getLikeBook(int size, int number, ApiCallBack<List<LikeBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiLike(size, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<LikeBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getRecommend(String type, ApiCallBack<List<RecommendBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiRecommend(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<RecommendBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getFreeBook(int number, int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance()
                .getLimitData(number, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }
}
