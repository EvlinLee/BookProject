package com.zhangwan.app.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.read.model.local.DaoDbHelper;
import com.zhangwan.app.utils.CommonUtil;
import com.zhangwan.app.utils.CommonUtils;
import com.zhangwan.app.utils.JPushUtil;
import com.zhangwan.app.utils.ReadHistorySPUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhangwan.app.utils.JPushUtil.ISOPEN;

/**
 * 系统设置
 * Created by Administrator on 2018/3/22 0022.
 */

public class SettingActivity extends BaseTitleActivity {

    @BindView(R.id.push_switch)
    SwitchCompat btnPushSwitch;
    @BindView(R.id.switch_compat_settin_auto_next_chatper)
    SwitchCompat switchCompatChatper;
    @BindView(R.id.ll_set_clear)
    LinearLayout llSetClear;
    @BindView(R.id.ll_set_support)
    LinearLayout llSetSupport;
    @BindView(R.id.ll_set_about)
    LinearLayout llSetAbout;
    @BindView(R.id.tv_set_exit)
    TextView tvSetExit;
    @BindView(R.id.tv_set_size)
    TextView tvSetSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_set);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("系统设置");
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (!SpUtil.getIsLogin(this)) {
            gone(tvSetExit);
        }

        boolean isOpen = SpUtil.getBoolean(this, ISOPEN, false);
        btnPushSwitch.setChecked(isOpen);

        boolean isAutoNextChatper = SpUtil.getIsCheckAutoNextChatper(this);
        switchCompatChatper.setChecked(isAutoNextChatper);

        try {
            tvSetSize.setText(CommonUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        btnPushSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchCompat switchCompat = (SwitchCompat) view;
                boolean isChecked = switchCompat.isChecked();
                SpUtil.putBoolean(SettingActivity.this, ISOPEN, isChecked);
                JPushUtil.isOpenPush(SettingActivity.this, isChecked);
                if (isChecked) {
                    connectJiGuang();
                }

            }
        });
    }

    private void connectJiGuang() {
        String tokent = SpUtil.getToken(this);
        ;
        LogUtil.i("tokent   : " + tokent);
        if (!TextUtils.isEmpty(tokent)) {
            //极光推送
            JPushUtil.getInstance().setJPushAlias(this, tokent);
        }
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.ll_set_clear, R.id.ll_set_support, R.id.ll_set_about,
            R.id.tv_set_exit, R.id.push_switch, R.id.switch_compat_settin_auto_next_chatper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_set_clear:
                CommonUtils.clearAllCache(this);
                ToastUtil.showShort(this, "清除成功！");
                tvSetSize.setText("0K");
                break;
            case R.id.ll_set_support:
                openApplicationMarket("com.zhangwan.app");
                break;
            case R.id.ll_set_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.tv_set_exit:
                ReadHistorySPUtils.getInstance().clear();
                SpUtil.exitSystemt(this);
                DaoDbHelper.getInstance().getSession().getBookChapterBeanDao().deleteAll();
                DaoDbHelper.getInstance().getSession().getBookRecordBeanDao().deleteAll();
                DaoDbHelper.getInstance().getSession().getCollBookBeanDao().deleteAll();
                //退出之后通知其他界面刷新
                EventBusUtil.postStickyEvent(new LoginEvent());
                finish();
                break;
            case R.id.push_switch:
                break;
            case R.id.switch_compat_settin_auto_next_chatper://是否自动解锁下一章
                boolean checked = switchCompatChatper.isChecked();
                SpUtil.setIsCheckAutoNextChatper(this, checked);
                break;
        }
    }

    /**
     * 通过包名 在应用商店打开应用
     *
     * @param packageName 包名
     */
    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "打开应用商店失败", Toast.LENGTH_SHORT).show();
            // 调用系统浏览器进入商城
            String url = "http://app.mi.com/detail/163525?ref=search";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}
