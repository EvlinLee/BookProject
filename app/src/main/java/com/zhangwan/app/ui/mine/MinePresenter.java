package com.zhangwan.app.ui.mine;

import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.mine.ticket.TicketListContract;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class MinePresenter implements MineContract.Presenter  {
    MineContract.View mView;
    MineReposetory      datas;

    public MinePresenter(MineContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
        datas = new MineReposetory();

    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        datas.destroy();
    }

    @Override
    public void setFeedback(String token, String content, String type, String phone) {
        datas.setFeedback(token,content,type,phone, new ApiCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                if(mView == null) return;
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }
}
