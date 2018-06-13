package com.zhangwan.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.TabBean;
import com.zhangwan.app.bean.event.ChangeBookShelfEvent;
import com.zhangwan.app.bean.event.ToFragmentEvent;
import com.zhangwan.app.ui.bookshelf.BookShelfFragment;
import com.zhangwan.app.ui.bookstore.BookStoreFragment;
import com.zhangwan.app.ui.mine.MineFragment;
import com.zhangwan.app.ui.recommend.RecommendFragment;
import com.zhangwan.app.utils.JPushUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

import static com.zhangwan.app.utils.JPushUtil.ISOPEN;

public class MainActivity extends BaseTitleActivity {

    @BindView(R.id.common_tab_layout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    //退出时间
    private long exitTime = 0;

    private String[] mTitles = {"书架", "推荐", "书城", "我的"};
    private ArrayList<CustomTabEntity> tabEntitys;

    private BookShelfFragment bookShelfFragment;
    private RecommendFragment recommendFragment;
    private BookStoreFragment bookStoreFragment;
    private MineFragment mineFragment;


    private int[] mIconNormalIds = {R.drawable.main_bookshelf_normal, R.drawable.main_recommend_normal,
            R.drawable.main_bookstore_normal, R.drawable.main_mine_normal};
    private int[] mIconPressIds = {R.drawable.main_bookshelf_select, R.drawable.main_recommend_select,
            R.drawable.main_bookstore_select, R.drawable.main_mine_select};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        EventBusUtil.register(this);
        bookShelfFragment = new BookShelfFragment();
        recommendFragment = new RecommendFragment();
        bookStoreFragment = new BookStoreFragment();
        mineFragment = new MineFragment();
        connectJiGuang();
        tabEntitys = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            tabEntitys.add(new TabBean(mTitles[i], mIconPressIds[i], mIconNormalIds[i]));
        }
        commonTabLayout.setTabData(tabEntitys);

        commonTabLayout.setCurrentTab(1);
        switchFragment(recommendFragment, RecommendFragment.class.getSimpleName(), R.id.fl_fragment);
    }

    @Override
    public void initListener() {
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                switch (position) {
                    //书架
                    case 0:
                        switchFragment(bookShelfFragment, BookShelfFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //推荐
                    case 1:
                        switchFragment(recommendFragment, RecommendFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //书城
                    case 2:
                        switchFragment(bookStoreFragment, BookStoreFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //我的
                    case 3:
                        switchFragment(mineFragment, MineFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        String token = SpUtil.getToken(MainActivity.this);
                        LogUtil.printD("token:" + token);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    //连接极光
    private void connectJiGuang() {
        boolean isOpen = SpUtil.getBoolean(this, ISOPEN, false);
        if (isOpen) {
            String tokent = SpUtil.getToken(this);
            ;
            LogUtil.i("tokent   : " + tokent);
            if (!TextUtils.isEmpty(tokent)) {
                //极光推送
                JPushUtil.getInstance().setJPushAlias(this, tokent);
            }
        }
    }

    //跳转书城
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onToBookStoreEvent(ToFragmentEvent event) {
        if (event.getId() == 2) {
            commonTabLayout.setCurrentTab(2);
            switchFragment(bookStoreFragment, BookStoreFragment.class.getSimpleName(), R.id.fl_fragment);
        } else if (event.getId() == 0) {
            commonTabLayout.setCurrentTab(0);
            switchFragment(bookShelfFragment, BookShelfFragment.class.getSimpleName(), R.id.fl_fragment);
        }
    }

    //隐藏导航栏
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onChangeBookShelfEvent(ChangeBookShelfEvent event) {
        switch (event.getType()) {
            case 1:
                gone(commonTabLayout);
                break;
            case 2:
                visible(commonTabLayout);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;

        }

        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showShort(this, "再按一次退出程序");

            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
