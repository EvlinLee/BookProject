package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.FiltrResultBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class FiltrBookContract {
    public interface View extends BaseUiView<FiltrBookContract.Presenter> {
        //  小说类型
        void showBookType(List<BookTypeBean> data);

        //  搜索结果
        void showResult(List<BookBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getBookType();
        void getFiltrResult(String type, String is_finish, String attr, int number, int size);
    }

    public interface Source extends BaseSource {
        void getBookType(ApiCallBack<List<BookTypeBean>> callBack);

        void getFiltrResult(String type, String is_finish, String attr, int number, int size, ApiCallBack<List<BookBean>> callBack);
    }
}
