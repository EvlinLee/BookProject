package com.zhangwan.app.ui.bookstore.news;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.bookstore.BookStoreReposetory;
import com.zhangwan.app.ui.bookstore.BookStoreSource;

import java.util.List;
import java.util.Map;

/**
 * Created by zzg on 2018/3/23.
 */

public class NewListPresenter implements NewListContract.Presenter  {
    NewListContract.View mView;
    BookStoreSource      datas;

    public NewListPresenter(NewListContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        datas = new BookStoreReposetory();

    }

    @Override
    public void getNesData(Map<String, String> map) {
        datas.getNewData(map, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showData(data);
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
