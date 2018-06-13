package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class BookShelfListPresenter implements BookShelfListContract.Presenter {

    private BookShelfListContract.View view;
    private BookShelfListContract.Source source;

    public BookShelfListPresenter(BookShelfListContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new BookShelfListRepository();
    }

    @Override
    public void getBookRackData(int start, String uniqueId) {
        source.getBookRackData(start, uniqueId, new ApiCallBack<BookRackBean>() {
            @Override
            public void onSuccess(BookRackBean data) {
                view.showLoadFinish();
//                if (data.getBs() == null || data.getBs().size() == 0) {
//                    view.showEmpty();
//                    return;
//                }
                view.showBookRackData(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getBookRackBanner(String token) {
        source.showBookRackBanner(token, new ApiCallBack<BookShelfBannerBean>() {
            @Override
            public void onSuccess(BookShelfBannerBean data) {
                view.showLoadFinish();
                if (data == null ) {
                    return;
                }
                List<BookShelfBannerBean> datas = new ArrayList<>();
                datas.add(data);
                view.showBookRackBanner(datas);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void deleteBookRack(String id) {
        source.deleteBookRack(id, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if (data == null) {
                    view.showEmpty();
                    return;
                }
                view.showBookRack(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status,message);
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
