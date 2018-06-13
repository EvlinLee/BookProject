package com.zhangwan.app.ui.read;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.ChatperAdapter;
import com.zhangwan.app.adapter.ChatperMenuAdapter;
import com.zhangwan.app.adapter.PayCentreAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperDetailBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.bean.CollectBookBean;
import com.zhangwan.app.bean.Config;
import com.zhangwan.app.bean.DayShareBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.bean.event.BookShelfChangeEvent;
import com.zhangwan.app.bean.event.RefreshUserInfoEvent;
import com.zhangwan.app.bean.event.ToFragmentEvent;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.http.service.ReadApiService;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.SearchActivity;
import com.zhangwan.app.ui.bookstore.FiltrBookActivity;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListActivity;
import com.zhangwan.app.ui.bookstore.like.GessLikeListActivity;
import com.zhangwan.app.ui.bookstore.news.NewListActivity;
import com.zhangwan.app.ui.recommend.FilterBookListActivity;
import com.zhangwan.app.utils.ActivityManagerUtils;
import com.zhangwan.app.utils.BrightnessUtil;
import com.zhangwan.app.utils.DayShareUtils;
import com.zhangwan.app.utils.DisplayUtils;
import com.zhangwan.app.utils.LoginDialogUtils;
import com.zhangwan.app.utils.PageFactory;
import com.zhangwan.app.utils.ReadHistorySPUtils;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.utils.StringUtils;
import com.zhangwan.app.utils.UMShareUtil;
import com.zhangwan.app.widget.MyDrawerLayout;
import com.zhangwan.app.widget.PageWidget;
import com.zhangwan.app.widget.dialog.LoadingDialog;
import com.zhangwan.app.widget.dialog.PageModeDialog;
import com.zhangwan.app.widget.dialog.SettingDialog;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 阅读界面
 */

public class ReadActivity extends BaseReadActivity {

    private static final String TAG = "NovelReadActivity";

    private final static int MESSAGE_CHANGEPROGRESS = 1;

    private String shareUrl = "";
    private String intro = "";
    private LoadingDialog dialog;
    private LoadingDialog.Builder loadBuilder;

