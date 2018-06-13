package com.zhangwan.app.ui.mine.recharge;

import com.zhangwan.app.bean.RechargeHistoryBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

public class RechargeHistoryPresenter implements RechargeHistoryContract.Presenter {

    private RechargeHistoryContract.View view;
    private RechargeHistoryContract.Source source;

    public RechargeHistoryPresenter(RechargeHistoryContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new RechargeHistoryRepository();
    }

    @Override
    public void getRechargeHistory(String token, int start, int type) {
        source.getResult(token, start, type, new ApiCallBack<List<RechargeHistoryBean>>() {
            @Override
            public void onSuccess(List<RechargeHistoryBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showRechargeHistory(data);
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
