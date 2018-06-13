package com.zhangwan.app.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.bean.ShareWelfareBean;
import com.zhangwan.app.bean.event.RefreshUserInfoEvent;
import com.zhangwan.app.bean.event.ToFragmentEvent;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.ui.SearchActivity;
import com.zhangwan.app.ui.bookstore.FiltrBookActivity;
import com.zhangwan.app.ui.bookstore.free.BookStoreFreeActivity;
import com.zhangwan.app.ui.bookstore.hot.HotListActivity;
import com.zhangwan.app.ui.bookstore.like.GessLikeListActivity;
import com.zhangwan.app.ui.bookstore.news.NewListActivity;
import com.zhangwan.app.ui.recommend.FilterBookListActivity;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//  每日分享工具类
public class DayShareUtils {

    private Context context;
    private int state;

    public DayShareUtils(Context context, int state) {
        this.context = context;
        this.state = state;
    }

    // 带面板的链接分享
    public void shareUrl(final Activity activity, String url, String thumbUrl, String title, String info, int type, final int bookId) {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setCancelButtonText("取消");
        config.setTitleText("每天首次分享得书券");

        final UMWeb web = new UMWeb(url);
        final UMImage image = new UMImage(activity, thumbUrl);//网络图片
        web.setTitle(title);//标题
        web.setDescription(info);//描述
        web.setThumb(image);//缩略图

        ShareAction mShareAction;
        if (type == 1) {
            mShareAction = new ShareAction(activity)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                    .addButton("去书架", "go_bookshelf", "gobookshelf", "gobookshelf")
                    .addButton("书籍详情", "go_bookdetail", "bookdetail", "bookdetail")
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            switch (snsPlatform.mKeyword) {
                                case "go_bookshelf":
                                    EventBusUtil.post(new ToFragmentEvent(0));
                                    ActivityManagerUtils.getInstance().finishActivityclass(SearchActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(FiltrBookActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(FilterBookListActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(BookInfoActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(HotListActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(GessLikeListActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(BookStoreFreeActivity.class);
                                    ActivityManagerUtils.getInstance().finishActivityclass(NewListActivity.class);
                                    activity.finish();
                                    break;
                                case "go_bookdetail":
                                    BookInfoActivity.gotoBookInfoActivity(activity, bookId);
                                    break;
                                default:
                                    new ShareAction(activity)
                                            .setPlatform(share_media)
                                            .withMedia(web)
                                            .setCallback(shareListener)
                                            .share();
                                    break;
                            }
                        }
                    });
        } else {
            mShareAction = new ShareAction(activity)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            new ShareAction(activity)
                                    .setPlatform(share_media)
                                    .withMedia(web)
                                    .setCallback(shareListener)
                                    .share();
                        }
                    });
        }
        mShareAction.open(config);
    }

    //  分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("UMShareUtil", "onResult: " + "分享成功！" + state);
            if (state == 0) {
                getBookCoin();
            } else if (state == 1) {
                Toast.makeText(context, "分享成功！", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.d("UMShareUtil", "onError: " + "分享失败！");
            Toast.makeText(context, "分享失败！" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.d("UMShareUtil", "onCancel: " + "取消分享！");
            Toast.makeText(context, "取消分享！", Toast.LENGTH_SHORT).show();
        }
    };

    //  领取每日奖励
    private void getBookCoin() {
        MineApiService.getInstance().apiShareWelfare(SpUtil.getToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponseBean<ShareWelfareBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApiResponseBean<ShareWelfareBean> shareWelfareBeanApiResponseBean) {
                        if (shareWelfareBeanApiResponseBean.getCode().equals("200")) {
                            EventBusUtil.postStickyEvent(new RefreshUserInfoEvent());
                            Toast.makeText(context, "分享成功！奖励" + shareWelfareBeanApiResponseBean.getResult().getReward() + "书币！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