    @BindView(R.id.bookpage)
    PageWidget bookpage;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.tv_pre)
    TextView tvPre;
    @BindView(R.id.sb_progress)
    SeekBar sbProgress;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_directory)
    TextView tvDirectory;
    @BindView(R.id.tv_dayornight)
    TextView tvDayornight;
    @BindView(R.id.tv_pagemode)
    TextView tvPagemode;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.bookpop_bottom)
    LinearLayout bookpop_bottom;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_stop_read)
    TextView tvStopRead;
    @BindView(R.id.rl_read_bottom)
    RelativeLayout rlReadBottom;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.iv_read_choose)
    ImageView ivReadChoose;
    @BindView(R.id.rv_read_1)
    RecyclerView mRecyclerView1;//章节目录
    @BindView(R.id.rv_read_2)
    RecyclerView mRecyclerView2;//章节选择
    @BindView(R.id.read_drawerlayout)
    MyDrawerLayout mDrawerlayout;

    //解锁相关
    @BindView(R.id.rl_read_lock)
    RelativeLayout rlReadLock;
    @BindView(R.id.tv_read_deblocking_ban)
    TextView tvBookPrice;
    @BindView(R.id.tv_read_deblocking_need)
    TextView tvBookNeed;
    @BindView(R.id.cb_dialog_read_deblocking_auto)
    CheckBox checkBox;
    @BindView(R.id.btn_read_deblocking)
    Button btDeBlock;
    @BindView(R.id.iv_read_deblocking_close)
    ImageView tvLockClose;
    //充值相关
    @BindView(R.id.rl_read_recharge)
    RelativeLayout rlRecharge;
    @BindView(R.id.rv_read)
    RecyclerView rvRecharge;
    @BindView(R.id.iv_read_bookpic)
    ImageView ivRechargePic;
    @BindView(R.id.tv_read_bookname)
    TextView tvRechargeName;
    @BindView(R.id.tv_read_price)
    TextView tvRechargePrice;
    @BindView(R.id.tv_read_balance)
    TextView tvRechargeBalance;
    @BindView(R.id.cb_read_recharge)
    CheckBox cbRecharge;
    @BindView(R.id.iv_read_recharge_close)
    ImageView ivRechargeClose;

    private Config config;
    private WindowManager.LayoutParams lp;

    private PageFactory pageFactory;
    private int screenWidth, screenHeight;
    // popwindow是否显示
    private Boolean isShow = false;
    private SettingDialog mSettingDialog;
    private PageModeDialog mPageModeDialog;
    //日夜模式
    private Boolean mDayOrNight;
    ViewStub vs;
    //章节
    private ChatperAdapter mAdapter1;
    private ChatperMenuAdapter mAdapter2;

    private boolean isShowChatperMenu;//是否显示章节筛选

    Subscription subChatper;//请求章节目录
    Subscription subChatperDetail;//请求章节详情
    Subscription subPayList;//请求充值列表

    private int total = -1;//章节总数 上个界面传过来
    String bookId;//书本id
    String readId;//章节id
    String nextId;//下一个章节id
    String preId;//上一个章节id
    String bookName;//书名
    String picUrl;//封面
    private boolean isCatalogue;//是否从目录进入阅读

    private List<ChatperBean.CListBean> list;
    private ChatperBean bean;

    //第一次进来点击目录才请求目录数据
    private boolean isFirst = true;
    private Handler mHandler = new MyHandler(this);
    private LinearLayoutManager linearLayoutManager;
    // 接收电池信息更新的广播
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Log.d(TAG, Intent.ACTION_BATTERY_CHANGED);
                int level = intent.getIntExtra("level", 0);
                pageFactory.updateBattery(level);
            } else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                Log.d(TAG, Intent.ACTION_TIME_TICK);
                pageFactory.updateTime();
            }
        }
    };

    private String nextIdFee;
    private String nextIdIsPay;
    private String prevIdFee;
    private String prevIdIsPay;
    private String isCollect;//  用户是否已经加入书架
    private int clickType;//  用户点击  1为上一章2为下一章3为从目录进

    private String newReadId;//  用户最新想看的章节id
    private String newFee;//  用户最新想看的章节解锁书币
    private boolean isPay = false;//  章节是否需要付费
    private int balance; //  用户书币余额
    private Dialog collectDialog;
    private int state;

    @Override
    public int getLayoutRes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_read;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        Intent intent = getIntent();

        total = intent.getIntExtra("total", -1);
        bookId = intent.getStringExtra("bookId");
        readId = intent.getStringExtra("readId");
        picUrl = intent.getStringExtra("picUrl");
        bookName = intent.getStringExtra("bookName");
        isCatalogue = intent.getBooleanExtra("isCatalogue", false);

        loadBuilder = new LoadingDialog.Builder(ReadActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);

        vs = findViewById(R.id.vs_load);
        vs.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 19) {
            bookpage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.person_icon_more_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollect.equals("0")) {
                    showCollectDialog();
                } else {
                    finish();
                }
            }
        });

        config = Config.getInstance();
        pageFactory = PageFactory.getInstance();

        IntentFilter mfilter = new IntentFilter();
        mfilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mfilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(myReceiver, mfilter);

        mSettingDialog = new SettingDialog(this);
        mPageModeDialog = new PageModeDialog(this);
        //获取屏幕宽高
        WindowManager manage = getWindowManager();
        Display display = manage.getDefaultDisplay();
        Point displaysize = new Point();
        display.getSize(displaysize);
        screenWidth = displaysize.x;
        screenHeight = displaysize.y;
        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //改变屏幕亮度
        if (!config.isSystemLight()) {
            BrightnessUtil.setBrightness(this, config.getLight());
        }

        bookpage.setPageMode(config.getPageMode());
        pageFactory.setPageWidget(bookpage);

        chosePermissions();
        initDayOrNight();
        initRecyclerView();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        mDrawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                hideReadSetting();
                if (isFirst) {
                    getChapter(1, String.valueOf(total));
                } else {
                    setScrollNowAndClick();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void chosePermissions() {
        String[] pers = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        performRequestPermissions("此应用需要读取文件夹权限", pers, 100,
                new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (isCatalogue) {
                            getChapterDetail(readId);
                        } else {
                            if (ReadHistorySPUtils.getInstance().contains(bookId)) {
                                getChapterDetail(ReadHistorySPUtils.getInstance().getString(bookId));
                            } else {
                                getChapterDetail(readId);
                            }
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtil.showShort(MyApplication.getInstance(), getString(R.string.pre_scan_notice_msg));
                        ReadActivity.this.finish();
                    }
                });
    }

    @Override
    protected void initListener() {
        dialog = loadBuilder.create();
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float pro;

            // 触发操作，拖动
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pro = (float) (progress / 10000.0);
                showProgress(pro);
            }

            // 表示进度条刚开始拖动，开始拖动时候触发的操作
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // 停止拖动时候
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pageFactory.changeProgress(pro);
            }
        });
        mPageModeDialog.setPageModeListener(new PageModeDialog.PageModeListener() {
            @Override
            public void changePageMode(int pageMode) {
                bookpage.setPageMode(pageMode);
            }
        });

        mSettingDialog.setSettingListener(new SettingDialog.SettingListener() {
            @Override
            public void changeSystemBright(Boolean isSystem, float brightness) {
                if (!isSystem) {
                    BrightnessUtil.setBrightness(ReadActivity.this, brightness);
                } else {
                    int bh = BrightnessUtil.getScreenBrightness(ReadActivity.this);
                    BrightnessUtil.setBrightness(ReadActivity.this, bh);
                }
            }

            @Override
            public void changeFontSize(int fontSize) {
                pageFactory.changeFontSize(fontSize);
            }

            @Override
            public void changeTypeFace(Typeface typeface) {
                pageFactory.changeTypeface(typeface);
            }

            @Override
            public void changeBookBg(int type) {
                pageFactory.changeBookBg(type);
            }
        });

        pageFactory.setPageEvent(new PageFactory.PageEvent() {
            @Override
            public void changeProgress(float progress) {
                Message message = new Message();
                message.what = MESSAGE_CHANGEPROGRESS;
                message.obj = progress;
                mHandler.sendMessage(message);
            }
        });

        bookpage.setTouchListener(new PageWidget.TouchListener() {
            @Override
            public void center() {
                if (isShow) {
                    hideReadSetting();
                } else {
                    showReadSetting();
                }
            }

            /**
             * 上一页
             * @return
             */
            @Override
            public Boolean prePage() {
                Log.d("setTouchListener", "prePage");
                if (isShow) {
                    return false;
                }
                pageFactory.prePage();
                if (pageFactory.isfirstPage()) {
                    clickType = 1;
                    if (prevIdFee != null && prevIdFee.equals("0") && prevIdIsPay.equals("1")) {//先判断是否需要收费
                        getChapterDetailInLastPage(preId);
                    } else if (prevIdFee != null) {
                        if (SpUtil.getIsCheckAutoNextChatper(ReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(ReadActivity.this) < Integer.parseInt(prevIdFee)) {//余额小于自动解锁金额
                                showPayDialog(bean, 0, Integer.parseInt(prevIdFee), false);
                            } else {//直接扣除阅读币然后阅读
                                getChapterDetailInLastPage(preId);
                            }
                        } else {//未开启自动解锁
                            showDeblocking();
                        }
                    } else {
                        ToastUtil.showShort(ReadActivity.this, "没有上一页啦！");
                    }
                    return false;
                }
                return true;
            }

            /**
             * 下一页
             * @return
             */
            @Override
            public Boolean nextPage() {
                Log.d("setTouchListener", "nextPage");

                if (isShow) {
                    return false;
                }

                pageFactory.nextPage();
                if (pageFactory.islastPage()) {
                    clickType = 2;
                    getPreAndNext(nextIdFee, nextIdIsPay, nextId);
                    return false;
                }
                return true;
            }

            @Override
            public void cancel() {
                pageFactory.cancelPage();
            }
        });

    }

    public static class MyHandler extends Handler {
        ReadActivity mReadActivity;
        WeakReference<ReadActivity> mWeakReference;

        public MyHandler(ReadActivity mReadActivity) {
            mWeakReference = new WeakReference<>(mReadActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mReadActivity = mWeakReference.get();
            if (mReadActivity == null) return;
            switch (msg.what) {
                case MESSAGE_CHANGEPROGRESS:
                    float progress = (float) msg.obj;
                    mReadActivity.setSeekBarProgress(progress);
                    break;
            }
        }
    }

    private void initRecyclerView() {

        linearLayoutManager = (new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyclerView1.setLayoutManager(linearLayoutManager);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
    }

    /**
     * @param bookId   书籍id
     * @param readId   上次阅读章节id
     * @param total    章节总数
     * @param picUrl   封面
     * @param bookName 书名
     * @param context
     */
    public static void startReadActivity(String bookId, String readId, int total, String picUrl, String bookName, boolean isCatalogue, Activity context) {
        if (SpUtil.getIsLogin(context)) {
            Intent intent = new Intent(context, ReadActivity.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("readId", readId);
            intent.putExtra("total", total);
            intent.putExtra("picUrl", picUrl);
            intent.putExtra("bookName", bookName);
            intent.putExtra("isCatalogue", isCatalogue);
            context.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            context.startActivity(intent);
        } else {
            LoginDialogUtils utils = new LoginDialogUtils(context);
            utils.loginType(context);
        }
    }

    /**
     * 获取章节目录
     *
     * @param start 从1开始
     * @param size  上个页面传来章节总数
     */
    private void getChapter(final int start, String size) {
        final String token = SpUtil.getToken(this);
        if (!"".equals(token)) {

            subChatper = ReadApiService.getInstance()
                    .getChatper(bookId, start, size, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ApiObserver<ApiResponseBean<ChatperBean>>(new ApiCallBack() {
                        @Override
                        public void onSuccess(Object data) {
                            bean = (ChatperBean) data;
                            total = bean.getTotal();
                            list = bean.getCList();
                            if (list != null && list.size() > 0) {
                                isFirst = false;
                                setScrollNowAndClick();
                            }
                        }

                        @Override
                        public void onError(String status, String message) {
                            ToastUtil.showShort(ReadActivity.this, message);

                        }
                    }));
        }

    }

    //滚动到上次阅读的章节
    private void setScrollNowAndClick() {
        //滚动到上次阅读的章节
        for (int i = 0; i < list.size(); i++) {
            if (ReadHistorySPUtils.getInstance().contains(bookId)) {
                if (Integer.valueOf(ReadHistorySPUtils.getInstance().getString(bookId)) == list.get(i).getId()) {
                    mAdapter1 = new ChatperAdapter(ReadActivity.this, Integer.valueOf(ReadHistorySPUtils.getInstance().getString(bookId)), list, R.layout.item_chatper_type_1);
                    mRecyclerView1.setAdapter(mAdapter1);
                    linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    chapterPos = i;
                }
            } else {
                if (Integer.valueOf(readId) == list.get(i).getId()) {
                    mAdapter1 = new ChatperAdapter(ReadActivity.this, Integer.valueOf(readId), list, R.layout.item_chatper_type_1);
                    mRecyclerView1.setAdapter(mAdapter1);
                    linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    chapterPos = i;
                }
            }
        }
        mAdapter1.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                clickType = 3;
                newReadId = String.valueOf(list.get(position).getId());
                newFee = String.valueOf(list.get(position).getFee());
                ChatperBean.CListBean cListBean = bean.getCList().get(position);
                if (0 == cListBean.getIsFee()) {//免费
                    ReadHistorySPUtils.getInstance().put(bookId, String.valueOf(list.get(position).getId()));
                    getChapterDetail(String.valueOf(list.get(position).getId()));
                } else if (1 == cListBean.getIsFee() && 0 == cListBean.getBought()) {//未解锁
                    //设置自动解锁
                    if (SpUtil.getIsCheckAutoNextChatper(ReadActivity.this)) {
                        //金额小于自动解锁金额
                        if (SpUtil.getUserBalacne(ReadActivity.this) < cListBean.getFee()) {
                            showPayDialog(bean, position, cListBean.getFee(), true);
                        } else {//直接扣除阅读币然后阅读
                            ReadHistorySPUtils.getInstance().put(bookId, String.valueOf(list.get(position).getId()));
                            getChapterDetail(String.valueOf(list.get(position).getId()));
                        }
                    } else {//没有自动解锁
                        showDeblocking();
                    }
                } else if (1 == cListBean.getIsFee() && 1 == cListBean.getBought()) {//已解锁
                    ReadHistorySPUtils.getInstance().put(bookId, String.valueOf(list.get(position).getId()));
                    getChapterDetail(String.valueOf(list.get(position).getId()));
                }
                mDrawerlayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    //获取章节内容
    private void getChapterDetail(String cId) {
        String token = SpUtil.getToken(this);
        ReadHistorySPUtils.getInstance().put(bookId, cId);
        ReadApiService.getInstance()
                .getChatperDetail(cId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<ApiResponseBean<ChatperDetailBean>, Boolean>() {
                    @Override
                    public Boolean call(ApiResponseBean<ChatperDetailBean> responseBean) {
                        return responseBean != null && (ApiObserver.SERVER_SUCCESS.equals(responseBean.getStatus()) || "书币不足".equals(responseBean.getMsg()));
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Func1<ApiResponseBean<ChatperDetailBean>, Boolean>() {
                    @Override
                    public Boolean call(ApiResponseBean<ChatperDetailBean> responseBean) {
                        refreshUserInfo();
                        nextId = responseBean.getResult().getNextId();
                        nextIdFee = responseBean.getResult().getNextIdFee();
                        nextIdIsPay = responseBean.getResult().getNextIdIsPay();
                        preId = responseBean.getResult().getPrevId();
                        prevIdFee = responseBean.getResult().getPrevIdFee();
                        prevIdIsPay = responseBean.getResult().getPrevIdIsPay();
                        isCollect = responseBean.getResult().getIsCollect();
                        vs.setVisibility(View.GONE);
                        return pageFactory.cacheBookByNetwork(responseBean.getResult());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        vs.setVisibility(View.GONE);
                        ToastUtil.showShort(ReadActivity.this, "获取数据失败");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            pageFactory.init();

                        } else {

                            ToastUtil.showShort(ReadActivity.this, "打开书籍失败");
                        }

                        vs.setVisibility(View.GONE);
                    }
                });
    }

    //获取上一章节的最后一页
    private void getChapterDetailInLastPage(String cId) {
        if (SpUtil.getIsLogin(this)) {
            String token = SpUtil.getToken(this);
            if (!"".equals(token)) {
                ReadHistorySPUtils.getInstance().put(bookId, cId);
                ReadApiService.getInstance()
                        .getChatperDetail(cId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Func1<ApiResponseBean<ChatperDetailBean>, Boolean>() {
                            @Override
                            public Boolean call(ApiResponseBean<ChatperDetailBean> responseBean) {
                                return responseBean != null && (ApiObserver.SERVER_SUCCESS.equals(responseBean.getStatus()) || "书币不足".equals(responseBean.getMsg()));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<ApiResponseBean<ChatperDetailBean>, Boolean>() {
                            @Override
                            public Boolean call(ApiResponseBean<ChatperDetailBean> responseBean) {
                                refreshUserInfo();
                                nextId = responseBean.getResult().getNextId();
                                nextIdFee = responseBean.getResult().getNextIdFee();
                                nextIdIsPay = responseBean.getResult().getNextIdIsPay();
                                preId = responseBean.getResult().getPrevId();
                                prevIdFee = responseBean.getResult().getPrevIdFee();
                                prevIdIsPay = responseBean.getResult().getPrevIdIsPay();
                                return pageFactory.cacheBookByNetwork(responseBean.getResult());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();

                                ToastUtil.showShort(ReadActivity.this, "获取数据失败");
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    pageFactory.initPre();
                                } else {

                                    ToastUtil.showShort(ReadActivity.this, "打开书籍失败");
                                }
                            }
                        });
            }
        }
    }

    //弹出充值界面
    @SuppressLint("SetTextI18n")
    private void showPayDialog(ChatperBean bean, int position, int fee, boolean isCatalogue) {
        rlRecharge.setVisibility(View.VISIBLE);
        //充值价格列表
        rvRecharge.setLayoutManager(new GridLayoutManager(this, 2));
        getPayList(rvRecharge);
        //封面
        ImageHelper.getInstance().loadImage(this, picUrl, ivRechargePic);
        //书名
        tvRechargeName.setText("书名：" + bookName);
        //价格
        if (isCatalogue) {
            tvRechargePrice.setText("价格：" + bean.getCList().get(position).getFee() + "书币");
        } else {
            tvRechargePrice.setText("价格：" + fee + "书币");
        }
        //余额
        tvRechargeBalance.setText("余额：" + SpUtil.getUserBalacne(this) + "书币");

        boolean isCheckAutoNextChatper = SpUtil.getIsCheckAutoNextChatper(this);
        if (isCheckAutoNextChatper) {
            cbRecharge.setChecked(true);
        } else {
            cbRecharge.setChecked(false);
        }
        cbRecharge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SpUtil.setIsCheckAutoNextChatper(ReadActivity.this, true);
                } else {
                    SpUtil.setIsCheckAutoNextChatper(ReadActivity.this, false);
                }
            }
        });
    }

    //弹出解锁页面
    @SuppressLint("SetTextI18n")
    private void showDeblocking() {
        tvBookPrice.setText("账户余额：" + SpUtil.getUserBalacne(this) + "书币");
        if (clickType == 1) {
            tvBookNeed.setText("本章解锁：" + prevIdFee + "书币");
        } else if (clickType == 2) {
            tvBookNeed.setText("本章解锁：" + nextIdFee + "书币");
        } else if (clickType == 3) {
            tvBookNeed.setText("本章解锁：" + newFee + "书币");
        }
        boolean isCheckAutoNextChatper = SpUtil.getIsCheckAutoNextChatper(this);
        if (isCheckAutoNextChatper) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SpUtil.setIsCheckAutoNextChatper(ReadActivity.this, true);
                } else {
                    SpUtil.setIsCheckAutoNextChatper(ReadActivity.this, false);
                }
            }
        });
        rlReadLock.setVisibility(View.VISIBLE);
    }

    private void getPayList(final RecyclerView recyclerView) {

        subPayList = MineApiService
                .getInstance()
                .getPayList("0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<ApiResponseBean<List<PayListBean>>>(new ApiCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        List<PayListBean> listBeans = (List<PayListBean>) data;
                        for (int i = 0; i < listBeans.size(); i++) {
                            PayListBean bean = listBeans.get(i);
                            bean.setListType("0");
                            listBeans.set(i, bean);
                        }
                        PayCentreAdapter adapter = new PayCentreAdapter(ReadActivity.this, listBeans);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickLitener(new PayCentreAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                ToastUtil.showShort(ReadActivity.this, "po:" + position);
                            }
                        });
                    }

                    @Override
                    public void onError(String status, String message) {
                        ToastUtil.showShort(ReadActivity.this, message);

                    }
                }));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pageFactory.clear();
        bookpage = null;
        unregisterReceiver(myReceiver);
        RxTaskHelper.getInstance().cancelTask(this);
        if (subChatper != null && !subChatper.isUnsubscribed()) {
            subChatper.unsubscribe();
        }
        if (subChatperDetail != null && !subChatperDetail.isUnsubscribed()) {
            subChatperDetail.unsubscribe();
        }
        if (subPayList != null && !subPayList.isUnsubscribed()) {
            subPayList.unsubscribe();
        }
        RxTaskHelper.getInstance().cancelTask(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow) {
                hideReadSetting();
                return true;
            }
            if (mSettingDialog.isShowing()) {
                mSettingDialog.hide();
                return true;
            }
            if (mPageModeDialog.isShowing()) {
                mPageModeDialog.hide();
                return true;
            }
            if (isCollect.equals("0")) {
                showCollectDialog();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_read_book) {
            if (StringUtils.isEmpty(shareUrl)) {
                dialog.show();
                getShare();
            } else {
                showUMDialog(intro, state);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //显示书本进度
    public void showProgress(float progress) {
        if (rlProgress.getVisibility() != View.VISIBLE) {
            rlProgress.setVisibility(View.VISIBLE);
        }
        setProgress(progress);
    }

    //隐藏书本进度
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initDayOrNight() {
        mDayOrNight = config.getDayOrNight();
        if (mDayOrNight) {
            tvDayornight.setText(getResources().getString(R.string.read_setting_day));
            tvDayornight.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.dayornight), null, null);

        } else {
            tvDayornight.setText(getResources().getString(R.string.read_setting_night));
            tvDayornight.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.night), null, null);
        }
    }

    //改变显示模式
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeDayOrNight() {
        if (mDayOrNight) {
            mDayOrNight = false;
            tvDayornight.setText(getResources().getString(R.string.read_setting_night));
            tvDayornight.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.night), null, null);

        } else {
            mDayOrNight = true;
            tvDayornight.setText(getResources().getString(R.string.read_setting_day));
            tvDayornight.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.dayornight), null, null);
        }
        config.setDayOrNight(mDayOrNight);
        pageFactory.setDayOrNight(mDayOrNight);
    }

    private void setProgress(float progress) {
        DecimalFormat decimalFormat = new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(progress * 100.0);//format 返回的是字符串
        tvProgress.setText(p + "%");
    }

    public void setSeekBarProgress(float progress) {
        sbProgress.setProgress((int) (progress * 10000));
    }

    /**
     * 展开设置对话框
     */
    private void showReadSetting() {
        isShow = true;
        rlProgress.setVisibility(View.GONE);

        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_enter);
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_top_enter);
        rlBottom.startAnimation(topAnim);
        appbar.startAnimation(topAnim);
