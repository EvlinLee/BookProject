package com.zhangwan.app.ui.mine.friend;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.InviteUrlBean;
import com.zhangwan.app.http.ApiCallBack;

public class InviteContract {
    public interface View extends BaseUiView<Presenter> {
        //  邀请链接
        void showUrl(InviteUrlBean data);
    }

    public interface Presenter extends BasePresenter {
        void getFriend(String token);
    }

    public interface Source extends BaseSource {
        void getFriend(String token, ApiCallBack<InviteUrlBean> callBack);
    }
}
