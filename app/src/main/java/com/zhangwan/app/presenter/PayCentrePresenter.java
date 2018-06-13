package com.zhangwan.app.presenter;


import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laoshiren on 2018/3/31.
 * 充值中心
 */

public class PayCentrePresenter implements PayCentreContract.Presenter {
    private PayCentreContract.Source mData;
    private PayCentreContract.View mView;
    private String type;
    private String type2;


    public PayCentrePresenter(PayCentreContract.View mView, String type, String type2) {
        this.mView = mView;
        this.mView.setPresenter(this);
        this.type = type;
        this.type2 = type2;

        mData = new PayCentreRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mData.destroy();
    }


    @Override
    public void getPayList() {
        mView.showLoad();
        mData.getPayList(type, new ApiCallBack<List<PayListBean>>() {
            @Override
            public void onSuccess(List<PayListBean> data) {
                mView.showLoadFinish();
                if (data == null || data.size() == 0) {
                    mView.showEmpty();
                    return;
                }
                final List<PayListBean> list = data;

                for (int i = 0; i < list.size(); i++) {
                    PayListBean bean = list.get(i);
                    bean.setListType("0");
                    list.set(i, bean);

                }
                mData.getPayList2(type2, new ApiCallBack<List<PayListBean>>() {
                    @Override
                    public void onSuccess(List<PayListBean> data) {
                        for (int i = 0; i < data.size(); i++) {
                            PayListBean bean = data.get(i);
                            bean.setListType("1");
                            data.set(i, bean);
                        }
                        list.addAll(data);
                        mView.showPayList(list);
                    }

                    @Override
                    public void onError(String status, String message) {

                        ErrorCodeUtil.handleErr(mView, status, message);
                    }
                });

            }

            @Override
            public void onError(String status, String message) {
                mView.showLoadFinish();
                ErrorCodeUtil.handleErr(mView, status, message);
            }
        });
    }

}
