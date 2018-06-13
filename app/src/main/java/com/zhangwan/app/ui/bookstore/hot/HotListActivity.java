package com.zhangwan.app.ui.bookstore.hot;

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
import com.zhangwan.app.adapter.HotListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by zzg on 2018/3/23.
 */
public class HotListActivity extends BaseTitleActivity implements HotListContract.View {

    @BindView(R.id.refreshlayout) SwipeRefreshLayout        refreshlayout;
    @BindView(R.id.recyclerView)  RecyclerView              recyclerView;
    private                       HotListAdater             mHotListAdater;
    private                       HotListContract.Presenter mPresenter;
    private int number  = 0;
    public final static int size  = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("本周热搜榜").showBackButton(R.drawable.back, new View.OnClickListener() {
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
        new HotListPresenter(this);
        mHotListAdater = new HotListAdater(this,new ArrayList<BookBean>(), R.layout.hot_list_item_layout);
        recyclerView.setAdapter(mHotListAdater);
        mPresenter.getHotData(number,size);
        mHotListAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                BookInfoActivity.gotoBookInfoActivity(v.getContext(),Integer.parseInt(mHotListAdater.getList().get(position).getBookId()));
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                number = 0;
                mPresenter.getHotData(number,size);
                recyclerView.reLoadFinish();
            }
        });
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                number = number + 1;
                mPresenter.getHotData(number,size);
            }
        });
    }

    @Override
    public void showData(List<BookBean> datas) {
            getBaseLoadingView().hideLoading();
            if(number == 0){
                refreshlayout.setRefreshing(false);
                recyclerView.notifyChangeData(datas,mHotListAdater);
            }else {
                recyclerView.changeData(datas,mHotListAdater);
            }
    }

    @Override
    public void setPresenter(HotListContract.Presenter presenter) {
        mPresenter = presenter;
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
        if(number == 0){
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

    public static void goToHotListActivity(Activity activity){
        Intent intent = new Intent(activity,HotListActivity.class);
        activity.startActivity(intent);
    }
}
