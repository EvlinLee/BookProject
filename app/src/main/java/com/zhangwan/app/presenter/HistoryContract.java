package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.http.ApiCallBack;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class HistoryContract {
    public interface View extends BaseUiView<HistoryContract.Presenter> {
        //  历史记录
        void showHistory(HistoryBean data);
    }

    public interface Presenter extends BasePresenter {
        void getHistory(int start, String token);
    }

    public interface Source extends BaseSource {
        void getHistory(int start, String token, ApiCallBack<HistoryBean> callBack);
    }
}
