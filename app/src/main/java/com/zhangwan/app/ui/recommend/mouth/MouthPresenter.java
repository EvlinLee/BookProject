package com.zhangwan.app.ui.recommend.mouth;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.bookstore.BookStoreReposetory;
import com.zhangwan.app.ui.bookstore.BookStoreSource;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeContract;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MouthPresenter implements MouthContract.Presenter  {
    MouthContract.View mView;
    BookStoreSource            datas;

    public MouthPresenter(MouthContract.View mView) {
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
    public void getMouthData(int number, int size) {
        datas.getLimitData(number,size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(mView == null) return;
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showMouthData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void getCommomData(int number, int size) {
        datas.getBoyData(number,size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showCommomData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }
}
