package com.zhangwan.app.ui.bookstore;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;

import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zzg on 2018/3/23.
 */

public class BookStoreReposetory extends BaseRepository implements BookStoreSource {

    @Override
    public void getNewData(Map<String, String> map, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance().getNewList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getHotData(int number, int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance().getHotList(number,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }


    @Override
    public void getLimitData(int start,int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance().getLimitData(start,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }

    //需要更换接口
    @Override
    public void getBoyData(int number,int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance().getHotList(number,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }
    //需要更换接口
    @Override
    public void getGirlData(int number,int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance().getHotList(number,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }
}
