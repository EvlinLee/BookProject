package com.zhangwan.app.ui.bookstore.free;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.HotListAdater;
import com.zhangwan.app.adapter.LimitListAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
  Created by zzg on 2018/3/23.
 */
public class BookStoreFreeActivity extends BaseTitleActivity implements BookStoreFreeContract.View{
    @BindView(R.id.rv_free_limit) RecyclerView                    freeRecyclerView;
    @BindView(R.id.rv_boy_list)   RecyclerView                    boyRecyclerView;
    @BindView(R.id.rv_girl_list)  RecyclerView                    girlRecyclerView;
    public                        LimitListAdater                 freeAdater;
    public                        HotListAdater                   boyAdater;
    public                        HotListAdater                   girlAdater;
    public                        BookStoreFreeContract.Presenter mPresenter;
    private int number  = 0;
    public final static int size  = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store_free);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("免费小说").showBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        freeRecyclerView.setNestedScrollingEnabled(false);
        boyRecyclerView.setNestedScrollingEnabled(false);
        girlRecyclerView.setNestedScrollingEnabled(false);
        freeAdater = new LimitListAdater(this,new ArrayList<BookBean>(), R.layout.activity_book_store_free_item);
        boyAdater = new HotListAdater(this,new ArrayList<BookBean>(), R.layout.hot_list_item_layout);
        girlAdater = new HotListAdater(this,new ArrayList<BookBean>(), R.layout.hot_list_item_layout);

        freeRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        boyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        girlRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        freeRecyclerView.setAdapter(freeAdater);
        boyRecyclerView.setAdapter(boyAdater);
        girlRecyclerView.setAdapter(girlAdater);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initData() {
        super.initData();
        new BookStoreFreePresenter(this);
        mPresenter.getLimitData(number,size);
        mPresenter.getBoyData(number,size);
        mPresenter.getGirlData(number,size);
        freeAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                BookInfoActivity.gotoBookInfoActivity(v.getContext(),Integer.parseInt(freeAdater.getList().get(position).getBookId()));
            }
        });
    }

    @Override
    public void showLimitData(List<BookBean> datas) {
        freeAdater.notifyChangeData(datas);
    }

    @Override
    public void showBoyData(List<BookBean> datas) {
        boyAdater.notifyChangeData(datas);
    }

    @Override
    public void showGirlData(List<BookBean> datas) {
        girlAdater.notifyChangeData(datas);
    }

    @Override
    public void setPresenter(BookStoreFreeContract.Presenter presenter) {
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
        getBaseEmptyView().showEmptyContent();
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
           ToastUtil.showShort(this,msg);
    }

    @Override
    public void showNetError() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    public static void goToBookStoreFreeActivity(Activity activity){
        Intent intent = new Intent(activity,BookStoreFreeActivity.class);
        activity.startActivity(intent);
    }
}
