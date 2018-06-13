package com.zhangwan.app.ui.mine.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gxtc.commlibrary.utils.SpUtil;
import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.DayShareAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.DayShareBean;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.presenter.HistoryContract;
import com.zhangwan.app.presenter.HistoryPresenter;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.utils.DayShareUtils;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.utils.UMShareUtil;
import com.zhangwan.app.widget.dialog.LoadingDialog;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//  每日分享
public class DayShareActivity extends BaseTitleActivity implements HistoryContract.View {
    @BindView(R.id.rv_dayshare)
    RecyclerView rvDayshare;

    private LoadingDialog dialog;
    private LoadingDialog.Builder loadBuilder;

    private HistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_dayshare);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("每日分享").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvDayshare.setNestedScrollingEnabled(false);
        rvDayshare.setLayoutManager(new LinearLayoutManager(this));
        loadBuilder = new LoadingDialog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);

        new HistoryPresenter(this);
        presenter.getHistory(1, SpUtil.getToken(this));
    }

    @Override
    public void showHistory(final HistoryBean data) {
        DayShareAdapter adapter = new DayShareAdapter(this, data.getList());
        rvDayshare.setAdapter(adapter);

        adapter.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                dialog = loadBuilder.create();
                dialog.show();
                getShare(Integer.parseInt(data.getList().get(position).getBookId()));
            }
        });
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

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        RxTaskHelper.getInstance().cancelTask(this);
    }

    //分享的时候记得加上，（QQ和新浪回调需要，如果是fragment需要在他的父activity中加上）
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //  获取分享链接
    private void getShare(int bookId) {
        Subscription subscriber = BookStoreApiService.getInstance().apiDayShare(SpUtil.getToken(this), bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DayShareBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("BookInfoActivity", "onError: " + e);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(DayShareBean url) {
                        dialog.dismiss();
                        DayShareUtils utils = new DayShareUtils(DayShareActivity.this, url.getResult().getState());
                        utils.shareUrl(DayShareActivity.this,
                                url.getResult().getUrl(), url.getResult().getBookPic(), url.getResult().getBookName(), url.getResult().getBookIntro(), 2, 0);
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }
}
