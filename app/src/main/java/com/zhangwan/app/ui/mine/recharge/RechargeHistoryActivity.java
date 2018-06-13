package com.zhangwan.app.ui.mine.recharge;

import android.os.Bundle;
import android.view.View;

import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.RechargeHistoryBean;

import java.util.List;

public class RechargeHistoryActivity extends BaseTitleActivity implements RechargeHistoryContract.View {

    private RechargeHistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_recharge);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("充值记录").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new RechargeHistoryPresenter(this);
        presenter.getRechargeHistory(SpUtil.getToken(this), 1, 1);
    }

    @Override
    public void showRechargeHistory(List<RechargeHistoryBean> data) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {
        getBaseEmptyView().showEmptyContent("还没有记录哦");
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
    public void setPresenter(RechargeHistoryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
