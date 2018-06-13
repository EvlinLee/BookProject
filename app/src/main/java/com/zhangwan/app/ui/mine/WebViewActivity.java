package com.zhangwan.app.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.event.RefreshUserInfoEvent;
import com.zhangwan.app.bean.event.ToFragmentEvent;
import com.zhangwan.app.ui.mine.friend.FriendActivity;
import com.zhangwan.app.ui.mine.share.DayShareActivity;
import com.zhangwan.app.utils.ADFilterTool;
import com.zhangwan.app.utils.LoginDialogUtils;
import com.zhangwan.app.utils.SystemTools;


import butterknife.BindView;

/**
 * Created by laoshiren on 2018/3/31.
 */
public class WebViewActivity extends BaseTitleActivity {

    @BindView(R.id.webView)
    WebView webView;

    String mUrl;

    public static void startActivity(Context context, String url, boolean refresh) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("web_url", url);
        intent.putExtra("refresh", refresh);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_common_web_view);
        initData();
        initWebView();
    }

    @Override
    public void initData() {

        final Intent intent = getIntent();
        mUrl = intent.getStringExtra("web_url");
        final boolean refresh = intent.getBooleanExtra("refresh", false);

        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (webView.canGoBack()) {
//                    webView.goBack();
//                    return;
//                }
                if (refresh) {
                    EventBusUtil.postStickyEvent(new RefreshUserInfoEvent());
                    WebViewActivity.this.finish();
                } else {
                    WebViewActivity.this.finish();
                }
            }
        });

    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        webView.loadUrl(mUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        webView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true);//开启 database storage API 功能
        webView.getSettings().setAppCacheEnabled(true);//开启 Application Caches 功能
        webView.getSettings().setBlockNetworkImage(true);

        webView.addJavascriptInterface(this, "openQQ");
        webView.addJavascriptInterface(this, "openQQGroup");
        webView.addJavascriptInterface(this, "dayShare");
        webView.addJavascriptInterface(this, "dayRead");
        webView.addJavascriptInterface(this, "writeComment");
        webView.addJavascriptInterface(this, "toInvite");
        webView.addJavascriptInterface(this, "toInfo");
        webView.addJavascriptInterface(this, "toBookShelf");

        getBaseLoadingView().showLoading();
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getBaseHeadView().showTitle(title);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getBaseLoadingView().hideLoading();
                webView.getSettings().setBlockNetworkImage(false);
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == WebViewClient.ERROR_CONNECT) {
                    getBaseLoadingView().hideLoading();
                    showNetError();
                }
            }

            /**
             * 广告拦截
             * @param view
             * @param url
             * @return
             */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                url = url.toLowerCase();
                if (!url.contains(mUrl)) {
                    if (!ADFilterTool.hasAd(WebViewActivity.this, url)) {
                        return super.shouldInterceptRequest(view, url);
                    } else {
                        return new WebResourceResponse(null, null, null);
                    }
                } else {
                    return super.shouldInterceptRequest(view, url);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                return super.shouldOverrideUrlLoading(view, url);
            }

        });

    }

    /**
     * 初始网络错误，点击重新加载
     */
    public void showNetError() {
        getBaseEmptyView().showNetWorkViewReload(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void goToQQ(String qq) {
        final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
        boolean qqClientAvailable = SystemTools.isQQClientAvailable(this);
        if (qqClientAvailable)
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
        else ToastUtil.showShort(this, "请先安装QQ客户端");
    }

    @JavascriptInterface
    public void goToQQGroup(String qqgroup) {
        if (joinQQGroup(qqgroup)) {
            joinQQGroup(qqgroup);
        } else {
            ToastUtil.showShort(this, "请先安装QQ客户端");
        }
    }

    @JavascriptInterface
    public void goShare() {
        if (SpUtil.getIsLogin(this)) {
            startActivity(DayShareActivity.class);
        } else {
            LoginDialogUtils utils = new LoginDialogUtils(this);
            utils.loginType(this);
        }
    }

    @JavascriptInterface
    public void goRead() {
        EventBusUtil.post(new ToFragmentEvent(0));
        finish();
    }

    @JavascriptInterface
    public void goComment() {
        ToastUtil.showShort(this, "评论！");
    }

    @JavascriptInterface
    public void goInvite() {
        startActivity(FriendActivity.class);
    }

    @JavascriptInterface
    public void goInfo() {
        if (SpUtil.getIsLogin(this)) {
            startActivity(UserInfoActivity.class);
        } else {
            LoginDialogUtils utils = new LoginDialogUtils(this);
            utils.loginType(this);
        }
    }

    @JavascriptInterface
    public void goBookShelf() {
        EventBusUtil.post(new ToFragmentEvent(0));
        finish();
    }

    private boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }
}
