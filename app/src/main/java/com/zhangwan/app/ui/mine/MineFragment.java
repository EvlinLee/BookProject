package com.zhangwan.app.ui.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.CountWelfareBean;
import com.zhangwan.app.bean.SignBean;
import com.zhangwan.app.bean.UserInfoBean;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.bean.event.MessageEvent;
import com.zhangwan.app.bean.event.RefreshUserInfoEvent;
import com.zhangwan.app.presenter.MineContract;
import com.zhangwan.app.presenter.MinePresenter;
import com.zhangwan.app.ui.mine.friend.FriendActivity;
import com.zhangwan.app.ui.mine.message.MessageActivity;
import com.zhangwan.app.ui.mine.recharge.RechargeHistoryActivity;
import com.zhangwan.app.ui.mine.ticket.TicketActivity;
import com.zhangwan.app.utils.LoginDialogUtils;
import com.zhangwan.app.utils.RegexUtils;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.widget.CircleImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by sjr on 2018/1/9.
 * 我的
 */

public class MineFragment extends BaseTitleFragment implements MineContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_mine_pic)
    CircleImageView ivMinePic;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_userid)
    TextView tvMineUserid;
    @BindView(R.id.tv_mine_coin)
    TextView tvMineCoin;
    @BindView(R.id.ll_mine_coin)
    LinearLayout llMineCoin;
    @BindView(R.id.tv_mine_welfare)
    TextView tvMineWelfare;
    @BindView(R.id.ll_mine_welfare)
    LinearLayout llMineWelfare;
    @BindView(R.id.tv_mine_money)
    TextView tvMineMoney;
    @BindView(R.id.ll_mine_money)
    LinearLayout llMineMoney;
    @BindView(R.id.ll_mine_recharge)
    LinearLayout llMineRecharge;
    @BindView(R.id.ll_mine_coupon)
    LinearLayout llMineCoupon;
    @BindView(R.id.ll_mine_friend)
    LinearLayout llMineFriend;
    @BindView(R.id.ll_mine_service)
    LinearLayout llMineService;
    @BindView(R.id.ll_mine_feedback)
    LinearLayout llMineFeedback;
    @BindView(R.id.ll_mine_auto)
    LinearLayout llMineAuto;
    @BindView(R.id.ll_mine_set)
    LinearLayout llMineSet;
    @BindView(R.id.sf_mine)
    SwipeRefreshLayout sfMine;
    @BindView(R.id.iv_mine_message)
    ImageView ivMineMessage;
    @BindView(R.id.tv_mine_sign)
    TextView tvMineSign;

    private MineContract.Presenter presenter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void initData() {
        EventBusUtil.register(this);
        sfMine.setColorSchemeColors(getResources().getColor(R.color.main));
        //为SwipeRefreshLayout设置监听事件
        sfMine.setOnRefreshListener(this);
        new MinePresenter(this);
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getUserInfo(SpUtil.getToken(getActivity()));
            presenter.getCountWelfare(SpUtil.getToken(getActivity()));
            presenter.getSign(SpUtil.getToken(getActivity()));
        } else {
            tvMineName.setText("请登录");
            sfMine.setEnabled(false);
        }
        setUserInfo();
    }

    @OnClick({R.id.iv_mine_pic, R.id.tv_mine_name, R.id.tv_mine_userid, R.id.tv_mine_coin, R.id.ll_mine_coin,
            R.id.tv_mine_welfare, R.id.ll_mine_welfare, R.id.tv_mine_money, R.id.ll_mine_money, R.id.ll_mine_recharge,
            R.id.ll_mine_coupon, R.id.ll_book_ticket, R.id.ll_mine_friend, R.id.ll_mine_service, R.id.ll_mine_feedback, R.id.ll_mine_auto,
            R.id.ll_mine_set, R.id.iv_mine_message, R.id.tv_mine_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_pic:
                if (SpUtil.getIsLogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    LoginDialogUtils utils = new LoginDialogUtils(getActivity());
                    utils.loginType(getActivity());
                }
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.tv_mine_userid:
                break;
            case R.id.tv_mine_coin:
                if (NetworkUtil.isConnected(getActivity())) {
                    if (SpUtil.getIsLogin(getActivity())) {
                        startActivity(RechargeHistoryActivity.class);
                    } else {
                        LoginDialogUtils utils = new LoginDialogUtils(getActivity());
                        utils.loginType(getActivity());
                    }
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_coin:
                if (NetworkUtil.isConnected(getActivity())) {
                    if (SpUtil.getIsLogin(getActivity())) {
                        startActivity(RechargeHistoryActivity.class);
                    } else {
                        LoginDialogUtils utils = new LoginDialogUtils(getActivity());
                        utils.loginType(getActivity());
                    }
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.tv_mine_welfare:
                if (NetworkUtil.isConnected(getActivity())) {
                    WebViewActivity.startActivity(getActivity(), "http://120.76.65.43/book/book_welfare.html?token=" + SpUtil.getToken(getActivity()), true);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_welfare:
                if (NetworkUtil.isConnected(getActivity())) {
                    WebViewActivity.startActivity(getActivity(), "http://120.76.65.43/book/book_welfare.html?token=" + SpUtil.getToken(getActivity()), true);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.tv_mine_money:
                break;
            case R.id.ll_mine_money:
                break;
            case R.id.ll_mine_recharge://书币充值
                if (NetworkUtil.isConnected(getActivity())) {
                    if (SpUtil.getIsLogin(getActivity())) {
                        GotoUtil.goToActivity(MineFragment.this, PayCentreActivity.class);
                    } else {
                        LoginDialogUtils utils = new LoginDialogUtils(getActivity());
                        utils.loginType(getActivity());
                    }
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_coupon:
                if (NetworkUtil.isConnected(getActivity())) {
                    startActivity(TicketActivity.class);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_book_ticket:
                if (NetworkUtil.isConnected(getActivity())) {
                    startActivity(BookTicketActivity.class);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_friend:
                if (NetworkUtil.isConnected(getActivity())) {
                    startActivity(FriendActivity.class);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_service://客服帮助
                if (NetworkUtil.isConnected(getActivity())) {
                    WebViewActivity.startActivity(getActivity(), "http://120.76.65.43/book/book_help.html", false);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.tv_mine_sign://福利中心
                if (NetworkUtil.isConnected(getActivity())) {
                    WebViewActivity.startActivity(getActivity(), "http://120.76.65.43/book/book_welfare.html?token=" + SpUtil.getToken(getActivity()), true);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_feedback:
                if (NetworkUtil.isConnected(getActivity())) {
                    startActivity(SuggestionFeedbackActivity.class);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_auto:
                if (NetworkUtil.isConnected(getActivity())) {
                    WebViewActivity.startActivity(getActivity(), "http://120.76.65.43/book/book_becomeAuthor.html", false);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
            case R.id.ll_mine_set:
                startActivity(SettingActivity.class);
                break;
            case R.id.iv_mine_message://  我的消息
                if (NetworkUtil.isConnected(getActivity())) {
                    ivMineMessage.setImageResource(R.drawable.no_message);
                    startActivity(MessageActivity.class);
                } else {
                    ToastUtil.showShort(getActivity(), getString(R.string.empty_net_error));
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(final LoginEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            sfMine.setEnabled(true);
            presenter.getUserInfo(SpUtil.getToken(getActivity()));
            presenter.getCountWelfare(SpUtil.getToken(getActivity()));
            presenter.getSign(SpUtil.getToken(getActivity()));
        } else {
            setUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRefreshUserInfo(RefreshUserInfoEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            presenter.getUserInfo(SpUtil.getToken(getActivity()));
            presenter.getCountWelfare(SpUtil.getToken(getActivity()));
            presenter.getSign(SpUtil.getToken(getActivity()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        ivMineMessage.setImageResource(R.drawable.have_message);
    }

    @Override
    public void showUserInfo(final UserInfoBean data) {
        SpUtil.setUserName(getActivity(), data.getUserName());
        SpUtil.setUserId(getActivity(), data.getUserId() + "");
        SpUtil.setUserPic(getActivity(), data.getUserPic());
        SpUtil.setUserBalacne(getActivity(), data.getUserBalacne());
        SpUtil.setUserSex(getActivity(), data.getSex());
        if (data.getIsVip() == 0) {
            SpUtil.setUserVip(getActivity(), false);
        } else {
            SpUtil.setUserVip(getActivity(), true);
        }
        setUserInfo();
        sfMine.setRefreshing(false);
    }

    @Override
    public void showSign(SignBean data) {
        SpUtil.setUserSign(getActivity(), data.isResult());
        if (!data.isResult()) {
            tvMineSign.setTextColor(getResources().getColor(R.color.text_color_black_3));
            tvMineSign.setBackgroundResource(R.drawable.mine_sign_grey);
        } else {
            tvMineSign.setTextColor(getResources().getColor(R.color.white));
            tvMineSign.setBackgroundResource(R.drawable.mine_sign_bg);
        }
    }

    @Override
    public void showCountWelfare(CountWelfareBean data) {
        String welfare = data.getTotal() - data.getNum() + "/" + data.getTotal();
        SpUtil.setUserWelfare(getActivity(), welfare);
        tvMineWelfare.setText(welfare);
    }

    @Override
    public void setPresenter(MineContract.Presenter presenter) {
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
        sfMine.setRefreshing(false);
    }

    @Override
    public void showNetError() {
        sfMine.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.getUserInfo(SpUtil.getToken(getActivity()));
        presenter.getCountWelfare(SpUtil.getToken(getActivity()));
        presenter.getSign(SpUtil.getToken(getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
        presenter.destroy();
        RxTaskHelper.getInstance().cancelTask(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            setUserInfo();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUserInfo() {
        if (SpUtil.getIsLogin(getActivity())) {
            ImageHelper.getInstance().loadImage(getActivity(), SpUtil.getUserPic(getActivity()), ivMinePic);
            String userName = SpUtil.getUserName(getActivity());
            if (RegexUtils.isMobileExact(userName)) {//电话号码
                StringBuffer buffer = new StringBuffer(userName);
                buffer.replace(3, 7, "****");
                tvMineName.setText(buffer.toString());
            } else {
                tvMineName.setText(userName);
            }
            tvMineWelfare.setText(SpUtil.getUserWelfare(getActivity()));
            tvMineUserid.setText("ID：" + SpUtil.getUserId(getActivity()));
            if (SpUtil.getUserBalacne(getActivity()) == 0) {
                tvMineCoin.setText("0.0");
            } else {
                tvMineCoin.setText("" + SpUtil.getUserBalacne(getActivity()));
            }
            if (!SpUtil.getUserSign(getActivity())) {
                tvMineSign.setTextColor(getResources().getColor(R.color.text_color_black_3));
                tvMineSign.setBackgroundResource(R.drawable.mine_sign_grey);
            }
        } else {
            sfMine.setEnabled(false);
            tvMineCoin.setText("0.0");
            tvMineName.setText("请登录");
            tvMineUserid.setText("");
            tvMineWelfare.setText("0");
            ivMinePic.setImageResource(R.drawable.default_author);
        }
    }

}
