package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.http.ApiCallBack;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View view;
    private HistoryContract.Source source;

    public HistoryPresenter(HistoryContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new HistoryRepository();
    }

    @Override
    public void getHistory(int start, String token) {
        source.getHistory(start, token, new ApiCallBack<HistoryBean>() {
            @Override
            public void onSuccess(HistoryBean data) {
                view.showHistory(data);
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
