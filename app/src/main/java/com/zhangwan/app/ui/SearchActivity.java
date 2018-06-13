package com.zhangwan.app.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.HotSearchAdapter;
import com.zhangwan.app.adapter.SearchAdapter;
import com.zhangwan.app.adapter.SearchHistoryAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.presenter.SearchContract;
import com.zhangwan.app.presenter.SearchPresenter;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.recyclerview.wrapper.LoadMoreWrapper;
import com.zhangwan.app.utils.SystemTools;
import com.zhangwan.app.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 小说搜索
 * Created by Administrator on 2018/3/22 0022.
 */

public class SearchActivity extends BaseTitleActivity implements SearchContract.View, TextWatcher {

    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.ll_search_hot)
    LinearLayout llSearchHot;
    @BindView(R.id.tv_search_key)
    TextView tvSearchKey;
    @BindView(R.id.tv_search_num)
    TextView tvSearchNum;
    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.iv_delete_history)
    ImageView ivDeleteHistory;
    @BindView(R.id.rv_search_history)
    RecyclerView mRecyclerViewSearchHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;

    private int type = 1;//右边按钮点击事件
    private int start = 0;

    private SearchAdapter adapter;

    private SearchContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    @Override
    public void initData() {
        new SearchPresenter(this);

        presenter.getHotData();
        etSearch.addTextChangedListener(this);

        rvSearch.setLayoutManager(new GridLayoutManager(this, 2));

        rvSearchResult.setLoadMoreView(R.layout.model_footview_loadmore);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this, new ArrayList<SearchBean.ListBean>());
        rvSearchResult.setAdapter(adapter);
        String searchKey = SpUtil.getSearchKey(this);
        if ("".equals(searchKey)) {
            llSearchHistory.setVisibility(View.GONE);
        }else {
            llSearchHistory.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void initListener() {
        rvSearchResult.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                start = start + 1;
                presenter.getSearchResult(start, etSearch.getText().toString());
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    SystemTools.hideKeyBoard(SearchActivity.this);
                    start = 0;
                    presenter.getSearchResult(start, etSearch.getText().toString());
                    //把搜索关键字保存起来
                    String searchKey1 = SpUtil.getSearchKey(MyApplication.getInstance());
                    if (!"".equals(etSearch.getText().toString())) {
                        String searchKey = SpUtil.getSearchKey(SearchActivity.this);
                        String[] split = searchKey.split(",");
                        boolean isHas = false;
                        for (String s : split) {
                            if (etSearch.getText().toString().equals(s)) {
                                isHas = true;

                            }

                        }
                        if (!isHas) {
                            searchKey1 = searchKey1 + "," + etSearch.getText().toString();
                            SpUtil.setSearchKey(MyApplication.getInstance(), searchKey1);
                        }

                    }

                    return true;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.iv_delete_history, R.id.tv_search_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_history:
                SpUtil.setSearchKey(this, "");
                llSearchHistory.setVisibility(View.GONE);
                break;
            case R.id.tv_search_cancel:
                if (type == 1) {
                    finish();
                } else {
                    start = 0;
                    presenter.getSearchResult(start, etSearch.getText().toString());
                    //把搜索关键字保存起来
                    String searchKey1 = SpUtil.getSearchKey(MyApplication.getInstance());
                    if (!"".equals(etSearch.getText().toString())) {
                        String searchKey = SpUtil.getSearchKey(SearchActivity.this);
                        String[] split = searchKey.split(",");
                        boolean isHas = false;
                        for (String s : split) {
                            if (etSearch.getText().toString().equals(s)) {
                                isHas = true;

                            }

                        }
                        if (!isHas) {
                            searchKey1 = searchKey1 + "," + etSearch.getText().toString();
                            SpUtil.setSearchKey(MyApplication.getInstance(), searchKey1);
                        }

                    }
                }
                break;
        }
    }

    @Override
    public void showSearchResult(SearchBean data) {
        if (start == 0) {
            WindowUtil.closeInputMethod(this);
            llSearchHot.setVisibility(View.GONE);
            llSearch.setVisibility(View.VISIBLE);
            tvSearchKey.setText(etSearch.getText().toString());
            tvSearchNum.setText("" + data.getTotal());
            rvSearchResult.notifyChangeData(data.getList(), adapter);

        } else {
            rvSearchResult.changeData(data.getList(), adapter);
            if (adapter.getList().size() >= data.getTotal()) {
                rvSearchResult.loadFinish();
            }
        }
    }

    @Override
    public void showHoData(final List<HotBean> list) {
        HotSearchAdapter adapter = new HotSearchAdapter(this, list);
        rvSearch.setAdapter(adapter);
        adapter.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                BookInfoActivity.gotoBookInfoActivity(SearchActivity.this, list.get(position).getBookId());
            }
        });
    }

    @Override
    public void showSaveSearchData(String[] split) {
        if (split.length > 0) {
            llSearchHistory.setVisibility(View.VISIBLE);
            mRecyclerViewSearchHistory.setLayoutManager(new LinearLayoutManager(this));
            final List<String> list = new ArrayList<>();
            for (String s : split) {
                if (!"".equals(s))
                    list.add(s);
            }
            SearchHistoryAdapter adapter = new SearchHistoryAdapter(this, list, R.layout.item_search_history);
            mRecyclerViewSearchHistory.setAdapter(adapter);
            adapter.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    start = 0;
                    presenter.getSearchResult(start, list.get(position));
                    etSearch.setText(list.get(position));
                }
            });
        } else {
            llSearchHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoad() {
        getBaseLoadingView().showLoading();
    }

    @Override
    public void showLoadFinish() {
        getBaseLoadingView().hideLoading();
    }

    @Override
    public void showEmpty() {
        ToastUtil.showShort(this, getString(R.string.text_no_data));
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void showNetError() {
        ToastUtil.showShort(this, getResources().getString(R.string.empty_net_error));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    // 输入搜索关键字的时候
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 0) {
            tvSearchCancel.setText("取消");
            visible(llSearchHot);
            gone(llSearch);
            type = 1;
        } else {
            tvSearchCancel.setText("搜索");
            type = 2;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    private void searchKeyBord() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });
    }


}
