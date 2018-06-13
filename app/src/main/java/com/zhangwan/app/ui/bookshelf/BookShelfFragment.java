package com.zhangwan.app.ui.bookshelf;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.ViewPagerAdapter;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.bean.event.ChangeBookShelfEvent;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.utils.LoginDialogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 书架
 * Created by sjr on 2018/1/9.
 */

public class BookShelfFragment extends BaseTitleFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_fragment_bookshelf_details)
    TextView tvFragmentBookshelfDetails;
    @BindView(R.id.tv_fragment_bookshelf_history)
    TextView tvFragmentBookshelfHistory;
    @BindView(R.id.iv_fragment_bookshelf_delete)
    ImageView ivFragmentBookshelfDelete;
    @BindView(R.id.tv_fragment_bookshelf_finish)
    TextView tvFinish;
    @BindView(R.id.vp_fragment_bookshelf_content)
    ViewPager vpFragmentBookshelfContent;
    @BindView(R.id.ll_notlogin)
    LinearLayout llNoLogin;

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main_bookshelf, container, false);
        return view;
    }

    @Override
    public void initData() {
        EventBusUtil.register(this);
        if (SpUtil.getIsLogin(getActivity())) {
            gone(llNoLogin);
            visible(vpFragmentBookshelfContent);
        } else {
            visible(llNoLogin);
            gone(vpFragmentBookshelfContent);
        }
        initFragmentTab();
        vpFragmentBookshelfContent.addOnPageChangeListener(this);
        /*
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        vpFragmentBookshelfContent.setAdapter(adapter);
//        vpFragmentBookshelfContent.setCurrentItem(0);
    }

    @OnClick({R.id.tv_fragment_bookshelf_details, R.id.tv_fragment_bookshelf_history, R.id.iv_fragment_bookshelf_delete, R.id.ll_notlogin, R.id.tv_fragment_bookshelf_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment_bookshelf_details:
                resetTab();
                vpFragmentBookshelfContent.setCurrentItem(0);
                tvFragmentBookshelfDetails.setBackgroundResource(R.drawable.textview_bottom_white);
                visible(ivFragmentBookshelfDelete);
                break;
            case R.id.tv_fragment_bookshelf_history:
                if (!SpUtil.getIsLogin(getActivity())) {
                    showLoginType();
                    return;
                }
                resetTab();
                vpFragmentBookshelfContent.setCurrentItem(1);
                tvFragmentBookshelfHistory.setBackgroundResource(R.drawable.textview_bottom_white);
                gone(ivFragmentBookshelfDelete);
                break;
            case R.id.iv_fragment_bookshelf_delete:
                if (NetworkUtil.isConnected(getActivity())){
                    if (!SpUtil.getIsLogin(getActivity())) {
                        showLoginType();
                        return;
                    }
                    gone(ivFragmentBookshelfDelete);
                    visible(tvFinish);
                    tvFragmentBookshelfHistory.setClickable(false);
                    EventBusUtil.postStickyEvent(new ChangeBookShelfEvent(1));
                }else {
                    ToastUtil.showShort(getActivity(),"网络异常，请检查网络设置！");
                }
                break;
            case R.id.tv_fragment_bookshelf_finish:
                visible(ivFragmentBookshelfDelete);
                gone(tvFinish);
                tvFragmentBookshelfHistory.setClickable(true);
                EventBusUtil.postStickyEvent(new ChangeBookShelfEvent(2));
                break;
            case R.id.ll_notlogin:
                showLoginType();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToBookStoreEvent(Boolean isCancel) {
        if(isCancel){
            visible(ivFragmentBookshelfDelete);
            gone(tvFinish);
            tvFragmentBookshelfHistory.setClickable(true);
            EventBusUtil.postStickyEvent(new ChangeBookShelfEvent(2));
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
                tvFragmentBookshelfDetails.setBackgroundResource(R.drawable.textview_bottom_white);
                visible(ivFragmentBookshelfDelete);
                break;
            case 1:
                resetTab();
                tvFragmentBookshelfHistory.setBackgroundResource(R.drawable.textview_bottom_white);
                gone(ivFragmentBookshelfDelete);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onLoginEvent(LoginEvent event) {
        if (SpUtil.getIsLogin(getActivity())) {
            gone(llNoLogin);
            visible(vpFragmentBookshelfContent);
        } else {
            visible(llNoLogin);
            gone(vpFragmentBookshelfContent);
        }
    }

    //初始化fragment和tab
    private void initFragmentTab() {
        mTitleList.add("书架");
        mTitleList.add("历史");
        mFragments.add(new BookShelfListFragment());
        mFragments.add(new HistoryFragment());
    }

    //重置tab
    private void resetTab() {
        tvFragmentBookshelfDetails.setBackgroundResource(0);
        tvFragmentBookshelfHistory.setBackgroundResource(0);
    }

    //弹出登录方式
    private void showLoginType() {
        LoginDialogUtils utils = new LoginDialogUtils(getActivity());
        utils.loginType(getActivity());
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }
}
