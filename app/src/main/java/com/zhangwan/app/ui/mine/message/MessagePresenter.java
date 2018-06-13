package com.zhangwan.app.ui.mine.message;

import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MessagePresenter implements MessageContract.Presenter {
    private MessageContract.View mView;
    private MessageContract.Source source;

    public MessagePresenter(MessageContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        source = new MessageRepository();
    }

    @Override
    public void start() {
    }

    @Override
    public void destroy() {
        source.destroy();
    }

    @Override
    public void getMessageListData(int number, int size) {
        source.getResult(number, size, new ApiCallBack<List<MessageBean>>() {
            @Override
            public void onSuccess(List<MessageBean> data) {
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showData(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showError(status, message);
            }
        });
    }
}
