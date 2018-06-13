package com.zhangwan.app.ui.mine.ticket;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.TicketAdater;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity.size;

/**
 * Created by zzg on 2018/3/26.
 */
public class TicketActivity extends BaseTitleActivity implements TicketListContract.View {
    @BindView(R.id.sf_message)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.rv_message)
    RecyclerView recyclerView;
    TicketAdater mTicketAdater;
    TicketListContract.Presenter mPresenter;
    int numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mesage);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showTitle("优惠券").showBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshlayout.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3);
        recyclerView.setLoadMoreView(R.layout.model_footview_loadmore);
        recyclerView.addHeadView(getLayoutInflater().inflate(R.layout.head_line, null, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTicketAdater = new TicketAdater(this, new ArrayList<String>(), R.layout.ticket_item_layout);
        recyclerView.setAdapter(mTicketAdater);
    }

    @Override
    public void initListener() {
        super.initListener();
        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                numbers = numbers + 1;
                mPresenter.getTicketListData("", numbers, size);
            }
        });

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                numbers = 0;
                recyclerView.reLoadFinish();
                mPresenter.getTicketListData("", numbers, size);
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        new TicketListPresenter(this);
        mPresenter.getTicketListData("", numbers, size);
    }

    @Override
    public void showData(List<String> datas) {
        if (numbers == 0) {
            refreshlayout.setRefreshing(false);
            recyclerView.notifyChangeData(datas, mTicketAdater);
        } else {
            recyclerView.changeData(datas, mTicketAdater);
        }
    }

    @Override
    public void setPresenter(TicketListContract.Presenter presenter) {
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
        if (numbers == 0) {
            refreshlayout.setRefreshing(false);
            getBaseEmptyView().showEmptyContent();
        } else {
            recyclerView.loadFinishNotView();
        }
    }

    @Override
    public void showReLoad() {}

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void showNetError() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
