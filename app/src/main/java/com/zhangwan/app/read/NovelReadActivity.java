package com.zhangwan.app.read;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.PayCentreAdapter;
import com.zhangwan.app.bean.CollectBookBean;
import com.zhangwan.app.bean.DayShareBean;
import com.zhangwan.app.bean.PayListBean;
import com.zhangwan.app.bean.event.BookShelfChangeEvent;
import com.zhangwan.app.bean.event.RefreshUserInfoEvent;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.read.base.BaseMVPActivity;
import com.zhangwan.app.read.model.bean.BookChapterBean;
import com.zhangwan.app.read.model.bean.CollBookBean;
import com.zhangwan.app.read.model.bean.ZhuiYueChapterInfoBean;
import com.zhangwan.app.read.model.local.BookRepository;
import com.zhangwan.app.read.model.local.DaoDbHelper;
import com.zhangwan.app.read.model.local.ReadSettingManager;
import com.zhangwan.app.read.presenter.ReadPresenter;
import com.zhangwan.app.read.presenter.contract.ReadContract;
import com.zhangwan.app.read.utils.BrightnessUtils;
import com.zhangwan.app.read.utils.Constant;
import com.zhangwan.app.read.utils.FileUtils;
import com.zhangwan.app.read.utils.LogUtils;
import com.zhangwan.app.read.utils.RxUtils;
import com.zhangwan.app.read.utils.ScreenUtils;
import com.zhangwan.app.read.utils.StringUtils;
import com.zhangwan.app.read.utils.SystemBarUtils;
import com.zhangwan.app.read.widget.page.PageLoader;
import com.zhangwan.app.read.widget.page.PageView;
import com.zhangwan.app.read.widget.page.TxtChapter;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.ui.read.ReadActivity;
import com.zhangwan.app.utils.ActivityManagerUtils;
import com.zhangwan.app.utils.DayShareUtils;
import com.zhangwan.app.utils.LoginDialogUtils;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.widget.dialog.LoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v4.view.ViewCompat.LAYER_TYPE_SOFTWARE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by newbiechen on 17-5-16.
 * https://github.com/newbiechen1024/NovelReader
 */

