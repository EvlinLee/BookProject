package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.LikeBean;
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

public class BookInfoRepository extends BaseRepository implements BookInfoContract.Source{
    @Override
    public void getBookInfo(String token,int bookId, ApiCallBack<BookInfoBean> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiBookInfo(token,bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<BookInfoBean>>(callBack));
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
}
