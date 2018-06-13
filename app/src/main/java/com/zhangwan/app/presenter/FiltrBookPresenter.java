package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.FiltrResultBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class FiltrBookPresenter implements FiltrBookContract.Presenter {

    private FiltrBookContract.View view;
    private FiltrBookContract.Source source;

    public FiltrBookPresenter(FiltrBookContract.View view) {
        this.view = view;
        this.view.setPresenter(this);// ***
        source = new FiltrBookRepository();// ***
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        source.destroy();
    }

    @Override
    public void getBookType() {
        source.getBookType(new ApiCallBack<List<BookTypeBean>>() {
            @Override
            public void onSuccess(List<BookTypeBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showBookType(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getFiltrResult(String type, String is_finish, String attr, int number, int size) {
        source.getFiltrResult(type, is_finish, attr, number, size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showResult(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }
}
