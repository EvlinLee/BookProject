package com.zhangwan.app.ui.bookstore.news;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.NewListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
/**
 * Created by zzg on 2018/3/23.
 */
public class NewListActivity extends BaseTitleActivity implements NewListContract.View {

    @BindView(R.id.refreshlayout) SwipeRefreshLayout        refreshlayout;
    @BindView(R.id.recyclerView)  RecyclerView              recyclerView;
    private                       NewListAdater             mNewListAdater;
    private                       NewListContract.Presenter mPresenter;
    private                       Map<String,String>        map;
    private int numbers  = 0;
    private int type  = 2; //0：获取首页轮播，1：获取主编推荐，2：获取新书推荐
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
    }

    @Override
    public void initView() {
        super.initView();
        map = new HashMap<>();
        getBaseHeadView().showTitle("新书推荐").showBackButton(R.drawable.back, new View.OnClickListener() {
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
        new NewListPresenter(this);
        getBaseLoadingView().showLoading();
        mNewListAdater = new NewListAdater(this,new ArrayList<BookBean>(),R.layout.new_list_item_layout);
        recyclerView.setAdapter(mNewListAdater);
        map.put("number",numbers+"");
        map.put("size", HotListActivity.size+"");
        map.put("type", type+"");
        mPresenter.getNesData(map);
        mNewListAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                BookInfoActivity.gotoBookInfoActivity(v.getContext(),Integer.parseInt(mNewListAdater.getList().get(position).getBookId()));
            }
        });
        mNewListAdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookBean bean = (BookBean) view.getTag();
                NovelReadActivity.startReadActivity(bean.getBookId(),bean.getReadId(),bean.getTotal(),bean.getBookPic(),bean.getBookName(),false,NewListActivity.this);
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                numbers = 0;
                map.clear();
                map.put("number",numbers+"");
                map.put("size", HotListActivity.size+"");
                map.put("type", type+"");
                mPresenter.getNesData(map);
                recyclerView.reLoadFinish();
            }
        });
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                numbers = numbers + 1;
                map.clear();
                map.put("number",numbers+"");
                map.put("size", HotListActivity.size+"");
                map.put("type", type+"");
                mPresenter.getNesData(map);
            }
        });
    }

    @Override
    public void showData(List<BookBean> datas) {
            getBaseLoadingView().hideLoading();
            refreshlayout.setRefreshing(false);
            if(numbers == 0){
                recyclerView.notifyChangeData(datas,mNewListAdater);
            }else {
                recyclerView.changeData(datas,mNewListAdater);
            }
    }

    @Override
    public void setPresenter(NewListContract.Presenter presenter) {
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
        if(numbers == 0){
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

    public static void goToNewListActivity(Activity activity){
        Intent intent = new Intent(activity,NewListActivity.class);
        activity.startActivity(intent);
    }
}
