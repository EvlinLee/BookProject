package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BookInfoPresenter implements BookInfoContract.Presenter {

    private BookInfoContract.View view;
    private BookInfoContract.Source source;

    public BookInfoPresenter(BookInfoContract.View view) {
        this.view = view;
        source = new BookInfoRepository();
        this.view.setPresenter(this);
    }

    @Override
    public void getBookInfo(String token,int bookId) {
        view.showLoad();
        source.getBookInfo(token,bookId, new ApiCallBack<BookInfoBean>() {
            @Override
            public void onSuccess(BookInfoBean data) {
                view.showLoadFinish();
                view.showBookInfo(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void getLikeBook(int size, int number) {
        source.getLikeBook(size, number, new ApiCallBack<List<LikeBean>>() {
            @Override
            public void onSuccess(List<LikeBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showLike(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        source.destroy();
    }
}
