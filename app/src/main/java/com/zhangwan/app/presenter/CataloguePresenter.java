package com.zhangwan.app.presenter;


import android.view.View;

import com.zhangwan.app.R;
import com.zhangwan.app.adapter.ChatperMenuAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.utils.ErrorCodeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laoshiren on 2018/3/21.
 * 章节目录
 */

public class CataloguePresenter implements CatalogueContract.Presenter {
    private CatalogueContract.Source mData;
    private CatalogueContract.View mView;
    private String bookId;
    private String total;
    private String token;

    public CataloguePresenter(CatalogueContract.View mView, String token, String bookId, String total) {
        this.mView = mView;
        this.mView.setPresenter(this);
        this.total = total;
        this.bookId = bookId;
        this.token = token;
        mData = new CatalogueRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mData.destroy();
    }

    @Override
    public void getCalogue() {
        mView.showLoad();
        mData.geCalogue(token, bookId, total, new ApiCallBack<ChatperBean>() {
            @Override
            public void onSuccess(ChatperBean data) {
                mView.showLoadFinish();
                if (data.getTotal() == 0) {
                    mView.showEmpty();
                    return;
                }
                mView.showCalogue(data);
            }

            @Override
            public void onError(String status, String message) {
                mView.showLoadFinish();
                ErrorCodeUtil.handleErr(mView, status, message);
            }
        });
    }

    @Override
    public void getChatperMenu() {
        mView.showChatperMenu(getChatperMenus());
    }


    private List<ChatperMenuBean> getChatperMenus() {
        final List<ChatperMenuBean> list = new ArrayList<>();
        int size = Integer.valueOf(total) / 100;
        int n = 1;
        int m = 100;
        for (int i = 0; i < size; i++) {
            ChatperMenuBean bean = new ChatperMenuBean();
            bean.setName1(n);
            bean.setName2(m);
            n = n + 100;
            m = m + 100;
            list.add(bean);
        }
        ChatperMenuBean bean = new ChatperMenuBean();
        bean.setName1(n);
        bean.setName2(Integer.valueOf(total));
        list.add(bean);

        return list;
    }
}
