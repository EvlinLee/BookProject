package com.zhangwan.app.ui.bookstore;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.ACache;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.BaseBookAdapter;
import com.zhangwan.app.adapter.BsFreeAdapter;
import com.zhangwan.app.adapter.BsNewAdapter;
import com.zhangwan.app.adapter.GridViewAdapter;
import com.zhangwan.app.adapter.LikeAdapter;
import com.zhangwan.app.adapter.StoreRecommendAdapter;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.BannerBean;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.ListBookStoreBean;
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.presenter.BookStoreContract;
import com.zhangwan.app.presenter.BookStorePresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.SearchActivity;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListActivity;
import com.zhangwan.app.ui.bookstore.like.GessLikeListActivity;
import com.zhangwan.app.ui.bookstore.news.NewListActivity;
import com.zhangwan.app.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

;

/**
 * 书城
 * Created by Administrator on 2018/3/22 0022.
 */

public class BookStoreFragment extends BaseTitleFragment implements BookStoreContract.View, SwipeRefreshLayout.OnRefreshListener, NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.banner_bookstore)
    Banner banner;
    @BindView(R.id.tv_bookstore_classify)
    TextView tvBookstoreClassify;
    @BindView(R.id.tv_bookstore_hot)
    TextView tvBookstoreHot;
    @BindView(R.id.tv_bookstore_new)
    TextView tvBookstoreNew;
    @BindView(R.id.tv_bookstore_free)
    TextView tvBookstoreFree;
    @BindView(R.id.ll_bookstore_dot)
    LinearLayout llBookstoreDot;
    @BindView(R.id.ll_bookstore_recommend)
    RelativeLayout llBookstoreRecommend;
    @BindView(R.id.vp_bookstore_recommend)
    ViewPager vpBookstoreRecommend;
    @BindView(R.id.ll_bookstore_hot)
    LinearLayout llBookstoreHot;
    @BindView(R.id.rv_bookstore_hot)
    RecyclerView rvBookstoreHot;
    @BindView(R.id.ll_bookstore_short)
    LinearLayout llBookstoreShort;
    @BindView(R.id.ll_bookstore_new)
    LinearLayout llBookstoreNew;
    @BindView(R.id.ll_bookstore_free)
    LinearLayout llBookstoreFree;
    @BindView(R.id.ll_bookstore_like)
    LinearLayout llBookstoreLike;
    @BindView(R.id.rv_bookstore_like)
    RecyclerView rvBookstoreLike;
    @BindView(R.id.rv_bookstore_new)
    RecyclerView rvBookstoreNew;
    @BindView(R.id.ll_bookstore_sell)
    LinearLayout llBookstoreSell;
    @BindView(R.id.ns_bookstore)
    NestedScrollView nsBookstore;
    @BindView(R.id.iv_bookstore_top)
    ImageView ivBookstoreTop;
    @BindView(R.id.rv_bookstore_free)
    RecyclerView rvBookstoreFree;
    @BindView(R.id.tv_bookstore_change)
    TextView tvChange;
    @BindView(R.id.recommend_more)
    TextView recommendMore;
    @BindView(R.id.recommend_line)
    View recommendLine;
    @BindView(R.id.sf_bookstore)
    SwipeRefreshLayout sfBookStore;

    //每页展示的主题个数
    private final int pageSize = 3;
    //当前页索引
    private int currentIndex;

    private BookStoreContract.Presenter mPresenter;

    //缓存书城
    private static String CACHE_BOOK_STORE = "cache_book_store";
    ListBookStoreBean listBookStoreBean = new ListBookStoreBean();

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bookstore_content, container, false);
        return view;
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("书城").showHeadRightImageButton(R.drawable.search, new View.OnClickListener() {
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

        sfBookStore.setColorSchemeColors(getResources().getColor(R.color.main));
        //为SwipeRefreshLayout设置监听事件
        sfBookStore.setOnRefreshListener(this);
        rvBookstoreLike.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvBookstoreNew.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvBookstoreFree.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvBookstoreHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookstoreHot.setNestedScrollingEnabled(false);
        rvBookstoreNew.setNestedScrollingEnabled(false);
        rvBookstoreLike.setNestedScrollingEnabled(false);
        rvBookstoreFree.setNestedScrollingEnabled(false);
        new BookStorePresenter(this);
        mPresenter.getBannerData("0");
        mPresenter.getRecommend("1", 1);//主编推荐
        mPresenter.getRecommend("2", 2);//新书推荐
        mPresenter.getHotData(1, 3);
        mPresenter.getLikeBook(6, 1);
        mPresenter.getFreeBook(0, 3);
    }


    @OnClick({R.id.tv_bookstore_classify, R.id.tv_bookstore_hot, R.id.tv_bookstore_new, R.id.limit_more,
            R.id.tv_bookstore_free, R.id.ll_bookstore_dot, R.id.ll_bookstore_recommend, R.id.ll_bookstore_hot, R.id.hot_more, R.id.recommend_more,
            R.id.ll_bookstore_short, R.id.ll_bookstore_new, R.id.ll_bookstore_free, R.id.ll_bookstore_like, R.id.ll_bookstore_sell,
            R.id.tv_bookstore_change, R.id.iv_bookstore_top})
    public void onViewClicked(View view) {
        if (NetworkUtil.isConnected(getActivity())) {
            switch (view.getId()) {
                case R.id.tv_bookstore_classify:
                    startActivity(FiltrBookActivity.class);
                    break;
                case R.id.tv_bookstore_hot:
                    HotListActivity.goToHotListActivity(getActivity());
                    break;
                case R.id.tv_bookstore_new:
                    NewListActivity.goToNewListActivity(getActivity());
                    break;
                case R.id.tv_bookstore_free:
                    BookStoreFreeActivity.goToBookStoreFreeActivity(getActivity());
                    break;
                case R.id.ll_bookstore_dot:
                    break;
                case R.id.ll_bookstore_recommend:
                    break;
                //热门小说查看更多
                case R.id.hot_more:
                    HotListActivity.goToHotListActivity(getActivity());
                    break;
                //新书推荐查看更多
                case R.id.recommend_more:
                    NewListActivity.goToNewListActivity(getActivity());
                    break;
                //限时免费查看更多
                case R.id.limit_more:
                    BookStoreFreeActivity.goToBookStoreFreeActivity(getActivity());
                    break;
                case R.id.ll_bookstore_hot://热门小说
                    HotListActivity.goToHotListActivity(getActivity());
                    break;
                case R.id.ll_bookstore_short:
                    break;
                case R.id.ll_bookstore_new:
                    break;
                case R.id.ll_bookstore_free://免费
                    break;
                case R.id.ll_bookstore_like:
                    break;
                case R.id.ll_bookstore_sell:
                    break;
                case R.id.tv_bookstore_change:
                    startActivity(GessLikeListActivity.class);
//                    mPresenter.getLikeBook(6, 1);
                    break;
                case R.id.iv_bookstore_top:
                    nsBookstore.scrollTo(0, 0);
                    break;
            }
        } else {
            ToastUtil.showShort(this.getContext(), getString(R.string.empty_net_error));
        }

    }

    //  主编推荐
    @Override
    public void showRecommend(final List<RecommendBean> data, int tp) {
        currentIndex = 0;
        if (tp == 1) {
            llBookstoreNew.setVisibility(View.GONE);
            rvBookstoreNew.setVisibility(View.GONE);
            recommendMore.setVisibility(View.GONE);
            recommendLine.setVisibility(View.GONE);
            llBookstoreDot.removeAllViewsInLayout();
            //需要的页面数
            int pageCount = (int) Math.ceil(data.size() * 1.0 / pageSize);
            final List<View> viewList = new ArrayList<>();
            for (int i = 0; i < pageCount; i++) {
                GridView gridView = (GridView) getLayoutInflater().inflate(R.layout.layout_grid_view, vpBookstoreRecommend, false);
                gridView.setNumColumns(3);
                gridView.setAdapter(new GridViewAdapter(data, i, pageSize, getActivity()));
                viewList.add(gridView);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (NetworkUtil.isConnected(getActivity())) {
                            int pos = position + currentIndex * pageSize;
                            Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                            intent.putExtra("bookId", data.get(pos).getBookId());
                            startActivity(intent);
                        } else {
                            ToastUtil.showShort(BookStoreFragment.this.getContext(), getString(R.string.empty_net_error));
                        }
                    }
                });


            }
            vpBookstoreRecommend.setAdapter(new StoreRecommendAdapter(viewList));

            for (int i = 0; i < pageCount; i++) {
                llBookstoreDot.addView(getLayoutInflater().inflate(R.layout.view_dot, null));
            }
            //使第一个小圆点呈选中状态
            llBookstoreDot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
            vpBookstoreRecommend.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageSelected(int position) {
                    llBookstoreDot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                    llBookstoreDot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
                    currentIndex = position;
                }

                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                public void onPageScrollStateChanged(int arg0) {
                }
            });

            listBookStoreBean.setRecommendBeanList(data);
            Observable.just(listBookStoreBean)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<ListBookStoreBean>() {
                        @Override
                        public void call(ListBookStoreBean recommendBean) {
                            ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, recommendBean);
                        }
                    });
        } else {
            BsNewAdapter adapter = new BsNewAdapter(getActivity(), data);
            rvBookstoreNew.setAdapter(adapter);

            listBookStoreBean.setNewRecomendBeanList(data);
            Observable.just(listBookStoreBean)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<ListBookStoreBean>() {
                        @Override
                        public void call(ListBookStoreBean recommendBean) {
                            ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, recommendBean);
                        }
                    });
        }
        sfBookStore.setRefreshing(false);

    }

    //  热门小说
    @Override
    public void showHotData(List<HotBean> data) {
        BaseBookAdapter adapter = new BaseBookAdapter(getActivity(), R.layout.item_book_read, data, "hot");
        rvBookstoreHot.setAdapter(adapter);
        sfBookStore.setRefreshing(false);

        listBookStoreBean.setHotBeanList(data);
        Observable.just(listBookStoreBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ListBookStoreBean>() {
                    @Override
                    public void call(ListBookStoreBean listBookStoreBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, listBookStoreBean);
                    }
                });
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotBean bookBean = (HotBean) view.getTag();
                if (NetworkUtil.isConnected(getActivity())) {
                    NovelReadActivity.startReadActivity(String.valueOf(bookBean.getBookId()), bookBean.getChapter_id(), bookBean.getTotal(), bookBean.getBookPic(), bookBean.getBookName(), false, getActivity());
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
            }
        });

    }

    //  轮播
    @Override
    public void showBanner(final List<BannerBean> data) {
        List<String> url = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            url.add(data.get(i).getBookPic());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(url);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (NetworkUtil.isConnected(getActivity())) {

                    BookInfoActivity.gotoBookInfoActivity(BookStoreFragment.this.getContext(), data.get(position).getBookId());
                } else {
                    ToastUtil.showShort(BookStoreFragment.this.getContext(), getString(R.string.empty_net_error));
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        sfBookStore.setRefreshing(false);

        listBookStoreBean.setBannerBeanList(data);
        Observable.just(listBookStoreBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ListBookStoreBean>() {
                    @Override
                    public void call(ListBookStoreBean listBookStoreBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, listBookStoreBean);
                    }
                });

    }

    //  猜你喜欢
    @Override
    public void showLike(List<LikeBean> data) {
        LikeAdapter adapter = new LikeAdapter(getActivity(), data);
        rvBookstoreLike.setAdapter(adapter);
        sfBookStore.setRefreshing(false);

        listBookStoreBean.setLikeBeanList(data);
        Observable.just(listBookStoreBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ListBookStoreBean>() {
                    @Override
                    public void call(ListBookStoreBean listBookStoreBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, listBookStoreBean);
                    }
                });
    }

    //  限时免费
    @Override
    public void showFree(List<BookBean> data) {
        BsFreeAdapter adapter = new BsFreeAdapter(getActivity(), data);
        rvBookstoreFree.setAdapter(adapter);
        sfBookStore.setRefreshing(false);

        listBookStoreBean.setBookBeanList(data);
        Observable.just(listBookStoreBean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ListBookStoreBean>() {
                    @Override
                    public void call(ListBookStoreBean listBookStoreBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOK_STORE, listBookStoreBean);
                    }
                });
    }

    @Override
    public void setPresenter(BookStoreContract.Presenter presenter) {
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
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {

    }

    @Override
    public void showNetError() {

        Observable.just(CACHE_BOOK_STORE).subscribeOn(Schedulers.io()).map(
                new Func1<String, ListBookStoreBean>() {
                    @Override
                    public ListBookStoreBean call(String key) {
                        ListBookStoreBean bean = (ListBookStoreBean) ACache.get(
                                BookStoreFragment.this.getContext()).getAsObject(key);
                        return bean;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Action1<ListBookStoreBean>() {
                    @Override
                    public void call(ListBookStoreBean bean) {
                        if (bean != null) {
                            showBanner(bean.getBannerBeanList());
                            showRecommend(bean.getRecommendBeanList(), 1);
                            showRecommend(bean.getRecommendBeanList(), 2);
                            showHotData(bean.getHotBeanList());
                            showLike(bean.getLikeBeanList());
                            showFree(bean.getBookBeanList());

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

    @Override
    public void onRefresh() {
        mPresenter.getBannerData("0");
        mPresenter.getRecommend("1", 1);//主编推荐
        mPresenter.getRecommend("2", 2);//新书推荐
        mPresenter.getHotData(1, 3);
        mPresenter.getLikeBook(6, 1);
        mPresenter.getFreeBook(0, 3);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY >= banner.getHeight()) {
            visible(ivBookstoreTop);
        } else {
            gone(ivBookstoreTop);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
