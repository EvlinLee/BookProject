package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class SearchRepository extends BaseRepository implements SearchContract.Source {
    @Override
    public void getSearchResult(int start, String searchKey, ApiCallBack<SearchBean> callBack) {
                Subscription sub = ApiService
                .getInstance()
                .apiSearchBook(start,searchKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<SearchBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void getHoData(String number, String size, ApiCallBack<List<HotBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiHotBook(Integer.valueOf(number),Integer.valueOf(size))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<HotBean>>>(callBack));
        addSub(sub);
    }

}
