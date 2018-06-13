package com.zhangwan.app.ui.mine.friend;

import com.zhangwan.app.bean.InviteUrlBean;
import com.zhangwan.app.http.ApiCallBack;

public class InvitePresenter implements InviteContract.Presenter {

    private InviteContract.View view;
    private InviteContract.Source source;

    public InvitePresenter(InviteContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new InviteRepository();
    }

    @Override
    public void getFriend(String token) {
        source.getFriend(token, new ApiCallBack<InviteUrlBean>() {
            @Override
            public void onSuccess(InviteUrlBean data) {
                view.showUrl(data);
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
