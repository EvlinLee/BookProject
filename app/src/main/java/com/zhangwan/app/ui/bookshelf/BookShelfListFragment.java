package com.zhangwan.app.ui.bookshelf;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.ACache;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.BookRackAdapter;
import com.zhangwan.app.adapter.BookShelfBannerAdapter;
import com.zhangwan.app.adapter.BsBannerAdapter;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.cache.CacheBookShelfListBean;
import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.bean.event.BookShelfChangeEvent;
import com.zhangwan.app.bean.event.ChangeBookShelfEvent;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.presenter.BookShelfListContract;
import com.zhangwan.app.presenter.BookShelfListPresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.ui.BookInfoActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 书架
 * Created by Administrator on 2018/3/24 0024.
 */

public class BookShelfListFragment extends BaseTitleFragment implements BookShelfListContract.View {
    @BindView(R.id.vp_bookshelf_list)
    ViewPager vpBookshelfList;
    @BindView(R.id.rv_rack)
    RecyclerView rvRack;
    @BindView(R.id.ll_bookshelf_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_bookshelf_delete)
    TextView tvBookshelfDelete;
    @BindView(R.id.tv_bookshelf_all)
    TextView tvBookshelfAll;
    @BindView(R.id.ll_bookshelf_dot)
    LinearLayout llBookShelfDot;

    //每页展示的主题个数
    private final int pageSize = 1;
    //当前页索引
    private int currentIndex;

    private List<BookRackBean.BsBean> deleteData = new ArrayList<>();// 删除状态下的书架数据
    private List<BookRackBean.BsBean> normalData = new ArrayList<>();// 正常状态下的书架数据
    private List<String> deleteId = new ArrayList<>();//要删除的书本ID
    private List<BookRackBean.BsBean> cacheData = new ArrayList<>();// 缓存的书架数据
    private BookRackAdapter adapter;

    private int tag = 2;// 1设置为全部取消状态，2设置为全部选择状态

    private String CACHE_BOOKSHELF_LIST = "cache_bookshelf_list";
    private CacheBookShelfListBean cachebean = new CacheBookShelfListBean();

