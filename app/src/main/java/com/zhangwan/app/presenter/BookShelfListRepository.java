package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.http.service.BookShelfApiService;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class BookShelfListRepository extends BaseRepository implements BookShelfListContract.Source {
    @Override
    public void getBookRackData(int start, String uniqueId, ApiCallBack<BookRackBean> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiBookRack(start, uniqueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<BookRackBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void showBookRackBanner(String token, ApiCallBack<BookShelfBannerBean> callBack) {
        Subscription sub = BookShelfApiService
                .getInstance()
                .getBanner(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<BookShelfBannerBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void deleteBookRack(String id, ApiCallBack<Object> callBack) {
        Subscription sub = BookShelfApiService
                .getInstance()
                .deleteBookRack(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<Object>>(callBack));
        addSub(sub);
    }
}
