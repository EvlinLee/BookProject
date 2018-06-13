package com.zhangwan.app.ui.bookstore.free;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.bookstore.BookStoreReposetory;
import com.zhangwan.app.ui.bookstore.BookStoreSource;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class BookStoreFreePresenter implements BookStoreFreeContract.Presenter  {
    BookStoreFreeContract.View mView;
    BookStoreSource            datas;

    public BookStoreFreePresenter(BookStoreFreeContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        datas = new BookStoreReposetory();
    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        datas.destroy();
    }

    @Override
    public void getLimitData(int number, int size) {
        datas.getLimitData(number,size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(mView == null) return;
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showLimitData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void getBoyData(int number, int size) {
        datas.getBoyData(number,size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showBoyData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void getGirlData(int number, int size) {
        datas.getGirlData(number,size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showGirlData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }
}
