package com.zhangwan.app.ui.mine.friend;

import com.zhangwan.app.bean.FriendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;

public class FriendPresenter implements FriendContract.Presenter {

    private FriendContract.View view;
    private FriendContract.Source source;

    public FriendPresenter(FriendContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new FriendRepository();
    }

    @Override
    public void getFriend(String token) {
        view.showLoad();
        source.getFriend(token, new ApiCallBack<FriendBean>() {
            @Override
            public void onSuccess(FriendBean data) {
                view.showLoadFinish();
                view.showFriend(data);
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
