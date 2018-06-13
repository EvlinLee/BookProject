package com.zhangwan.app.ui.bookstore.like;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.GessLikeListAdater;
import com.zhangwan.app.adapter.HotListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListContract;
import com.zhangwan.app.ui.bookstore.hot.HotListPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by zzg on 2018/3/23.
 */
public class GessLikeListActivity extends BaseTitleActivity implements GessLikeListContract.View {

    @BindView(R.id.refreshlayout) SwipeRefreshLayout        refreshlayout;
    @BindView(R.id.recyclerView)  RecyclerView              recyclerView;
    private                       GessLikeListAdater             mGessLikeListAdater;
    private                       GessLikeListContract.Presenter mPresenter;
    private int number  = 1;
    public final static int size  = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("猜你喜欢").showBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshlayout.setColorSchemeResources( R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3);
        recyclerView.setLoadMoreView(R.layout.model_footview_loadmore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        super.initData();
        getBaseLoadingView().showLoading();
        new GessLikeListPresenter(this);
        mGessLikeListAdater = new GessLikeListAdater(this,new ArrayList<LikeBean>(), R.layout.hot_list_item_layout);
        recyclerView.setAdapter(mGessLikeListAdater);
        mPresenter.getLikeBook(size,number);
        mGessLikeListAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                BookInfoActivity.gotoBookInfoActivity(v.getContext(),mGessLikeListAdater.getList().get(position).getBookId());
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                number = 1;
                mPresenter.getLikeBook(size,number);
                recyclerView.reLoadFinish();
            }
        });
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                number = number + 1;
                mPresenter.getLikeBook(size,number);
            }
        });
    }

    @Override
    public void showLike(List<LikeBean> datas) {
            getBaseLoadingView().hideLoading();
            if(number == 1){
                refreshlayout.setRefreshing(false);
                recyclerView.notifyChangeData(datas,mGessLikeListAdater);
            }else {
                recyclerView.changeData(datas,mGessLikeListAdater);
            }
    }


    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {
        getBaseLoadingView().hideLoading();
        if(number == 1){
            getBaseEmptyView().showEmptyContent();
        }else {
            recyclerView.loadFinish();
        }
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        getBaseLoadingView().hideLoading();
        ToastUtil.showShort(this,msg);
    }

    @Override
    public void showNetError() {}


    @Override
    public void setPresenter(GessLikeListContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
