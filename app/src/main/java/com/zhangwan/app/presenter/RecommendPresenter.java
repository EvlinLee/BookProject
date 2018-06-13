package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/21.
 */

public class RecommendPresenter implements RecommendContract.Presenter {
    private RecommendContract.Source mData;
    private RecommendContract.View mView;
    private FiltrBookContract.Source source;

    private String type;


    public RecommendPresenter(RecommendContract.View mView, String type) {
        this.mView = mView;
        this.mView.setPresenter(this);
        mData = new RecommendRepository();
        source = new FiltrBookRepository();// ***
        this.type = type;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mData.destroy();
    }

    @Override
    public void getData() {

        mData.getData(type, new ApiCallBack<List<RecommendBean>>() {

            @Override
            public void onSuccess(List<RecommendBean> data) {

                mView.showLoadFinish();
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showData(data);

            }

            @Override
            public void onError(String errorCode, String message) {

                ErrorCodeUtil.handleErr(mView, errorCode, message);
            }
        });
    }

    @Override
    public void getBookType() {
        source.getBookType(new ApiCallBack<List<BookTypeBean>>() {
            @Override
            public void onSuccess(List<BookTypeBean> data) {
                if (mView == null) return;
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showBookType(data);
            }

            @Override
            public void onError(String status, String message) {
                if (mView == null) return;
                mView.showError(status, message);
            }
        });
    }

    @Override
    public void getFiltrResult(String type, String is_finish, String attr, int number,
                               int size) {
        source.getFiltrResult(type, is_finish, attr, number, size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                if (mView == null) return;
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showFiltrResult(data);
            }

            @Override
            public void onError(String status, String message) {
                if (mView == null) return;
                mView.showError(status, message);
            }
        });
    }

    @Override
    public void getBanner(String type) {
        mData.getBanner(type, new ApiCallBack<List<RecommendBannerBean>>() {
            @Override
            public void onSuccess(List<RecommendBannerBean> data) {
                mView.showLoadFinish();
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showBanner(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status, message);
            }
        });
    }
}
