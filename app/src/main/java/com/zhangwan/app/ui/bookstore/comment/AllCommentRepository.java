package com.zhangwan.app.ui.bookstore.comment;

import com.gxtc.commlibrary.data.BaseRepository;
import com.zhangwan.app.bean.AllCommentBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllCommentRepository extends BaseRepository implements AllCommentContract.Source {
    @Override
    public void getComment(String Token, int bookId, int start, int size, ApiCallBack<List<AllCommentBean>> callBack) {
        Subscription sub = BookStoreApiService
                .getInstance()
                .apiAllComment(Token, bookId, start, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<AllCommentBean>>>(callBack));
        addSub(sub);
    }
}
