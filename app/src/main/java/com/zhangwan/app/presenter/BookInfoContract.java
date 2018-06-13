package com.zhangwan.app.presenter;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class BookInfoContract {
    public interface View extends BaseUiView<BookInfoContract.Presenter> {
        //  小说详情
        void showBookInfo(BookInfoBean data);
        //  喜欢这部书的人还喜欢
        void showLike(List<LikeBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getBookInfo(String token,int bookId);
        void getLikeBook(int size, int number);
    }

    public interface Source extends BaseSource {
        void getBookInfo(String token,int bookId, ApiCallBack<BookInfoBean> callBack);
        void getLikeBook(int size, int number, ApiCallBack<List<LikeBean>> callBack);
    }
}
