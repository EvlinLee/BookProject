package com.zhangwan.app.ui.bookstore.like;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.presenter.BookStoreContract;
import com.zhangwan.app.presenter.BookStoreRepository;
import com.zhangwan.app.ui.bookstore.BookStoreReposetory;
import com.zhangwan.app.ui.bookstore.BookStoreSource;
import com.zhangwan.app.ui.bookstore.hot.HotListContract;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class GessLikeListPresenter implements GessLikeListContract.Presenter  {
    GessLikeListContract.View mView;
    private BookStoreContract.Source datas;

    public GessLikeListPresenter(GessLikeListContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        datas = new BookStoreRepository();// ***

    }

    @Override
    public void getLikeBook(int number,int size) {
        datas.getLikeBook(number,size, new ApiCallBack<List<LikeBean>>() {
            @Override
            public void onSuccess(List<LikeBean> data) {
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showLike(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        datas.destroy();
    }

}
