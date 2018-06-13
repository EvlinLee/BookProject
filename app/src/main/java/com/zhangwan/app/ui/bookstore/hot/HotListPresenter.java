package com.zhangwan.app.ui.bookstore.hot;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.bookstore.BookStoreReposetory;
import com.zhangwan.app.ui.bookstore.BookStoreSource;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class HotListPresenter implements HotListContract.Presenter  {
    HotListContract.View mView;
    BookStoreSource      datas;

    public HotListPresenter(HotListContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        datas = new BookStoreReposetory();

    }

    @Override
    public void getHotData(int number,int size) {
        datas.getHotData(number,size, new ApiCallBack<List<BookBean>>() {
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