public class NovelReadActivity extends BaseMVPActivity<ReadContract.Presenter>
        implements ReadContract.View {
    private static final String TAG = "NovelReadActivity";
    public static final int REQUEST_MORE_SETTING = 1;
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private static final int WHAT_CATEGORY = 1;
    private static final int WHAT_CHAPTER = 2;


    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;
    /*************top_menu_view*******************/
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout mAblTopMenu;
    /***************content_view******************/
    @BindView(R.id.read_pv_page)
    PageView mPvPage;
    /***************bottom_menu_view***************************/
    @BindView(R.id.read_tv_page_tip)
    TextView mTvPageTip;

    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout mLlBottomMenu;
    @BindView(R.id.read_tv_pre_chapter)
    TextView mTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar mSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView mTvNextChapter;
    @BindView(R.id.read_tv_category)
    TextView mTvCategory;
    @BindView(R.id.read_tv_night_mode)
    TextView mTvNightMode;
    /*    @BindView(R.id.read_tv_download)
        TextView mTvDownload;*/
    @BindView(R.id.read_tv_setting)
    TextView mTvSetting;
    /***************left slide*******************************/
    @BindView(R.id.read_iv_category)
    ListView mLvCategory;

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
    @BindView(R.id.read_tv_top_share)
    ImageView ivShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /*****************view******************/
    private ReadSettingDialog mSettingDialog;
    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private CategoryAdapter mCategoryAdapter;
    private CollBookBean mCollBook;

    private boolean isPrvChapter = false;// 是否要请求章节内容的最后一页（每次请求内容前记得声明状态）

    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_CATEGORY:
                    mLvCategory.setSelection(mPageLoader.getChapterPos());
                    break;
                case WHAT_CHAPTER:
                    mPageLoader.openChapter(isPrvChapter);
                    break;
            }
        }
    };
    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return;

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                Log.d(TAG, "亮度模式改变");
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(NovelReadActivity.this)) {
                Log.d(TAG, "亮度模式为手动模式 值改变");
                BrightnessUtils.setBrightness(NovelReadActivity.this, BrightnessUtils.getScreenBrightness(NovelReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(NovelReadActivity.this)) {
                Log.d(TAG, "亮度模式为自动模式 值改变");
                BrightnessUtils.setDefaultBrightness(NovelReadActivity.this);
            } else {
                Log.d(TAG, "亮度调整 其他");
            }
        }
    };

    /***************params*****************/
    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;

    private String mBookId;

    private int clickType = 0; // 请求章节的类型，1为上一章，2为下一章，3为指定章节,4为上一章的最后一页
    private int chapterPostion = 0;// 指定章节的位置
    private int total = -1;//章节总数 上个界面传过来
    String bookId;//书本id
    String readId;//章节id
    String bookName;//书名
    String picUrl;//封面
    private String nextIdFee;
    private String nextIdIsPay;
    private String prevIdFee;
    private String prevIdIsPay;
    private String newFee;//  用户最新想看的章节解锁书币
    private String isCollect = "-1";//  用户是否已经加入书架
    private LoadingDialog dialog;
    private LoadingDialog.Builder loadBuilder;
    private List<BookChapterBean> bookChapters;
    private boolean isPay = false;//  章节是否需要付费
    private int balance; //  用户书币余额
    private Dialog collectDialog;
    private String shareUrl = "";
    private int state;
    private String intro = "";
    private boolean isCatalogue;//是否从目录进入阅读

    public static void startReadActivity(String bookId, String readId, int total, String picUrl, String bookName, boolean isCatalogue, Activity context) {
        if (SpUtil.getIsLogin(context)) {
            Intent intent = new Intent(context, NovelReadActivity.class);
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

    @Override
    protected int getContentId() {
        return R.layout.activity_novel_read;
    }

    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ActivityManagerUtils.getInstance().addActivity(this);
        Intent intent = getIntent();
        FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);// 每次请求新的章节内容之前记得把以前缓存在本地的全部章节删除掉
//        mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
        isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        isFullScreen = ReadSettingManager.getInstance().isFullScreen();
        total = intent.getIntExtra("total", -1);
        bookId = intent.getStringExtra("bookId");
        readId = intent.getStringExtra("readId");
        picUrl = intent.getStringExtra("picUrl");
        bookName = intent.getStringExtra("bookName");
        isCatalogue = intent.getBooleanExtra("isCatalogue", false);
        isPrvChapter = isCatalogue;
        mCollBook = new CollBookBean();
        mCollBook.set_id(bookId);
        mCollBook.setTitle(bookName);
//        mCollBook.setAuthor("50975b961db679b876000029");
//        mCollBook.setCover("50975b961db679b876000029");
//        mCollBook.setLastChapter("50975b961db679b876000029");
//        mCollBook.setLastRead("50975b961db679b876000029");
//        mCollBook.setShortIntro("50975b961db679b876000029");
//        mCollBook.setTitle("50975b961db679b876000029");
//        mCollBook.setUpdated("adhja");
//        mCollBook.setIsLocal(false);
        Log.d("NovelReadActivity", "initData: " + bookName);
        Log.d("NovelReadActivity", "initData: " + readId);
        mBookId = mCollBook.get_id();
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        //设置标题
        toolbar.setTitle(mCollBook.getTitle());
        //半透明化StatusBar
        SystemBarUtils.transparentStatusBar(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        loadBuilder = new LoadingDialog.Builder(NovelReadActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
        // 如果 API < 18 取消硬件加速
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mPvPage.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        //禁止滑动展示DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDlSlide.setFocusableInTouchMode(false);
        mSettingDialog = new ReadSettingDialog(this, mPageLoader);

        setUpAdapter();

        //夜间模式按钮的状态
        toggleNightMode();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness());
        }

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");

        //隐藏StatusBar
        mPvPage.post(
                () -> hideSystemBar()
        );

        //初始化TopMenu
        initTopMenu();

        //初始化BottomMenu
        initBottomMenu();
    }

    private void initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            mAblTopMenu.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);
        }
    }

    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = ScreenUtils.getNavigationBarHeight();
            mLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            mLlBottomMenu.setLayoutParams(params);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: " + mAblTopMenu.getMeasuredHeight());
    }

    private void toggleNightMode() {
        if (isNightMode) {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_morning));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_morning);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_night));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_night);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }

    private void setUpAdapter() {
        mCategoryAdapter = new CategoryAdapter();
        mLvCategory.setAdapter(mCategoryAdapter);
        mLvCategory.setFastScrollEnabled(true);
    }

    // 注册亮度观察者
    private void registerBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (!isRegistered) {
                    final ContentResolver cr = getContentResolver();
                    cr.unregisterContentObserver(mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                    isRegistered = true;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "register mBrightObserver error! " + throwable);
        }
    }

    //解注册
    private void unregisterBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "unregister BrightnessObserver error! " + throwable);
        }
    }

    @Override
    protected void initClick() {
        super.initClick();

        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos) {
                        mCategoryAdapter.setChapter(pos);
                    }

                    @Override
                    public void requestChapters(List<TxtChapter> requestChapters) {
                        if (isCatalogue) {
                            isCatalogue = false;
                            clickType = 3;
                            isPrvChapter = false;
                            chapterPostion = Integer.parseInt(readId);
                            FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                            mPageLoader.skipToChapter(chapterPostion);
                        } else {
                            mPresenter.loadChapter(mBookId, SpUtil.getToken(NovelReadActivity.this), requestChapters);
                            mHandler.sendEmptyMessage(WHAT_CATEGORY);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }

                    @Override
                    public void onCategoryFinish(List<TxtChapter> chapters) {
                        mCategoryAdapter.refreshItems(chapters);
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        mSbChapterProgress.setMax(Math.max(0, count - 1));
                        mSbChapterProgress.setProgress(0);
                        // 如果处于错误状态，那么就冻结使用
                        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                                || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                            mSbChapterProgress.setEnabled(false);
                        } else {
                            mSbChapterProgress.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageChange(int pos) {
                        mSbChapterProgress.post(
                                () -> mSbChapterProgress.setProgress(pos)
                        );
                    }
                }
        );

        mSbChapterProgress.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mLlBottomMenu.getVisibility() == VISIBLE) {
                            //显示标题
                            mTvPageTip.setText((progress + 1) + "/" + (mSbChapterProgress.getMax() + 1));
                            mTvPageTip.setVisibility(VISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //进行切换
                        int pagePos = mSbChapterProgress.getProgress();
                        if (pagePos != mPageLoader.getPagePos()) {
                            mPageLoader.skipToPage(pagePos);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }
                }
        );

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void prePage() {
                if (mPageLoader.getIsFirstPage()) {
                    clickType = 4;
                    isPrvChapter = true;
                    if (prevIdFee != null && prevIdFee.equals("0") && prevIdIsPay.equals("1")) {//先判断是否需要收费
                        FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                        if (mPageLoader.skipPreChapter()) {
                            mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                        }
                    } else if (prevIdFee != null) {
                        if (SpUtil.getIsCheckAutoNextChatper(NovelReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(prevIdFee)) {//余额小于自动解锁金额
                                showPay(0, prevIdFee, false);
                            } else {//直接扣除阅读币然后阅读
                                FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                                if (mPageLoader.skipPreChapter()) {
                                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                                }
                            }
                        } else {//未开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(prevIdFee)) {//余额小于自动解锁金额
                                showPay(0, prevIdFee, false);
                            } else {
                                showDeblocking();
                            }
                        }
                    }
                }
            }

            @Override
            public void nextPage() {
                if (mPageLoader.getIsLastPage()) {
                    clickType = 2;
                    isPrvChapter = false;
                    if (nextIdFee != null && nextIdFee.equals("0") && nextIdIsPay.equals("1")) {//先判断是否需要收费
                        FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                        if (mPageLoader.skipNextChapter()) {
                            mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                        }
                    } else if (nextIdFee != null) {
                        if (SpUtil.getIsCheckAutoNextChatper(NovelReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(nextIdFee)) {//余额小于自动解锁金额
                                showPay(0, nextIdFee, false);
                            } else {//直接扣除阅读币然后阅读
                                FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                                if (mPageLoader.skipNextChapter()) {
                                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                                }
                            }
                        } else {//未开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(nextIdFee)) {//余额小于自动解锁金额
                                showPay(0, nextIdFee, false);
                            } else {
                                showDeblocking();
                            }
                        }
                    }
                }
            }

            @Override
            public void cancel() {
            }
        });


        mTvCategory.setOnClickListener(
                (v) -> {
                    //移动到指定位置
                    if (mCategoryAdapter.getCount() > 0) {
                        mLvCategory.setSelection(mPageLoader.getChapterPos());
                    }
                    //切换菜单
                    toggleMenu(true);
                    //打开侧滑动栏
                    mDlSlide.openDrawer(Gravity.START);
                }
        );
        mTvSetting.setOnClickListener(
                (v) -> {
                    toggleMenu(false);
                    mSettingDialog.show();
                }
        );

        mTvPreChapter.setOnClickListener(  //上一章
                (v) -> {
                    clickType = 1;
                    isPrvChapter = false;
                    FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                    if (prevIdFee != null && prevIdFee.equals("0") && prevIdIsPay.equals("1")) {//先判断是否需要收费
                        if (mPageLoader.skipPreChapter()) {
                            mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                        }
                    } else if (prevIdFee != null) {
                        if (SpUtil.getIsCheckAutoNextChatper(NovelReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(prevIdFee)) {//余额小于自动解锁金额
                                showPay(0, prevIdFee, false);
                            } else {//直接扣除阅读币然后阅读
                                if (mPageLoader.skipPreChapter()) {
                                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                                }
                            }
                        } else {//未开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(prevIdFee)) {//余额小于自动解锁金额
                                showPay(0, prevIdFee, false);
                            } else {
                                showDeblocking();
                            }
                        }
                    }
                }
        );

        mTvNextChapter.setOnClickListener(  //下一章
                (v) -> {
                    clickType = 2;
                    isPrvChapter = false;
                    FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                    //先判断是否需要收费
                    if (nextIdFee != null && nextIdFee.equals("0") && nextIdIsPay.equals("1")) {
                        if (mPageLoader.skipNextChapter()) {
                            mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                        }
                    } else if (nextIdFee != null && !nextIdFee.equals("0") && nextIdIsPay.equals("0")) {
                        if (SpUtil.getIsCheckAutoNextChatper(NovelReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(nextIdFee)) {//余额小于自动解锁金额
                                showPay(0, nextIdFee, false);
                            } else {//直接扣除阅读币然后阅读
                                if (mPageLoader.skipNextChapter()) {
                                    mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                                }
                            }
                        } else {//未开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(nextIdFee)) {//余额小于自动解锁金额
                                showPay(0, nextIdFee, false);
                            } else {
                                showDeblocking();
                            }
                        }
                    }
                }
        );

        mTvNightMode.setOnClickListener(
                (v) -> {
                    if (isNightMode) {
                        isNightMode = false;
                    } else {
                        isNightMode = true;
                    }
                    mPageLoader.setNightMode(isNightMode);
                    toggleNightMode();
                }
        );


        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );
    }

    @OnClick({R.id.iv_read_deblocking_close, R.id.iv_read_recharge_close, R.id.btn_read_deblocking, R.id.read_tv_top_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read_deblocking: // 解锁
                isPay = true;//  弹出解锁界面证明章节需要付费
                FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                if (clickType == 1 || clickType == 4) {
                    if (mPageLoader.skipPreChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                } else if (clickType == 2) {
                    if (mPageLoader.skipNextChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                } else if (clickType == 3) {
                    mPageLoader.skipToChapter(chapterPostion);
                }
                rlReadLock.setVisibility(View.GONE);
                break;
            case R.id.iv_read_deblocking_close: // 关闭解锁界面
                rlReadLock.setVisibility(View.GONE);
                break;
            case R.id.iv_read_recharge_close: // 关闭充值界面
                rlRecharge.setVisibility(View.GONE);
                break;
            case R.id.read_tv_top_share: // 分享
                if (com.zhangwan.app.utils.StringUtils.isEmpty(shareUrl)) {
                    dialog.show();
                    getShare();
                } else {
                    showUMDialog(intro, state);
                }
                break;
        }
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
        return false;
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();

        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            //关闭
            mAblTopMenu.startAnimation(mTopOutAnim);
            mLlBottomMenu.startAnimation(mBottomOutAnim);
            mAblTopMenu.setVisibility(GONE);
            mLlBottomMenu.setVisibility(GONE);
            mTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            mAblTopMenu.setVisibility(View.VISIBLE);
            mLlBottomMenu.setVisibility(View.VISIBLE);
            mAblTopMenu.startAnimation(mTopInAnim);
            mLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        // 如果是已经收藏的，那么就从数据库中获取目录
        if (isCollected) {
            Disposable disposable = BookRepository.getInstance()
                    .getBookChaptersInRx(mBookId)
                    .compose(RxUtils::toSimpleSingle)
                    .subscribe(
                            (bookChapterBeen, throwable) -> {
                                // 设置 CollBook
                                mPageLoader.getCollBook().setBookChapters(bookChapterBeen);
                                // 刷新章节列表
                                mPageLoader.refreshChapterList();
                                // 如果是网络小说并被标记更新的，则从网络下载目录
                                if (mCollBook.isUpdate() && !mCollBook.isLocal()) {
                                    mPresenter.loadCategory(mBookId, total + "", SpUtil.getToken(NovelReadActivity.this));
                                }
                                LogUtils.e(throwable);
                            }
                    );
            addDisposable(disposable);
        } else {
            // 从网络中获取目录
            mPresenter.loadCategory(mBookId, total + "", SpUtil.getToken(NovelReadActivity.this));
        }
    }

    /***************************view************************************/
    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    //  小说章节目录
    @Override
    public void showCategory(List<BookChapterBean> bookChapters) {
        this.bookChapters = bookChapters;
        mPageLoader.getCollBook().setBookChapters(bookChapters);
        mPageLoader.refreshChapterList();
        mLvCategory.setOnItemClickListener(
                (parent, view, position, id) -> {
                    clickType = 3;
                    isPrvChapter = false;
                    chapterPostion = position;
                    FileUtils.deleteFile(Constant.BOOK_CACHE_PATH);
                    mDlSlide.closeDrawer(Gravity.START);
                    if (0 == bookChapters.get(position).getFee()) {//免费
                        mPageLoader.skipToChapter(position);
                    } else if (0 != bookChapters.get(position).getFee() && 0 == bookChapters.get(position).getBought()) {//未解锁
                        newFee = bookChapters.get(position).getFee() + "";
                        if (SpUtil.getIsCheckAutoNextChatper(NovelReadActivity.this)) {//开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(newFee)) {//余额小于自动解锁金额
                                showPay(position, bookChapters.get(position).getFee() + "", true);
                            } else {//直接扣除阅读币然后阅读
                                mPageLoader.skipToChapter(position);
                            }
                        } else {//未开启自动解锁
                            if (SpUtil.getUserBalacne(NovelReadActivity.this) < Integer.parseInt(newFee)) {//余额小于自动解锁金额
                                showPay(position, bookChapters.get(position).getFee() + "", true);
                            } else {
                                showDeblocking();
                            }
                        }
                    } else if (0 != bookChapters.get(position).getFee() && 1 == bookChapters.get(position).getBought()) {//已解锁
                        mPageLoader.skipToChapter(position);
                    }
                }
        );
        // 如果是目录更新的情况，那么就需要存储更新数据
        if (mCollBook.isUpdate() && isCollected) {
            BookRepository.getInstance()
                    .saveBookChaptersWithAsync(bookChapters);
        }
    }

    //  章节内容
    @Override
    public void finishChapter(ZhuiYueChapterInfoBean chapterInfoBean) {
        refreshUserInfo();
        isCollect = chapterInfoBean.getResult().getIsCollect();
        nextIdFee = chapterInfoBean.getResult().getNextIdFee();
        nextIdIsPay = chapterInfoBean.getResult().getNextIdIsPay();
        prevIdFee = chapterInfoBean.getResult().getPrevIdFee();
        prevIdIsPay = chapterInfoBean.getResult().getPrevIdIsPay();
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            Log.d("LuckyRead", "finishChapter: ");
            mHandler.sendEmptyMessage(WHAT_CHAPTER);
        }
        // 当完成章节的时候，刷新列表
        mCategoryAdapter.notifyDataSetChanged();
        isPay = false;
    }

    @Override
    public void errorChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            // 非全屏下才收缩，全屏下直接退出
            if (!ReadSettingManager.getInstance().isFullScreen()) {
                toggleMenu(true);
                return;
            }
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return;
        } else if (mDlSlide.isDrawerOpen(Gravity.START)) {
            mDlSlide.closeDrawer(Gravity.START);
            return;
        }

        switch (isCollect) {
            case "0":
                showCollectDialog();
                break;
            case "1":
                //设置为已收藏
                isCollected = true;
                //设置阅读时间
                mCollBook.setLastRead(StringUtils.
                        dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBrightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
        if (isCollected) {
            mPageLoader.saveRecord();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBrightObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        mHandler.removeMessages(WHAT_CATEGORY);
        mHandler.removeMessages(WHAT_CHAPTER);

        mPageLoader.closeBook();
        mPageLoader = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        SystemBarUtils.hideStableStatusBar(this);
        if (requestCode == REQUEST_MORE_SETTING) {
            boolean fullScreen = ReadSettingManager.getInstance().isFullScreen();
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen;
                // 刷新BottomMenu
                initBottomMenu();
            }

            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this);
            } else {
                SystemBarUtils.showStableNavBar(this);
            }
        }
    }

    /***************************************** 强大的分割线 **********************************************/
    //弹出解锁页面
    @SuppressLint("SetTextI18n")
    private void showDeblocking() {
        tvBookPrice.setText("账户余额：" + SpUtil.getUserBalacne(this) + "书币");
        if (clickType == 1 || clickType == 4) {
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
                    SpUtil.setIsCheckAutoNextChatper(NovelReadActivity.this, true);
                } else {
                    SpUtil.setIsCheckAutoNextChatper(NovelReadActivity.this, false);
                }
            }
        });
        rlReadLock.setVisibility(View.VISIBLE);
    }

    //弹出充值界面
    @SuppressLint("SetTextI18n")
    private void showPay(int position, String fee, boolean isCatalogue) {
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
            tvRechargePrice.setText("价格：" + bookChapters.get(position).getFee() + "书币");
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
                    SpUtil.setIsCheckAutoNextChatper(NovelReadActivity.this, true);
                } else {
                    SpUtil.setIsCheckAutoNextChatper(NovelReadActivity.this, false);
                }
            }
        });
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
                NovelReadActivity.this.finish();
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
                            //设置为已收藏
                            isCollected = true;
                            //设置阅读时间
                            mCollBook.setLastRead(StringUtils.
                                    dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));

                            BookRepository.getInstance()
                                    .saveCollBookWithAsync(mCollBook);
                            dialog.dismiss();
                            EventBusUtil.postStickyEvent(new BookShelfChangeEvent());
                            finish();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }

    //  获取充值列表
    private void getPayList(final RecyclerView recyclerView) {
        Subscription subscriber = MineApiService
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
                        PayCentreAdapter adapter = new PayCentreAdapter(NovelReadActivity.this, listBeans);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickLitener(new PayCentreAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                ToastUtil.showShort(NovelReadActivity.this, "po:" + position);
                            }
                        });
                    }

                    @Override
                    public void onError(String status, String message) {
                        ToastUtil.showShort(NovelReadActivity.this, message);

                    }
                }));
        RxTaskHelper.getInstance().addTask(this, subscriber);
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

    private void showUMDialog(String info, int state) {
        DayShareUtils utils = new DayShareUtils(NovelReadActivity.this, state);
        utils.shareUrl(NovelReadActivity.this, shareUrl,
                picUrl, bookName, info, 1, Integer.valueOf(bookId));
    }

    //  更新用户书币余额
    private void refreshUserInfo() {
        if (isPay) {
            if (clickType == 1) {
                balance = SpUtil.getUserBalacne(NovelReadActivity.this) - Integer.parseInt(prevIdFee);
            } else if (clickType == 2) {
                balance = SpUtil.getUserBalacne(NovelReadActivity.this) - Integer.parseInt(nextIdFee);
            } else if (clickType == 3) {
                balance = SpUtil.getUserBalacne(NovelReadActivity.this) - Integer.parseInt(newFee);
            }
            SpUtil.setUserBalacne(NovelReadActivity.this, balance);
            EventBusUtil.postStickyEvent(new RefreshUserInfoEvent());
        }
    }

}
