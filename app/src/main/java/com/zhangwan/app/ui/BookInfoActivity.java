package com.zhangwan.app.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.umeng.socialize.UMShareAPI;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.CommentAdapter;
import com.zhangwan.app.adapter.RandLikeGridAdapter;
import com.zhangwan.app.adapter.StoreRecommendAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.BookInfoBean;
import com.zhangwan.app.bean.CollectBookBean;
import com.zhangwan.app.bean.DayShareBean;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.bean.event.BookShelfChangeEvent;
import com.zhangwan.app.bean.event.ToFragmentEvent;
import com.zhangwan.app.http.service.BookStoreApiService;
import com.zhangwan.app.presenter.BookInfoContract;
import com.zhangwan.app.presenter.BookInfoPresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.ui.bookstore.FiltrBookActivity;
import com.zhangwan.app.ui.bookstore.comment.AllCommentActivity;
import com.zhangwan.app.ui.bookstore.comment.WriteCommentActivity;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListActivity;
import com.zhangwan.app.ui.bookstore.like.GessLikeListActivity;
import com.zhangwan.app.ui.bookstore.news.NewListActivity;
import com.zhangwan.app.ui.read.CatalogueActivity;
import com.zhangwan.app.ui.recommend.FilterBookListActivity;
import com.zhangwan.app.utils.ActivityManagerUtils;
import com.zhangwan.app.utils.CommonUtils;
import com.zhangwan.app.utils.DayShareUtils;
import com.zhangwan.app.utils.LoginDialogUtils;
import com.zhangwan.app.utils.RxTaskHelper;
import com.zhangwan.app.utils.StatusBarUtils;
import com.zhangwan.app.utils.StringUtils;
import com.zhangwan.app.utils.UMShareUtil;
import com.zhangwan.app.widget.ExpandableTextView;
import com.zhangwan.app.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 书籍详情
 * Created by Administrator on 2018/3/24 0024.
 */

public class BookInfoActivity extends BaseTitleActivity implements BookInfoContract.View, NestedScrollView.OnScrollChangeListener {
    @BindView(R.id.iv_bookdetail_img)
    ImageView ivBookdetailImg;
    @BindView(R.id.tv_bookdetail_name)
    TextView tvBookdetailName;
    @BindView(R.id.tv_bookdetail_auto)
    TextView tvBookdetailAuto;
    @BindView(R.id.tv_bookdetail_autoname)
    TextView tvBookdetailAutoname;
    @BindView(R.id.tv_bookdetail_state1)
    TextView tvBookdetailState1;
    @BindView(R.id.tv_bookdetail_state)
    TextView tvBookdetailState;
    @BindView(R.id.tv_bookdetail_type2)
    TextView tvBookdetailType2;
    @BindView(R.id.tv_bookdetail_intro)
    TextView tvBookdetailIntro;
    @BindView(R.id.iv_bookdetail_catalogimg)
    ImageView ivBookdetailCatalogimg;
    @BindView(R.id.tv_bookdetail_catalog)
    TextView tvBookdetailCatalog;
    @BindView(R.id.tv_bookdetail_new)
    TextView tvBookdetailNew;
    @BindView(R.id.tv_bookdetail_time)
    TextView tvBookdetailTime;
    @BindView(R.id.tv_bookdetail_comment)
    TextView tvBookdetailComment;
    @BindView(R.id.tv_bookdetail_gocomment)
    TextView tvBookdetailGocomment;
    @BindView(R.id.rv_bookdetail_comment)
    RecyclerView rvBookdetailComment;
    @BindView(R.id.tv_bookinfo_collect)
    TextView tvBookinfoCollect;
    @BindView(R.id.tv_bookinfo_read)
    TextView tvBookinfoRead;
    @BindView(R.id.tv_bookinfo_all)
    TextView tvBooinfoAll;
    @BindView(R.id.ll_bookinfo_menu)
    LinearLayout llBookMenu;
    @BindView(R.id.ll_bookinfo_dot)
    LinearLayout llBookinfoDot;
    @BindView(R.id.vp_bookinfo)
    ViewPager vpBookinfo;
    @BindView(R.id.expandable_text)
    ExpandableTextView expandableTextView;
    @BindView(R.id.id_expand_textview)
    ImageView idExpandTextview;
    @BindView(R.id.ns_bookinfo)
    NestedScrollView nsBookinfo;
    @BindView(R.id.ib_bookinfo_back)
    ImageButton ibBookinfoBack;
    @BindView(R.id.tv_bookinfo_title)
    TextView tvBookinfoTitle;
    @BindView(R.id.ib_bookinfo_share)
    ImageButton ibBookinfoShare;
    @BindView(R.id.tv_bookinfo_shelf)
    TextView tvBooinfoShelf;
    @BindView(R.id.rl_bookinfo_top)
    RelativeLayout rlBookinfoTop;
    @BindView(R.id.ll_bookinfo_one)
    LinearLayout llBookinfoOne;
    @BindView(R.id.iv_bookinfo_topbg)
    ImageView ivBookinfoBg;
    @BindView(R.id.tv_bookdetail_readnum)
    TextView tvReadNum;

