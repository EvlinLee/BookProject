package com.zhangwan.app.ui.mine.friend;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.FriendBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;

public class FriendContract {
    public interface View extends BaseUiView<Presenter> {
        //  好友列表
        void showFriend(FriendBean data);
    }

    public interface Presenter extends BasePresenter {
        void getFriend(String token);
    }

    public interface Source extends BaseSource {
        void getFriend(String token, ApiCallBack<FriendBean> callBack);
    }
}
