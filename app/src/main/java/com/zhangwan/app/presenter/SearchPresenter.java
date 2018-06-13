package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.utils.ACache;
import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View view;
    private SearchContract.Source source;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        source = new SearchRepository();
        this.view.setPresenter(this);
    }

    @Override
    public void getSearchResult(int start, final String searchKey) {
        view.showLoad();

        source.getSearchResult(start, searchKey, new ApiCallBack<SearchBean>() {
            @Override
            public void onSuccess(SearchBean data) {
                view.showLoadFinish();

                if (data == null || data.getList().size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showSearchResult(data);


            }

            @Override
            public void onError(String status, String message) {
                view.showLoadFinish();

                ErrorCodeUtil.handleErr(view, status, message);
            }
        });
    }

    @Override
    public void getHotData() {
        view.showLoad();
        source.getHoData("0", "10", new ApiCallBack<List<HotBean>>() {
            @Override
            public void onSuccess(List<HotBean> data) {
                view.showLoadFinish();

                if (data != null && data.size() > 0) {

                    view.showHoData(data);
                    String searchKey = SpUtil.getSearchKey(MyApplication.getInstance());
                    String[] split = searchKey.split(",");

                    view.showSaveSearchData(split);

                }
            }

            @Override
            public void onError(String status, String message) {
                view.showLoadFinish();
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
