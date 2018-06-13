package com.zhangwan.app.ui.mine.message;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MessageContract {

    public  interface View extends BaseUiView<Presenter> {
        void  showData(List<MessageBean> datas);
    }

    public interface Presenter extends BasePresenter {
        void getMessageListData(int number, int size);
    }

    public interface Source extends BaseSource {
        void getResult(int number, int size, ApiCallBack<List<MessageBean>> callBack);
    }
}
