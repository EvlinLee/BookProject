package com.zhangwan.app.ui.bookstore.comment;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.app.bean.AllCommentBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

public class AllCommentContract {
    public interface View extends BaseUiView<Presenter> {
        //  热门小说
        void showComment(List<AllCommentBean> data);
    }

    public interface Presenter extends BasePresenter {
        void getComment(String Token, int bookId, int start, int size);
    }

    public interface Source extends BaseSource {
        void getComment(String Token, int bookId, int start, int size, ApiCallBack<List<AllCommentBean>> callBack);
    }
}
