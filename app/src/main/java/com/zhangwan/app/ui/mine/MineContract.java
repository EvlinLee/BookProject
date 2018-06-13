package com.zhangwan.app.ui.mine;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MineContract {

    public  interface View extends BaseUiView<Presenter> {
       public void  showData(Object datas);
    }

    public interface Presenter extends BasePresenter {
        public void setFeedback(String token,  String content, String type,String phone);
    }
}
