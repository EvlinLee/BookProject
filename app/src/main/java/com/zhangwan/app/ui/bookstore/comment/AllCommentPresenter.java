package com.zhangwan.app.ui.bookstore.comment;

import com.zhangwan.app.bean.AllCommentBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

public class AllCommentPresenter implements AllCommentContract.Presenter {

    private AllCommentContract.View view;
    private AllCommentContract.Source source;

    public AllCommentPresenter(AllCommentContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
        source = new AllCommentRepository();
    }

    @Override
    public void getComment(String Token, int bookId, int start, int size) {
        source.getComment(Token, bookId, start, size, new ApiCallBack<List<AllCommentBean>>() {
            @Override
            public void onSuccess(List<AllCommentBean> data) {
                view.showLoadFinish();
                if (data == null || data.size() == 0) {
                    view.showEmpty();
                    return;
                }
                view.showComment(data);
            }

            @Override
            public void onError(String status, String message) {
                view.showError(status, message);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        source.destroy();
    }
}