    private CacheBookShelfListBean bean;
    private BookShelfListContract.Presenter presenter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_list, container, false);
        return view;
    }

    @Override
    public void initData() {
        // 加载缓存数据
        bean = (CacheBookShelfListBean) ACache.get(getActivity()).getAsObject(CACHE_BOOKSHELF_LIST);
        if (bean != null) {
            getCacheData();
        }
        EventBusUtil.register(this);
        new BookShelfListPresenter(this);
        rvRack.setNestedScrollingEnabled(false);
        rvRack.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getBookRackData(1, SpUtil.getToken(getActivity()));
        }
        presenter.getBookRackBanner(SpUtil.getToken(MyApplication.getInstance()));
    }

    //书架列表
    @Override
    public void showBookRackData(BookRackBean data) {
        cacheData.addAll(data.getBs());
        cachebean.setBookRack(cacheData);
        Observable.just(cachebean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CacheBookShelfListBean>() {
                    @Override
                    public void call(CacheBookShelfListBean cacheBookShelfListBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOKSHELF_LIST, cacheBookShelfListBean);
                    }
                });
        getBookRack(data.getBs());
    }

    //最近阅读
    @Override
    public void showBookRackBanner(List<BookShelfBannerBean> data) {
        cachebean.setHistory(data);
        Observable.just(cachebean)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<CacheBookShelfListBean>() {
                    @Override
                    public void call(CacheBookShelfListBean cacheBookShelfListBean) {
                        ACache.get(MyApplication.getInstance()).put(CACHE_BOOKSHELF_LIST, cacheBookShelfListBean);
                    }
                });
        getRead(data);
    }

    // 删除书籍
    @Override
    public void showBookRack(Object data) {

        rx.Observable.from(deleteId).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                for (int i = 0; i < deleteData.size(); i++) {
                    if (deleteData.get(i).getId().equals(s)) {
                        deleteData.remove(i); //删除状态下的书架数据
                    }
                }
                for (int i = 0; i < normalData.size(); i++) {
                    if (normalData.get(i).getId().equals(s)) {
                        normalData.remove(i);//正常状态下的书架数据
                    }
                }
            }
        });

        if (deleteData.size() == 0) {
            gone(llBottom);
            adapter = new BookRackAdapter(getActivity(), normalData, 2, 1);
            EventBusUtil.post(true);
        } else {
            tag = 2;// 初始化全选状态
            adapter = new BookRackAdapter(getActivity(), deleteData, 1, 1);
        }

        rvRack.setAdapter(adapter);
        adapter.setOnIdChangeListenter(new BookRackAdapter.OnIdChangeListenter() {
            @Override
            public void onChange(List<String> id) {
                changeBottom(id, deleteData);
            }
        });

        deleteId.clear();
    }

    @Override
    public void setPresenter(BookShelfListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(MyApplication.getInstance(), msg);
    }

    @Override
    public void showNetError() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onChangeBookShelfEvent(ChangeBookShelfEvent event) {
        switch (event.getType()) {
            case 1:
                tag = 2;// 初始化全选状态
                visible(llBottom);
                deleteId.clear();
                tvBookshelfDelete.setText("删除");
                adapter = new BookRackAdapter(getActivity(), deleteData, 1, 1);
                rvRack.setAdapter(adapter);
                adapter.setOnIdChangeListenter(new BookRackAdapter.OnIdChangeListenter() {
                    @Override
                    public void onChange(List<String> id) {
                        changeBottom(id, deleteData);
                    }
                });
                break;
            case 2:
                gone(llBottom);
                adapter = new BookRackAdapter(getActivity(), normalData, 2, 1);
                rvRack.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BookRackBean.BsBean bookBean = (BookRackBean.BsBean) view.getTag();
                        NovelReadActivity.startReadActivity(bookBean.getBookId(), bookBean.getReadId(), Integer.parseInt(bookBean.getTotalNum()), bookBean.getBookPic(), bookBean.getBookName(), false, getActivity());
                    }
                });
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(LoginEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getBookRackData(1, SpUtil.getToken(getActivity()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBookShelfChangeEvent(BookShelfChangeEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getBookRackData(1, SpUtil.getToken(getActivity()));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        EventBusUtil.unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.tv_bookshelf_delete, R.id.tv_bookshelf_all})
    public void onViewClicked(View view) {
        if (NetworkUtil.isConnected(getActivity())) {
            switch (view.getId()) {
                case R.id.tv_bookshelf_delete:
                    if (deleteId == null || deleteId.size() == 0) {
                        ToastUtil.showShort(getActivity(), "请选择要删除的书籍！");
                    } else {
                        final StringBuilder bookId = new StringBuilder();
                        rx.Observable.from(deleteId).subscribe(new Action1<String>() {
                            boolean first = true;

                            @Override
                            public void call(String id) {
                                if (first) {
                                    first = false;
                                } else {
                                    bookId.append(",");
                                }
                                bookId.append(id);
                            }
                        });
                        presenter.deleteBookRack(bookId.toString());
                    }
                    break;
                case R.id.tv_bookshelf_all:
                    if (tag == 2) {
                        visible(llBottom);
                        adapter = new BookRackAdapter(getActivity(), deleteData, 1, tag);
                        rvRack.setAdapter(adapter);
                        tvBookshelfDelete.setText("删除" + "(" + deleteData.size() + ")");
                        tvBookshelfAll.setText("取消");
                        deleteId.clear();
                        for (int i = 0; i < deleteData.size(); i++) {
                            deleteId.add(deleteData.get(i).getId());
                        }
                        adapter.setOnIdChangeListenter(new BookRackAdapter.OnIdChangeListenter() {
                            @Override
                            public void onChange(List<String> id) {
                                tag = 2;//  全选状态下用户点击item记得初始化全选状态
                                changeBottom(id, deleteData);
                            }
                        });
                        tag = 1;
                    } else if (tag == 1) {
                        visible(llBottom);
                        adapter = new BookRackAdapter(getActivity(), deleteData, 1, tag);
                        rvRack.setAdapter(adapter);
                        tvBookshelfDelete.setText("删除" + "(" + 0 + ")");
                        tvBookshelfAll.setText("全选");
                        deleteId.clear();
                        adapter.setOnIdChangeListenter(new BookRackAdapter.OnIdChangeListenter() {
                            @Override
                            public void onChange(List<String> id) {
                                changeBottom(id, deleteData);
                            }
                        });
                        tag = 2;
                    }
                    break;
            }
        } else {
            ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
        }
    }

    private void changeBottom(List<String> id, List<BookRackBean.BsBean> bsBeanList) {
//        tvBookshelfDelete.setText("删除" + "(" + id.size() + ")");/
        tvBookshelfDelete.setText("删除");
        deleteId.clear();
        deleteId.addAll(id);
        if (id.size() == bsBeanList.size()) {//  单选状态下当全部选择（取消）完毕的时候记得改变全选状态
            tag = 1;
            tvBookshelfAll.setText("取消");
        } else {
            tag = 2;
            tvBookshelfAll.setText("全选");
        }
    }

    // 显示最近阅读数据
    private void getRead(final List<BookShelfBannerBean> data) {
        currentIndex = 0;
        llBookShelfDot.removeAllViewsInLayout();
        //需要的页面数
        int pageCount = (int) Math.ceil(data.size() * 1.0 / pageSize);
        final List<View> viewList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            GridView gridView = (GridView) getLayoutInflater().inflate(R.layout.layout_grid_view, vpBookshelfList, false);
            gridView.setNumColumns(pageSize);
            gridView.setAdapter(new BsBannerAdapter(data, i, pageSize, getActivity()));
            viewList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentIndex * pageSize;
                    if (NetworkUtil.isConnected(getActivity())) {
                        BookInfoActivity.gotoBookInfoActivity(getActivity(), data.get(pos).getBookId());
                    } else {
                        ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                    }
                }

            });
        }
        vpBookshelfList.setAdapter(new BookShelfBannerAdapter(viewList));

        for (int i = 0; i < pageCount; i++) {
            llBookShelfDot.addView(getLayoutInflater().inflate(R.layout.view_dot, null));
        }
        //使第一个小圆点呈选中状态
        llBookShelfDot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
        vpBookshelfList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                llBookShelfDot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                llBookShelfDot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
                currentIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    // 显示书架数据
    private void getBookRack(List<BookRackBean.BsBean> data) {
        //  初始化
        BookRackBean.BsBean bsBean = new BookRackBean.BsBean();
        deleteData.clear();
        normalData.clear();
        bsBean.setBookName("");
        bsBean.setBookPic("");
        bsBean.setId("");
        if (data != null && data.size() != 0) {//不为空的时候
            deleteData.addAll(data);
            data.add(bsBean);
            normalData.addAll(data);
        } else {// 为空的时候只显示添加书架
            normalData.add(bsBean);
        }
        adapter = new BookRackAdapter(getActivity(), normalData, 2, 1);
        rvRack.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookRackBean.BsBean bookBean = (BookRackBean.BsBean) view.getTag();
                if (NetworkUtil.isConnected(getActivity())) {
                    NovelReadActivity.startReadActivity(bookBean.getBookId(), bookBean.getReadId(), Integer.parseInt(bookBean.getTotalNum()), bookBean.getBookPic(), bookBean.getBookName(), false, getActivity());
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
            }
        });
    }

    // 显示缓存数据
    private void getCacheData() {
        Observable.just(CACHE_BOOKSHELF_LIST).subscribeOn(Schedulers.io()).map(new Func1<String, CacheBookShelfListBean>() {
            @Override
            public CacheBookShelfListBean call(String key) {
                return bean;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CacheBookShelfListBean>() {
            @Override
            public void call(CacheBookShelfListBean bean) {
                if (bean != null) {
                    if (bean.getHistory() != null) {
                        getRead(bean.getHistory());
                    }
                    if (bean.getBookRack() != null) {
                        getBookRack(bean.getBookRack());
                    }
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
