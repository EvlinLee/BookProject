package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUserView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/31.
 * 充值中心
 */

public class PayCentreContract {

    public interface View extends BaseUserView<Presenter> {
        void showPayList(List<PayListBean> list);


    }

    public interface Presenter extends BasePresenter {
        void getPayList();


    }

    public interface Source extends BaseSource {
        void getPayList( String type, ApiCallBack<List<PayListBean>> callBack);
        void getPayList2( String type, ApiCallBack<List<PayListBean>> callBack);
    }
}
