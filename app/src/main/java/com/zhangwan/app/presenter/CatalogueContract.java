package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUserView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/21.
 * 章节目录
 */

public class CatalogueContract {


    public interface View extends BaseUserView<Presenter> {

        void showCalogue(ChatperBean bean);

        void showChatperMenu(List<ChatperMenuBean> list);
    }

    public interface Presenter extends BasePresenter {

        void getCalogue();

        void getChatperMenu();

    }

    public interface Source extends BaseSource {
        void geCalogue(String token, String bookId, String total, ApiCallBack<ChatperBean> callBack);
    }
}
