package com.zhangwan.app.ui.mine.recharge;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.RechargeHistoryBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

public class RechargeHistoryContract {
    public interface View extends BaseUiView<Presenter> {

        //  充值记录
        void showRechargeHistory(List<RechargeHistoryBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getRechargeHistory(String token, int start, int type);
    }

    public interface Source extends BaseSource {
        void getResult(String token, int start, int type, ApiCallBack<List<RechargeHistoryBean>> callBack);
    }
}
