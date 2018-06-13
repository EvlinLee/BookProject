package com.zhangwan.app.ui.recommend;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.utils.ACache;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.BookTypeListAdater;
import com.zhangwan.app.adapter.RecommendAdapter;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;
import com.zhangwan.app.bean.RecommendBannerBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.bean.cache.CacheRecommendBean;
import com.zhangwan.app.presenter.RecommendContract;
import com.zhangwan.app.presenter.RecommendPresenter;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.recyclerview.wrapper.LoadMoreWrapper;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.SearchActivity;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity;

import com.zhangwan.app.ui.bookstore.hot.HotListActivity;
import com.zhangwan.app.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 推荐
 * Created by Administrator on 2018/3/22 0022.
 */

public class RecommendFragment extends BaseTitleFragment implements RecommendContract.View, LoadMoreWrapper.OnLoadMoreListener, View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_recommend_list)
    RecyclerView rvRecommendList;
    @BindView(R.id.sf_recommend)
    SwipeRefreshLayout sfRecommend;

    private RecommendContract.Presenter mPresenter;

    private View headView;// 头部
    private ViewHolder viewHolder;

    private android.support.v7.widget.RecyclerView mRecyclerView;
    private BookTypeListAdater bookTypeListAdater;

    //缓存相关数据
    private static final String CACHE_RECOMMEND_DATA = "cache_recommend_data";

    private CacheRecommendBean bean;
    private CacheRecommendBean cacheRecommendBean = new CacheRecommendBean();

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main_recommend, container, false);
        return view;
    }

    @Override
    public void initData() {
        bean = (CacheRecommendBean) ACache.get(getActivity()).getAsObject(CACHE_RECOMMEND_DATA);
        if (bean != null) {
            getCacheData();
        }
        getBaseHeadView().showTitle("推荐").showHeadRightImageButton(R.drawable.search, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(getActivity())) {
                    startActivity(SearchActivity.class);
                    getActivity().overridePendingTransition(0, 0);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
            }
        });
        sfRecommend.setColorSchemeColors(getResources().getColor(R.color.main));

        //为SwipeRefreshLayout设置监听事件
        sfRecommend.setOnRefreshListener(this);

        //  recyclerview
        headView = getLayoutInflater().inflate(R.layout.fragment_recommend_head, null);
        viewHolder = new ViewHolder(headView);
        rvRecommendList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecommendList.addHeadView(headView);
        rvRecommendList.setOnLoadMoreListener(this);

        mRecyclerView = headView.findViewById(R.id.book_type_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        bookTypeListAdater = new BookTypeListAdater(getContext(), new ArrayList<BookTypeBean>(), R.layout.book_type_list_layout);
        mRecyclerView.setAdapter(bookTypeListAdater);
        headView.findViewById(R.id.free_layout).setOnClickListener(this);
        headView.findViewById(R.id.mouth_layout).setOnClickListener(this);
        initBanner();
        //  获取推荐数据
        new RecommendPresenter(this, "1");
        mPresenter.getData();
        mPresenter.getBanner("4");
        mPresenter.getBookType();
    }

    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {
        mPresenter = presenter;
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
    public void showData(List<RecommendBean> datas) {
        RecommendAdapter adapter = new RecommendAdapter(getActivity(), R.layout.item_recommend, datas);
        rvRecommendList.setAdapter(adapter);
        sfRecommend.setRefreshing(false);

        cacheRecommendBean.setRecommendBeanList(datas);
        Observable.just(cacheRecommendBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CacheRecommendBean>() {
                    @Override
                    public void call(CacheRecommendBean listRecommendBean1) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_RECOMMEND_DATA, listRecommendBean1);
                    }
                });
    }

    //书籍分类
    @Override
    public void showBookType(final List<BookTypeBean> datas) {
        bookTypeListAdater.notifyChangeData(datas);
        bookTypeListAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(android.support.v7.widget.RecyclerView parentView, View v,
                                    int position) {
                BookTypeBean bean = datas.get(position);
                if (NetworkUtil.isConnected(getActivity())) {
                    FilterBookListActivity.gotoFilterBookListActivity(v.getContext(), String.valueOf(bean.getId()), bean.getName());
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
            }
        });

        cacheRecommendBean.setBookTypeBeanList(datas);
        Observable.just(cacheRecommendBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CacheRecommendBean>() {
                    @Override
                    public void call(CacheRecommendBean listBookTypeBean1) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_RECOMMEND_DATA, listBookTypeBean1);
                    }
                });
    }

    @Override
    public void showFiltrResult(List<BookBean> datas) {
    }

    // 轮播
    @Override
    public void showBanner(final List<RecommendBannerBean> datas) {
        List<String> url = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            url.add(datas.get(i).getPicUrl());
        }
        //设置图片集合
        viewHolder.recommendBanner.setImages(url);
        //banner点击事件
        viewHolder.recommendBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (NetworkUtil.isConnected(getActivity())) {
                    BookInfoActivity.gotoBookInfoActivity(RecommendFragment.this.getContext(), datas.get(position).getBookId());
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        viewHolder.recommendBanner.start();

        cacheRecommendBean.setBannerBeanList(datas);
        Observable.just(cacheRecommendBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CacheRecommendBean>() {
                    @Override
                    public void call(CacheRecommendBean listBookTypeBean1) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_RECOMMEND_DATA, listBookTypeBean1);
                    }
                });
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(getActivity(), msg);
    }

    @Override
    public void showNetError() {
        ToastUtil.showShort(getActivity(), getResources().getString(R.string.test_net_error));
        getCacheData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onClick(View view) {
        if (NetworkUtil.isConnected(getActivity())) {
            switch (view.getId()) {
                //免费专区
                case R.id.free_layout:
                    BookStoreFreeActivity.goToBookStoreFreeActivity(getActivity());
                    break;
                //包月专区(现在改为热门专区)
                case R.id.mouth_layout:
                    HotListActivity.goToHotListActivity(getActivity());
                    break;
            }
        } else {
            ToastUtil.showShort(this.getContext(), getString(R.string.empty_net_error));
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getData();
    }

    //  头部
    static class ViewHolder {
        @BindView(R.id.recommend_banner)
        Banner recommendBanner;
        @BindView(R.id.tv_recommend_area1)
        TextView tvRecommendArea1;
        @BindView(R.id.tv_recommend_area2)
        TextView tvRecommendArea2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //初始化banner样式
    private void initBanner() {
        //设置banner样式
        viewHolder.recommendBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        viewHolder.recommendBanner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        viewHolder.recommendBanner.setBannerAnimation(Transformer.Default);
        //设置轮播时间
        viewHolder.recommendBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        viewHolder.recommendBanner.setIndicatorGravity(BannerConfig.RIGHT);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        viewHolder.recommendBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        viewHolder.recommendBanner.stopAutoPlay();
    }

    //  加载缓存
    private void getCacheData() {
        Observable.just(CACHE_RECOMMEND_DATA).subscribeOn(Schedulers.io()).map(new Func1<String, CacheRecommendBean>() {
            @Override
            public CacheRecommendBean call(String key) {
                return bean;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CacheRecommendBean>() {
            @Override
            public void call(CacheRecommendBean bean) {
                if (bean != null) {
                    showData(bean.getRecommendBeanList());
                    showBookType(bean.getBookTypeBeanList());
                    showBanner(bean.getBannerBeanList());
                } else {
                    getBaseEmptyView().showNetWorkViewReload(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                            //记得隐藏
                            getBaseEmptyView().hideEmptyView();
                        }
                    });
                }
            }
        });
    }
}
