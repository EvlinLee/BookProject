package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.ModifyInfoBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiResponseBean;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class UserInfoContract {
    public interface View extends BaseUiView<UserInfoContract.Presenter> {
        //  搜索
        void showResult(Object data);
        void showUserInfo(ModifyInfoBean data);
        void showPicUrl(Object data);
    }

    public interface Presenter extends BasePresenter {
        void getResult(Map<String,String> map);
        void getUserInfo(String token);
        public void UpLoadFile(File file);
    }

    public interface Source extends BaseSource {
        void getHotData(Map<String,String> map, ApiCallBack<Object> callBack);
        void getUserInfo(String token, ApiCallBack<ModifyInfoBean> callBack);
    }
}
