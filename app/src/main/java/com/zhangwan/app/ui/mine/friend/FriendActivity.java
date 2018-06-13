package com.zhangwan.app.ui.mine.friend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.ViewPagerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请好友
 * Created by Administrator on 2018/3/23 0023.
 */

public class FriendActivity extends BaseTitleActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_friend_in)
    TextView tvFriendIn;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    @BindView(R.id.vp_friend)
    ViewPager vpFriend;

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_friend);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("邀请好友");
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initFragmentTab();
        vpFriend.addOnPageChangeListener(this);
        /*
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        vpFriend.setAdapter(adapter);
        vpFriend.setCurrentItem(0);
    }

    @OnClick({R.id.tv_friend_in, R.id.tv_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_friend_in:
                if (vpFriend.getCurrentItem() != 0) {
                    resetTab();
                    vpFriend.setCurrentItem(0);
                    tvFriendIn.setTextColor(getResources().getColor(R.color.main));
                    tvFriendIn.setBackgroundResource(R.drawable.bottom_main);
                }
                break;
            case R.id.tv_friend:
                if (vpFriend.getCurrentItem() != 1) {
                    resetTab();
                    vpFriend.setCurrentItem(1);
                    tvFriend.setTextColor(getResources().getColor(R.color.main));
                    tvFriend.setBackgroundResource(R.drawable.bottom_main);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                resetTab();
                tvFriendIn.setTextColor(getResources().getColor(R.color.main));
                tvFriendIn.setBackgroundResource(R.drawable.bottom_main);
                break;
            case 1:
                resetTab();
                tvFriend.setTextColor(getResources().getColor(R.color.main));
                tvFriend.setBackgroundResource(R.drawable.bottom_main);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //初始化fragment和tab
    private void initFragmentTab() {
        mFragments.add(new InviteFragment());
        mFragments.add(new FriendFragment());
    }

    //重置tab
    private void resetTab() {
        tvFriendIn.setBackgroundResource(0);
        tvFriend.setBackgroundResource(0);
        tvFriendIn.setTextColor(getResources().getColor(R.color.black_88));
        tvFriend.setTextColor(getResources().getColor(R.color.black_88));
    }
}
