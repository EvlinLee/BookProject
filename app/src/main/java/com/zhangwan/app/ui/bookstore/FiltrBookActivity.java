package com.zhangwan.app.ui.bookstore;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.gxtc.commlibrary.recyclerview.RecyclerView;
import com.gxtc.commlibrary.recyclerview.wrapper.LoadMoreWrapper;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.BookTypeAdapter;
import com.zhangwan.app.adapter.NewListAdater;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.presenter.FiltrBookContract;
import com.zhangwan.app.presenter.FiltrBookPresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity.size;

/**
 * 筛选小说
 * Created by Administrator on 2018/3/23 0023.
 */

public class FiltrBookActivity extends BaseTitleActivity implements FiltrBookContract.View, View.OnClickListener {
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    android.support.v7.widget.RecyclerView rvFiltrBooktype;
    TextView tvFiltrShow;
    TextView tvFiltrStateAll;
    TextView tvFiltrStateSerial;
    TextView tvFiltrStateFinish;
    TextView tvFiltrQualityAll;
    TextView tvFiltrQualityFree;
    TextView tvFiltrQualityPay;
    TextView tvFiltrQualityMonthly;

    int numbers = 1;
    private FiltrBookContract.Presenter mPresenter;

    private List<BookTypeBean> beanList = new ArrayList<>();
    private NewListAdater mNewListAdater;
    public String typeId;
    public String is_finish;
    public String attr;
    private BookTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore_filtr);
    }

    @Override
    public void initView() {
        super.initView();
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refreshlayout.setColorSchemeResources(R.color.refresh_color1,
                R.color.refresh_color2,
                R.color.refresh_color3);
        recyclerView.setLoadMoreView(R.layout.model_footview_loadmore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View view = getLayoutInflater().inflate(R.layout.filter_header_layout, null);
        rvFiltrBooktype = view.findViewById(R.id.rv_filtr_booktype);
        tvFiltrShow = view.findViewById(R.id.tv_filtr_show);
        tvFiltrStateAll = view.findViewById(R.id.tv_filtr_state_all);
        tvFiltrStateSerial = view.findViewById(R.id.tv_filtr_state_serial);
        tvFiltrStateFinish = view.findViewById(R.id.tv_filtr_state_finish);
        tvFiltrQualityAll = view.findViewById(R.id.tv_filtr_quality_all);
        tvFiltrQualityFree = view.findViewById(R.id.tv_filtr_quality_free);
        tvFiltrQualityPay = view.findViewById(R.id.tv_filtr_quality_pay);
        tvFiltrQualityMonthly = view.findViewById(R.id.tv_filtr_quality_monthly);
        recyclerView.addHeadView(view);
        mNewListAdater = new NewListAdater(this, new ArrayList<BookBean>(), R.layout.new_list_item_layout);
        recyclerView.setAdapter(mNewListAdater);
        rvFiltrBooktype.setLayoutManager(new GridLayoutManager(this, 4));
        rvFiltrBooktype.setNestedScrollingEnabled(false);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvFiltrShow.setOnClickListener(this);
        tvFiltrStateAll.setOnClickListener(this);
        tvFiltrStateSerial.setOnClickListener(this);
        tvFiltrStateFinish.setOnClickListener(this);
        tvFiltrQualityAll.setOnClickListener(this);
        tvFiltrQualityFree.setOnClickListener(this);
        tvFiltrQualityPay.setOnClickListener(this);
        tvFiltrQualityMonthly.setOnClickListener(this);

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                numbers = 1;
                recyclerView.reLoadFinish();
                mPresenter.getFiltrResult(typeId, is_finish, attr, numbers, size);
            }
        });

        recyclerView.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                numbers = numbers + 1;
                mPresenter.getFiltrResult(typeId, is_finish, attr, numbers, size);
            }
        });

        tvFiltrShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.isExtends) {
                    tvFiltrShow.setText("展开");
                    tvFiltrShow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filtr_right, 0);
                    adapter.isExtends = false;
                } else {
                    tvFiltrShow.setText("收起");
                    tvFiltrShow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filtr_right_up, 0);
                    adapter.isExtends = true;
                }
                adapter.notifyDataSetChanged();
            }
        });

        mNewListAdater.setOnReItemOnClickListener(new com.gxtc.commlibrary.base.BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BookBean item = mNewListAdater.getList().get(position);
                BookInfoActivity.gotoBookInfoActivity(v.getContext(), Integer.parseInt(item.getBookId()));
            }
        });

        mNewListAdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookBean bookBean = (BookBean) view.getTag();
                NovelReadActivity.startReadActivity(bookBean.getBookId(), bookBean.getReadId(), bookBean.getTotal(), bookBean.getBookPic(), bookBean.getBookName(),false, FiltrBookActivity.this);
            }
        });

    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("分类");
        new FiltrBookPresenter(this);
        mPresenter.getBookType();
        mPresenter.getFiltrResult(typeId, is_finish, attr, numbers, size);
        getBaseLoadingView().showLoading();
        hideContentLayout();
    }


    @Override
    public void onClick(View view) {
        numbers = 1;
        switch (view.getId()) {
            case R.id.tv_filtr_state_all:
                is_finish = null;
                reSetStatus();
                tvFiltrStateAll.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrStateAll.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_state_serial:
                is_finish = "0";
                reSetStatus();
                tvFiltrStateSerial.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrStateSerial.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_state_finish:
                is_finish = "1";
                reSetStatus();
                tvFiltrStateFinish.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrStateFinish.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_quality_all:
                attr = null;
                reAttrStatus();
                tvFiltrQualityAll.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrQualityAll.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_quality_free:
                attr = "0";
                reAttrStatus();
                tvFiltrQualityFree.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrQualityFree.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_quality_pay:
                attr = "2";
                reAttrStatus();
                tvFiltrQualityPay.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrQualityPay.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_filtr_quality_monthly:
                attr = "1";
                reAttrStatus();
                tvFiltrQualityMonthly.setBackgroundResource(R.drawable.filter_textview_bg);
                tvFiltrQualityMonthly.setTextColor(getResources().getColor(R.color.white));
                break;
        }
        recyclerView.reLoadFinish();
        mPresenter.getFiltrResult(typeId, is_finish, attr, numbers, size);
    }

    public void reSetStatus() {
        tvFiltrStateAll.setBackgroundResource(0);
        tvFiltrStateAll.setTextColor(getResources().getColor(R.color.text_color_black_1));

        tvFiltrStateSerial.setBackgroundResource(0);
        tvFiltrStateSerial.setTextColor(getResources().getColor(R.color.text_color_black_1));

        tvFiltrStateFinish.setBackgroundResource(0);
        tvFiltrStateFinish.setTextColor(getResources().getColor(R.color.text_color_black_1));
    }

    public void reAttrStatus() {
        tvFiltrQualityAll.setBackgroundResource(0);
        tvFiltrQualityAll.setTextColor(getResources().getColor(R.color.text_color_black_1));

        tvFiltrQualityFree.setBackgroundResource(0);
        tvFiltrQualityFree.setTextColor(getResources().getColor(R.color.text_color_black_1));

        tvFiltrQualityPay.setBackgroundResource(0);
        tvFiltrQualityPay.setTextColor(getResources().getColor(R.color.text_color_black_1));

        tvFiltrQualityMonthly.setBackgroundResource(0);
        tvFiltrQualityMonthly.setTextColor(getResources().getColor(R.color.text_color_black_1));
    }

    @Override
    public void showBookType(final List<BookTypeBean> data) {
        getBaseLoadingView().hideLoading();
        showContentLayout();
        final BookTypeBean bean = new BookTypeBean();
        bean.setName("全部");
        beanList.add(bean);
        beanList.addAll(data);
        adapter = new BookTypeAdapter(this, beanList);
        rvFiltrBooktype.setAdapter(adapter);
        adapter.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                numbers = 1;
                adapter.choosePosition = position;
                recyclerView.reLoadFinish();
                if (position == 0) {
                    typeId = null;
                } else {
                    typeId = String.valueOf(adapter.getList().get(position).getId());
                }
                mPresenter.getFiltrResult(typeId, is_finish, attr, numbers, size);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showResult(List<BookBean> data) {
        getBaseLoadingView().hideLoading();
        refreshlayout.setRefreshing(false);
        getBaseEmptyView().hideEmptyView();
        if (numbers == 1) {
            recyclerView.notifyChangeData(data, mNewListAdater);
        } else {
            recyclerView.changeData(data, mNewListAdater);
        }
    }

    @Override
    public void setPresenter(FiltrBookContract.Presenter presenter) {
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
        if (numbers == 1) {
            refreshlayout.setRefreshing(false);
            ToastUtil.showShort(this, "暂无此分类内容！");
        } else {
            recyclerView.loadFinish();
        }
    }

    @Override
    public void showReLoad() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(MyApplication.getInstance(),msg);
    }

    @Override
    public void showNetError() {

    }

}
