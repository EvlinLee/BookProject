package com.zhangwan.app.ui.recommend;

import android.content.Context;
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
import com.zhangwan.app.adapter.NewListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.presenter.RecommendContract;
import com.zhangwan.app.presenter.RecommendPresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity.size;

/**
 * Created by zzg on 2018/3/23.
 */
public class FilterBookListActivity extends BaseTitleActivity implements RecommendContract.View {

    @BindView(R.id.refreshlayout) SwipeRefreshLayout        refreshlayout;
    @BindView(R.id.recyclerView)  RecyclerView              recyclerView;
    private                       NewListAdater             mNewListAdater;
    private                       RecommendContract.Presenter mPresenter;
    private int numbers  = 1;
    public String typeId;

    public static  void  gotoFilterBookListActivity(Context context, String typeId, String name){
        Intent intent = new Intent(context,FilterBookListActivity.class);
        intent.putExtra("typeId",typeId);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
    }

    @Override
    public void initView() {
        super.initView();
        typeId = getIntent().getStringExtra("typeId");
        String name = getIntent().getStringExtra("name");
        getBaseHeadView().showTitle(name).showBackButton(R.drawable.back, new View.OnClickListener() {
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
        new RecommendPresenter(this,null);
        getBaseLoadingView().showLoading();
        mNewListAdater = new NewListAdater(this,new ArrayList<BookBean>(),R.layout.new_list_item_layout);
        recyclerView.setAdapter(mNewListAdater);
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
                NovelReadActivity.startReadActivity(bean.getBookId(),bean.getReadId(),bean.getTotal(),bean.getBookPic(),bean.getBookName(),false,FilterBookListActivity.this);
            }
        });
        mPresenter.getFiltrResult(typeId,null,null,numbers,size);
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                numbers = 1;
                recyclerView.reLoadFinish();
                mPresenter.getFiltrResult(typeId,null,null,numbers,size);
            }
        });
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                numbers = numbers + 1;
                mPresenter.getFiltrResult(typeId,null,null,numbers,size);
            }
        });
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
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
        if(numbers == 1){
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
    public void showData(List<RecommendBean> datas) {}

    @Override
    public void showBanner(List<RecommendBannerBean> datas) {

    }

    @Override
    public void showBookType(List<BookTypeBean> datas) {}

    @Override
    public void showFiltrResult(List<BookBean> datas) {
        getBaseLoadingView().hideLoading();
        refreshlayout.setRefreshing(false);
        if(numbers == 1){
            recyclerView.notifyChangeData(datas,mNewListAdater);
        }else {
            recyclerView.changeData(datas,mNewListAdater);
        }
    }
}
