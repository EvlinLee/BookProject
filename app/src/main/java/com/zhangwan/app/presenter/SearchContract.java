package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class SearchContract {
    public interface View extends BaseUiView<SearchContract.Presenter> {
        //  搜索
        void showSearchResult(SearchBean data);

        void showHoData(List<HotBean> list);

        void showSaveSearchData( String[] split );

    }

    public interface Presenter extends BasePresenter {
        void getSearchResult(int start, String searchKey);

        void getHotData();

    }

    public interface Source extends BaseSource {
        void getSearchResult(int start, String searchKey, ApiCallBack<SearchBean> callBack);

        void getHoData(String number, String size, ApiCallBack<List<HotBean>> callBack);
    }
}
