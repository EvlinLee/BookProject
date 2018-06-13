package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.ModifyInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.ui.UpLoadFileRespstory;
import com.zhangwan.app.ui.UpLoadFileSource;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class UserInfoPresenter implements UserInfoContract.Presenter {

    private UserInfoContract.View view;
    public UserInfoContract.Source source;
    public UpLoadFileSource data;
    public UserInfoPresenter(UserInfoContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new UserInfoRepository();
        data = new UpLoadFileRespstory();
    }

    @Override
    public void getResult(Map<String, String> map) {
        source.getHotData(map, new ApiCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                if(view == null) return;
                if(data == null){
                    view.showEmpty();
                    return;
                }
                view.showResult(data);
            }

            @Override
            public void onError(String status, String message) {
                if(view == null) return;
                view.showError(status, message);
            }
        });
    }

    @Override
    public void getUserInfo(String token) {
        source.getUserInfo(token, new ApiCallBack<ModifyInfoBean>() {
            @Override
            public void onSuccess(ModifyInfoBean data) {
                if(view == null) return;
                if(data == null){
                    view.showEmpty();
                    return;
                }
                view.showUserInfo(data);
            }

            @Override
            public void onError(String status, String message) {
                if(view == null) return;
                view.showError(status, message);
            }
        });
    }

    @Override
    public void UpLoadFile(File file) {
        RequestBody body =
                new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                    RequestBody.create(MediaType.parse("image*//**//*"), file)).build();
        data.UpLoadFile(body, new ApiCallBack<Object>() {

            @Override
            public void onSuccess(Object data) {
                if(view == null) return;
                if(data == null){
                    view.showEmpty();
                    return;
                }
                view.showPicUrl(data);
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
        data.destroy();
    }
}
