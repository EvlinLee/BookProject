package com.zhangwan.app.ui.bookshelf;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.HistoryAdapter;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.DeleteHistoryBean;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.ApiService;
import com.zhangwan.app.presenter.HistoryContract;
import com.zhangwan.app.presenter.HistoryPresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.utils.StringUtils;
import com.zhangwan.app.widget.SwipeItemLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 历史
 * Created by Administrator on 2018/3/24 0024.
 */

public class HistoryFragment extends BaseTitleFragment implements HistoryContract.View {
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;

    private HistoryContract.Presenter presenter;

    private HistoryAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_history, container, false);
        return view;
    }

    @Override
    public void initData() {
        EventBusUtil.register(this);
        new HistoryPresenter(this);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHistory.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getHistory(1, SpUtil.getToken(getActivity()));
        }
    }

    @Override
    public void showHistory(final HistoryBean data) {
        if (data != null && data.getList() != null && data.getList().size() != 0) {
            getBaseEmptyView().hideEmptyView();
            adapter = new HistoryAdapter(getActivity(), data.getList());
            rvHistory.setAdapter(adapter);
            adapter.setOnDeleteListenter(new HistoryAdapter.OnDeleteListenter() {
                @Override
                public void onDelete(int position, String id) {
                    deleteBook(data, position, Integer.parseInt(id));
                }
            });
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryBean.ListBean bookBean = (HistoryBean.ListBean) view.getTag();
                    if (NetworkUtil.isConnected(getActivity())) {
                        NovelReadActivity.startReadActivity(bookBean.getBookId(), String.valueOf(bookBean.getChapterId()), Integer.parseInt(bookBean.getTotalNum()), bookBean.getBookPic(), bookBean.getBookName(), false, getActivity());
                    } else {
                        ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                    }
                }
            });
        } else {
            getBaseEmptyView().showEmptyContent();
        }
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
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

    }

    @Override
    public void showNetError() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(LoginEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getHistory(1, SpUtil.getToken(getActivity()));
        }
    }

    private void deleteBook(final HistoryBean data, final int position, int id) {
        Subscription subscription = ApiService.getInstance().apiDeleteHistory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteHistoryBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(DeleteHistoryBean bean) {
                        if (bean.getCode().equals("200")) {
                            data.getList().remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, data.getList().size());
                        }
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        RxTaskHelper.getInstance().cancelTask(this);
        EventBusUtil.unregister(this);
    }
}
