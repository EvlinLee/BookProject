package com.zhangwan.app.ui.mine;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseTitleActivity {
    @BindView(R.id.tv_about_update)
    TextView tvAboutUpdate;
    @BindView(R.id.ll_about_update)
    LinearLayout llAboutUpdate;
    @BindView(R.id.tv_about_wechat)
    TextView tvAboutWechat;
    @BindView(R.id.ll_about_wechat)
    LinearLayout llAboutWechat;
    @BindView(R.id.tv_about_qq)
    TextView tvAboutQq;
    @BindView(R.id.ll_about_qq)
    LinearLayout llAboutQq;

    private ClipboardManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("关于我们").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.ll_about_update, R.id.ll_about_wechat, R.id.ll_about_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_about_update:
                ToastUtil.showShort(this, "已经是最新版本！");
                break;
            case R.id.ll_about_wechat:
                manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                assert manager != null;
                manager.setText(tvAboutWechat.getText().toString());
                ToastUtil.showShort(this, "复制成功！");
                break;
            case R.id.ll_about_qq:
                manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                assert manager != null;
                manager.setText(tvAboutQq.getText().toString());
                ToastUtil.showShort(this, "复制成功！");
                break;
        }
    }
}
