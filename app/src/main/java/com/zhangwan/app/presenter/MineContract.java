package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.CountWelfareBean;
import com.zhangwan.app.bean.SignBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class MineContract {
    public interface View extends BaseUiView<MineContract.Presenter> {
        //  个人信息
        void showUserInfo(UserInfoBean data);

        //  签到状态
        void showSign(SignBean data);

        //  福利中心
        void showCountWelfare(CountWelfareBean data);
    }

    public interface Presenter extends BasePresenter {
        void getUserInfo(String token);

        void getSign(String token);

        void getCountWelfare(String token);
    }

    public interface Source extends BaseSource {
        void getUserInfo(String token, ApiCallBack<UserInfoBean> callBack);

        void getSign(String token, ApiCallBack<SignBean> callBack);

        void getCountWelfare(String token, ApiCallBack<CountWelfareBean> callBack);
    }
}
