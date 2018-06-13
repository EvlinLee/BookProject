package com.zhangwan.app.presenter;

import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class BookStorePresenter implements BookStoreContract.Presenter {

    private BookStoreContract.View view;
    private BookStoreContract.Source source;


    public BookStorePresenter(BookStoreContract.View view) {
        this.view = view;
        this.view.setPresenter(this);// ***
        source = new BookStoreRepository();// ***
    }

    @Override
    public void getHotData(int number, int size) {
        source.getHotData(number, size, new ApiCallBack<List<HotBean>>() {
            @Override
            public void onSuccess(List<HotBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {

                    return;
                }
                view.showHotData(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getBannerData(String type) {
        source.getBannerData(type, new ApiCallBack<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {

                    return;
                }
                view.showBanner(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getLikeBook(int size, int number) {
        source.getLikeBook(size, number, new ApiCallBack<List<LikeBean>>() {
            @Override
            public void onSuccess(List<LikeBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {

                    return;
                }
                view.showLike(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getRecommend(String type, final int tp) {
        source.getRecommend(type, new ApiCallBack<List<RecommendBean>>() {
            @Override
            public void onSuccess(List<RecommendBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {

                    return;
                }
                view.showRecommend(data,tp);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getFreeBook(int number, int size) {
        source.getFreeBook(number, size, new ApiCallBack<List<BookBean>>() {
            @Override
            public void onSuccess(List<BookBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {

                    return;
                }
                view.showFree(data);
            }

            @Override
            public void onError(String status, String message) {
                ErrorCodeUtil.handleErr(view, status, message);
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
