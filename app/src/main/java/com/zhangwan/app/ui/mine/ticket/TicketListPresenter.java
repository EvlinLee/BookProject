package com.zhangwan.app.ui.mine.ticket;

import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.ui.mine.MineReposetory;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class TicketListPresenter implements TicketListContract.Presenter  {
    TicketListContract.View mView;
    MineReposetory      datas;

    public TicketListPresenter(TicketListContract.View mView) {
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
    public void getTicketListData(String parm, int number, int size) {
        datas.getTicketListData("",number,size, new ApiCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                if(mView == null) return;
                if(data == null || data.size() == 0){
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
