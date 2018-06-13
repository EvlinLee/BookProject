package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.FiltrResultBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class FiltrBookRepository extends BaseRepository implements FiltrBookContract.Source {

    @Override
    public void getBookType(ApiCallBack<List<BookTypeBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiBookType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookTypeBean>>>(callBack));
        addSub(sub);
    }

    @Override
    public void getFiltrResult(String type, String is_finish, String attr, int number, int size, ApiCallBack<List<BookBean>> callBack) {
        Subscription sub = ApiService
                .getInstance()
                .apiFiltrBook(type, attr, is_finish, number, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<BookBean>>>(callBack));
        addSub(sub);
    }
}