    private BookInfoContract.Presenter presenter;
    private int bookId;
    private String commentNum;//评论数量
    private String bookName;//书名
    private String author;//作者
    private String pic;//封面
    private String intro;//简介
    private String totle;
    private int readId;

    private String shareUrl = "";

    //每页展示的主题个数
    private final int pageSize = 4;
    //当前页索引
    private int currentIndex;

    private LoadingDialog dialog;
    private LoadingDialog.Builder loadBuilder;
    private int state;

    public static void gotoBookInfoActivity(Context context, int bookId) {
        Intent intent = new Intent(context, BookInfoActivity.class);
        intent.putExtra("bookId", bookId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);
    }

    @Override
    public void initData() {
        // 沉浸式状态栏
        // toolbar 的高
        int toolbarHeight = rlBookinfoTop.getLayoutParams().height;
        final int headerBgHeight = toolbarHeight + getStatusBarHeight();
        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams params = ivBookinfoBg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) ivBookinfoBg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
        StatusBarUtils.setTranslucentImageHeader(this, 0, rlBookinfoTop);
        // 上移背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivBookinfoBg.getLayoutParams();
        layoutParams.setMargins(0, -getStatusBarHeight(), 0, 0);

        loadBuilder = new LoadingDialog.Builder(BookInfoActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        nsBookinfo.setOnScrollChangeListener(this);

        bookId = getIntent().getIntExtra("bookId", 0);
        rvBookdetailComment.setLayoutManager(new LinearLayoutManager(this));
        rvBookdetailComment.setNestedScrollingEnabled(false);
        Log.d("BookInfoActivity", "initData: " + bookId);
        new BookInfoPresenter(this);
        hideContentLayout();
        presenter.getBookInfo(SpUtil.getToken(this), bookId);
        presenter.getLikeBook(6, 1);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showBookInfo(BookInfoBean data) {
        showContentLayout();
        totle = data.getTotle();
        readId = Integer.parseInt(data.getReadId());
        commentNum = data.getCommentNum();
        bookName = data.getBookName();
        author = data.getAuthor();
        pic = data.getBookPic();
        intro = data.getIntro();
        setImgHeaderBg(pic);
        ImageHelper.getInstance().loadImage(this, pic, ivBookdetailImg);
        tvBookdetailName.setText(bookName);
        tvBookdetailAutoname.setText(author);
        tvBookdetailType2.setText(data.getType());
        expandableTextView.setText(intro);
        tvReadNum.setText("已有" + data.getReadNum() + "书友阅读");
        tvBookdetailNew.setText(data.getNewChapter().getTitle());
        tvBookdetailComment.setText(data.getCommentNum() + "人评论");
        if (data.getCommentNum().equals("0")) {
            tvBooinfoAll.setText("暂无评论");
            tvBooinfoAll.setClickable(false);
        }
        if (data.getStatus().equals("1")) {
            tvBookdetailState.setText("已完结");
        } else {
            tvBookdetailState.setText("连载中");
        }
        CommentAdapter adapter = new CommentAdapter(this, data.getCommentVos(), this);
        rvBookdetailComment.setAdapter(adapter);
        if (data.getIsCollect().equals("1")) {
            tvBookinfoCollect.setText("已在书架");
            tvBookinfoCollect.setTextColor(getResources().getColor(R.color.text_color_black_3));
            tvBookinfoCollect.setClickable(false);
        }
        tvBookdetailTime.setText(CommonUtils.millisToString(data.getNewChapter().getCreateTime()));
    }

    @Override
    public void showLike(final List<LikeBean> data) {
        currentIndex = 0;
        llBookinfoDot.removeAllViewsInLayout();
        //需要的页面数
        int pageCount = (int) Math.ceil(data.size() * 1.0 / pageSize);
        final List<View> viewList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            GridView gridView = (GridView) getLayoutInflater().inflate(R.layout.layout_grid_view, vpBookinfo, false);
            gridView.setNumColumns(pageSize);
            gridView.setAdapter(new RandLikeGridAdapter(data, i, pageSize, this));
            viewList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentIndex * pageSize;
                    Intent intent = new Intent(BookInfoActivity.this, BookInfoActivity.class);
                    intent.putExtra("bookId", data.get(pos).getBookId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        vpBookinfo.setAdapter(new StoreRecommendAdapter(viewList));

        for (int i = 0; i < pageCount; i++) {
            llBookinfoDot.addView(getLayoutInflater().inflate(R.layout.view_dot, null));
        }
        //使第一个小圆点呈选中状态
        llBookinfoDot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
        vpBookinfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                llBookinfoDot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                llBookinfoDot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_select);
                currentIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void setPresenter(BookInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoad() {
        getBaseLoadingView().showLoading();
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

    @OnClick({R.id.tv_bookinfo_collect, R.id.tv_bookinfo_read, R.id.tv_bookinfo_all, R.id.ll_bookinfo_menu, R.id.tv_bookdetail_gocomment
            , R.id.ib_bookinfo_back, R.id.ib_bookinfo_share, R.id.tv_bookinfo_shelf, R.id.tv_bookdetail_new})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_bookinfo_collect:
                if (SpUtil.getIsLogin(this)) {
                    collect();
                } else {
                    LoginDialogUtils utils = new LoginDialogUtils(this);
                    utils.loginType(this);
                }
                break;
            case R.id.tv_bookinfo_read:
                NovelReadActivity.startReadActivity(String.valueOf(bookId), String.valueOf(readId), Integer.valueOf(totle), pic, bookName, false, this);
                break;
            case R.id.tv_bookinfo_all:
                intent = new Intent(this, AllCommentActivity.class);
                intent.putExtra("bookId", bookId);
                intent.putExtra("commentNum", commentNum);
                intent.putExtra("bookName", bookName);
                intent.putExtra("author", author);
                intent.putExtra("pic", pic);
                startActivity(intent);
                break;
            case R.id.ll_bookinfo_menu:
                CatalogueActivity.startCatalogueActivity(bookId + "", readId, totle, pic, bookName, this);
                break;
            case R.id.tv_bookdetail_gocomment:
                if (SpUtil.getIsLogin(this)) {
                    intent = new Intent(this, WriteCommentActivity.class);
                    intent.putExtra("bookId", bookId);
                    intent.putExtra("bookName", bookName);
                    intent.putExtra("author", author);
                    intent.putExtra("pic", pic);
                    startActivity(intent);
                } else {
                    LoginDialogUtils utils = new LoginDialogUtils(this);
                    utils.loginType(this);
                }
                break;
            case R.id.ib_bookinfo_back:
                finish();
                break;
            case R.id.tv_bookinfo_shelf:
                EventBusUtil.post(new ToFragmentEvent(0));
                ActivityManagerUtils.getInstance().finishActivityclass(SearchActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(FiltrBookActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(FilterBookListActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(NovelReadActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(HotListActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(GessLikeListActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(BookStoreFreeActivity.class);
                ActivityManagerUtils.getInstance().finishActivityclass(NewListActivity.class);
                finish();
                break;
            case R.id.ib_bookinfo_share:
                if (StringUtils.isEmpty(shareUrl)) {
                    dialog = loadBuilder.create();
                    dialog.show();
                    getShare();
                } else {
                    DayShareUtils utils = new DayShareUtils(this, state);
                    utils.shareUrl(BookInfoActivity.this, shareUrl, pic, bookName, intro, 2, 0);
                }
                break;
            case R.id.tv_bookdetail_new:
                NovelReadActivity.startReadActivity(String.valueOf(bookId), String.valueOf(readId), Integer.valueOf(totle), pic, bookName, false, this);
                break;
        }
    }

    //  加入书架
    private void collect() {
        Subscription subscriber = BookStoreApiService.getInstance().apiCollectBook(SpUtil.getToken(this), bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectBookBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CollectBookBean collectBookBean) {
                        if (collectBookBean.getCode().equals("200")) {
                            tvBookinfoCollect.setText("已在书架");
                            tvBookinfoCollect.setTextColor(getResources().getColor(R.color.text_color_black_3));
                            tvBookinfoCollect.setClickable(false);
                            EventBusUtil.postStickyEvent(new BookShelfChangeEvent());
                        }
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }

    //  获取分享链接
    private void getShare() {
        Subscription subscriber = BookStoreApiService.getInstance().apiDayShare(SpUtil.getToken(this), bookId)
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
                        dialog.dismiss();
                        DayShareUtils utils = new DayShareUtils(BookInfoActivity.this, state);
                        utils.shareUrl(BookInfoActivity.this, shareUrl, pic, bookName, intro, 2, 0);
                    }
                });
        RxTaskHelper.getInstance().addTask(this, subscriber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    // 标题栏渐变
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY <= 0) {  //设置标题的背景颜色
            rlBookinfoTop.setBackgroundColor(Color.argb((int) 0, 61, 201, 123));
            StatusBarUtils.setStatusBarColor(this, Color.argb((int) 0, 61, 201, 123));
        } else if (scrollY <= llBookinfoOne.getHeight()) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) scrollY / llBookinfoOne.getHeight();
            float alpha = (255 * scale);
            rlBookinfoTop.setBackgroundColor(Color.argb((int) alpha, 61, 201, 123));
            StatusBarUtils.setStatusBarColor(this, Color.argb((int) alpha, 61, 201, 123));
        } else {  //滑动到banner下面设置普通颜色
            rlBookinfoTop.setBackgroundColor(Color.argb((int) 255, 61, 201, 123));
            StatusBarUtils.setStatusBarColor(this, Color.argb((int) 255, 61, 201, 123));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxTaskHelper.getInstance().cancelTask(this);
    }

    // 高斯模糊背景
    private void setImgHeaderBg(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {

            // 高斯模糊背景 原来 参数：12,5  23,4
            Glide.with(this).load(imgUrl)
                    .error(R.drawable.stackblur_default)
                    .bitmapTransform(new BlurTransformation(this, 23, 4)).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    ivBookinfoBg.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(ivBookinfoBg);
        }
    }
}
