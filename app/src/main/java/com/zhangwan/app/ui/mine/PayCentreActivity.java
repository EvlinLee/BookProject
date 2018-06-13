package com.zhangwan.app.ui.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.PayCentreAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.presenter.PayCentreContract;
import com.zhangwan.app.presenter.PayCentrePresenter;
import com.zhangwan.app.recyclerview.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by laoshiren on 2018/3/29.
 * 充值中心
 */

public class PayCentreActivity extends BaseTitleActivity implements PayCentreContract.View {

    @BindView(R.id.rv_pay_centre)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_pay_centre_wechat)
    TextView tvPayCentreWechat;
    private PayCentreContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_centre);

    }


    @SuppressLint("StringFormatInvalid")
    @Override
    public void initData() {
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayCentreActivity.this.finish();
            }
        });
        getBaseHeadView().showTitle(getString(R.string.title_pay_centre));

        tvPayCentreWechat.setText(Html.fromHtml("<font color=\"#c5c5c5\">*充值阅读币可能会有延迟到账情况，如1小时后未到账请联系客服微信：</font>" +
                "<font color=\"#ff8a00\">xxxx."));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        new PayCentrePresenter(this, "0", "1");
        mPresenter.getPayList();

    }

    @OnClick(R.id.btn_pay_centre)
    public void onClick() {

    }

    @Override
    public void showPayList(List<PayListBean> list) {

        PayCentreAdapter adapter = new PayCentreAdapter(this, list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new PayCentreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showShort(PayCentreActivity.this, "po:" + position);
            }
        });
    }



    @Override
    public void tokenOverdue() {

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
        getBaseEmptyView().showEmptyView();
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        getBaseEmptyView().showEmptyView(msg);
    }

    @Override
    public void showNetError() {
        getBaseEmptyView().showEmptyView(getString(R.string.empty_net_error));
    }

    @Override
    public void setPresenter(PayCentreContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
