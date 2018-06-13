package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.CountWelfareBean;
import com.zhangwan.app.bean.SignBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class MinePresenter implements MineContract.Presenter {

    private MineContract.View view;
    private MineContract.Source source;

    public MinePresenter(MineContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new MineRepository();
    }

    @Override
    public void getUserInfo(String token) {
        source.getUserInfo(token, new ApiCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                view.showUserInfo(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void getSign(String token) {
        source.getSign(token, new ApiCallBack<SignBean>() {
            @Override
            public void onSuccess(SignBean data) {
                view.showSign(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void getCountWelfare(String token) {
        source.getCountWelfare(token, new ApiCallBack<CountWelfareBean>() {
            @Override
            public void onSuccess(CountWelfareBean data) {
                view.showCountWelfare(data);
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