//        ll_top.startAnimation(topAnim);
        rlBottom.setVisibility(View.VISIBLE);
//        ll_top.setVisibility(View.VISIBLE);
        appbar.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 隐藏设置对话框
     */
    private void hideReadSetting() {
        isShow = false;
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_exit);
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.dialog_top_exit);
        if (rlBottom.getVisibility() == View.VISIBLE) {
            rlBottom.startAnimation(topAnim);
        }
        if (appbar.getVisibility() == View.VISIBLE) {
            appbar.startAnimation(topAnim);
        }
        if (rlReadBottom.getVisibility() == View.VISIBLE) {
            rlReadBottom.startAnimation(topAnim);
        }
//        ll_top.startAnimation(topAnim);
        rlBottom.setVisibility(View.GONE);
        rlReadBottom.setVisibility(View.GONE);
//        ll_top.setVisibility(View.GONE);
        appbar.setVisibility(View.GONE);
    }

    /**
     * 友盟对话框
     */
    private void showUMDialog(String info, int state) {
        DayShareUtils utils = new DayShareUtils(ReadActivity.this, state);
        utils.shareUrl(ReadActivity.this, shareUrl,
                picUrl, bookName, info, 1, Integer.valueOf(bookId));
    }

    //  点击事件
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tv_progress, R.id.rl_progress, R.id.tv_pre, R.id.sb_progress, R.id.tv_next,
            R.id.tv_directory, R.id.tv_dayornight, R.id.tv_pagemode, R.id.btn_read_deblocking,
            R.id.tv_setting, R.id.bookpop_bottom, R.id.rl_bottom, R.id.tv_stop_read, R.id.iv_read_choose,
            R.id.iv_read_deblocking_close, R.id.iv_read_recharge_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_progress:
                break;
            case R.id.rl_progress:
                break;
            case R.id.tv_pre:
                clickType = 1;
                getPreAndNext(prevIdFee, prevIdIsPay, preId);
                break;
            case R.id.sb_progress:
                break;
            case R.id.tv_next:
                clickType = 2;
                getPreAndNext(nextIdFee, nextIdIsPay, nextId);
                break;
            case R.id.tv_directory://目录
                mDrawerlayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.tv_dayornight:
                changeDayOrNight();
                break;
            case R.id.tv_pagemode:
                hideReadSetting();
                mPageModeDialog.show();
                break;
            case R.id.tv_setting:
                hideReadSetting();
                mSettingDialog.show();
                break;
            case R.id.bookpop_bottom:
                break;
            case R.id.rl_bottom:
                break;
            case R.id.tv_stop_read:
                hideReadSetting();
                break;
            case R.id.iv_read_choose://选择章节显示菜单样式开关
                if (-1 != total) {
                    if (isShowChatperMenu) {//显示章节菜单，则隐藏章节列表
                        isShowChatperMenu = false;
                        ivReadChoose.setImageResource(R.drawable.icon_read_down);
                        mRecyclerView1.setVisibility(View.VISIBLE);
                        mRecyclerView2.setVisibility(View.GONE);

                    } else {
                        isShowChatperMenu = true;
                        ivReadChoose.setImageResource(R.drawable.icon_read_up);
                        mRecyclerView1.setVisibility(View.GONE);
                        mRecyclerView2.setVisibility(View.VISIBLE);
                        showChatperMenu();
                    }
                }
                break;
            case R.id.btn_read_deblocking: // 解锁
                isPay = true;//  弹出解锁界面证明章节需要付费
                rlReadLock.setVisibility(View.GONE);
                if (clickType == 1) {
                    getChapterDetailInLastPage(preId);
                } else if (clickType == 2) {
                    getChapterDetail(nextId);
                } else if (clickType == 3) {
                    getChapterDetail(newReadId);
                }
                break;
            case R.id.iv_read_deblocking_close: // 关闭解锁界面
                rlReadLock.setVisibility(View.GONE);
                break;
            case R.id.iv_read_recharge_close: // 关闭充值界面
                rlRecharge.setVisibility(View.GONE);
                break;
        }
    }

    private int chapterPos;//上次阅读章节

    /**
     * 展示多少章-多少章菜单
     */
    private void showChatperMenu() {
        final List<ChatperMenuBean> list = new ArrayList<>();
        int size = total / 100;
        int n = 1;
        int m = 100;
        for (int i = 0; i < size; i++) {
            ChatperMenuBean bean = new ChatperMenuBean();
            bean.setName1(n);
            bean.setName2(m);
            n = n + 100;
            m = m + 100;
            list.add(bean);
        }
        ChatperMenuBean bean = new ChatperMenuBean();
        bean.setName1(n);
        bean.setName2(total);
        list.add(bean);

        mAdapter2 = new ChatperMenuAdapter(ReadActivity.this, chapterPos, list, R.layout.item_chatper_type_2);
        mRecyclerView2.setAdapter(mAdapter2);
        mAdapter2.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {//定位到章节那里
            @Override
            public void onItemClick(View v, int position) {


                isShowChatperMenu = false;
                ivReadChoose.setImageResource(R.drawable.icon_read_down);
                mRecyclerView1.setVisibility(View.VISIBLE);
                //跳转到指定位置
                if (position == 0 || position == 1) {
                    linearLayoutManager.scrollToPositionWithOffset(list.get(position).getName1() - 1, 0);
                } else {
                    linearLayoutManager.scrollToPositionWithOffset(list.get(position).getName1(), 0);
                }

                linearLayoutManager.setStackFromEnd(true);
                mRecyclerView2.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //  获取分享链接
    private void getShare() {
        Subscription subscriber = BookStoreApiService.getInstance().apiDayShare(SpUtil.getToken(this), Integer.parseInt(bookId))
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
                        state = url.getResult().getState();
                        shareUrl = url.getResult().getUrl();
                        intro = url.getResult().getBookIntro();
                        dialog.dismiss();
                        showUMDialog(intro, state);
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }

    //  获取章节内容前的判断
    private void getPreAndNext(String fee, String isPay, String id) {
        if (fee.equals("0") && isPay.equals("1")) {//先判断是否需要收费
            getChapterDetail(id);
        } else {
            if (SpUtil.getIsCheckAutoNextChatper(ReadActivity.this)) {//开启自动解锁
                if (SpUtil.getUserBalacne(ReadActivity.this) < Integer.parseInt(fee)) {//余额小于自动解锁金额
                    showPayDialog(bean, 0, Integer.parseInt(fee), false);
                } else {//直接扣除阅读币然后阅读
                    getChapterDetail(id);
                }
            } else {//未开启自动解锁
                showDeblocking();
            }
        }
    }

    //  更新用户书币余额
    private void refreshUserInfo() {
        if (isPay) {
            if (clickType == 1 || clickType == 4) {
                balance = SpUtil.getUserBalacne(ReadActivity.this) - Integer.parseInt(prevIdFee);
            } else if (clickType == 2) {
                balance = SpUtil.getUserBalacne(ReadActivity.this) - Integer.parseInt(nextIdFee);
            } else if (clickType == 3) {
                balance = SpUtil.getUserBalacne(ReadActivity.this) - Integer.parseInt(newFee);
            }
            SpUtil.setUserBalacne(ReadActivity.this, balance);
            EventBusUtil.postStickyEvent(new RefreshUserInfoEvent());
        }
    }

    //  加入书架弹窗
    private void showCollectDialog() {
        collectDialog = new Dialog(this, R.style.common_dialog);
        View view = View.inflate(this, R.layout.dialog_collect, null);
        collectDialog.setContentView(view);
        collectDialog.setCanceledOnTouchOutside(false);
        collectDialog.show();
        Window window = collectDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        view.findViewById(R.id.tv_dialog_collect_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectDialog.dismiss();
                ReadActivity.this.finish();
            }
        });
        view.findViewById(R.id.tv_dialog_collect_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectDialog.dismiss();
                dialog.show();
                collect();
            }
        });
    }

    //  加入书架
    private void collect() {
        Subscription subscriber = BookStoreApiService.getInstance().apiCollectBook(SpUtil.getToken(this), Integer.parseInt(bookId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectBookBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(CollectBookBean collectBookBean) {
                        if (collectBookBean.getCode().equals("200")) {
                            dialog.dismiss();
                            EventBusUtil.postStickyEvent(new BookShelfChangeEvent());
                            ReadActivity.this.finish();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}