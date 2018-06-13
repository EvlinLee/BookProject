package com.zhangwan.app.ui.mine.friend;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.SpUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.FriendAdapter;
import com.zhangwan.app.base.BaseLazyFragment;
import com.zhangwan.app.bean.FriendBean;
import com.zhangwan.app.recyclerview.RecyclerView;

import butterknife.BindView;

/**
 * 我的好友
 * Created by Administrator on 2018/3/23 0023.
 */

public class FriendFragment extends BaseLazyFragment implements FriendContract.View {
    @BindView(R.id.tv_friend_num)
    TextView tvFriendNum;
    @BindView(R.id.tv_friend_money)
    TextView tvFriendMoney;
    @BindView(R.id.ll_friend_no)
    LinearLayout llFriendNo;
    @BindView(R.id.rv_friend)
    RecyclerView rvFriend;

    private FriendContract.Presenter presenter;

    @Override
    protected void lazyLoad() {
        getBaseLoadingView().showLoading();
        rvFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFriend.setNestedScrollingEnabled(false);
        new FriendPresenter(this);
        presenter.getFriend(SpUtil.getToken(getActivity()));
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showFriend(FriendBean data) {
        tvFriendNum.setText(data.getCount()+"人");
        tvFriendMoney.setText(data.getReward()+"书券");
        if (data.getList() != null && data.getList().size() != 0) {
            gone(llFriendNo);
            FriendAdapter adapter = new FriendAdapter(getActivity(), data.getList());
            rvFriend.setAdapter(adapter);
        }
    }

    @Override
    public void showLoad() {

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

    }

    @Override
    public void setPresenter(FriendContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
