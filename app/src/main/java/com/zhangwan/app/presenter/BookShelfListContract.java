package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class BookShelfListContract {
    public interface View extends BaseUiView<Presenter> {
        //  书架列表
        void showBookRackData(BookRackBean data);

        //轮播
        void showBookRackBanner(List<BookShelfBannerBean> data);

        //删除书架里的书籍
        void showBookRack(Object data);
    }

    public interface Presenter extends BasePresenter {
        void getBookRackData(int start, String uniqueId);

        void getBookRackBanner(String type);

        void deleteBookRack(String id);
    }

    public interface Source extends BaseSource {
        void getBookRackData(int start, String uniqueId, ApiCallBack<BookRackBean> callBack);

        void showBookRackBanner(String type, ApiCallBack<BookShelfBannerBean> callBack);

        void deleteBookRack(String id, ApiCallBack<Object> callBack);
    }
}
