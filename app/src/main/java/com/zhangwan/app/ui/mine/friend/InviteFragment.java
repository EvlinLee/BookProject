package com.zhangwan.app.ui.mine.friend;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseLazyFragment;
import com.zhangwan.app.bean.InviteUrlBean;
import com.zhangwan.app.utils.UMShareUtil;
import com.zhangwan.app.widget.SquareRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 邀请好友
 * Created by Administrator on 2018/3/23 0023.
 */

public class InviteFragment extends BaseLazyFragment implements InviteContract.View {
    @BindView(R.id.sr_invite_wechat)
    SquareRelativeLayout srInviteWechat;
    @BindView(R.id.sr_invite_wczone)
    SquareRelativeLayout srInviteWczone;
    @BindView(R.id.sr_invite_two)
    SquareRelativeLayout srInviteTwo;
    @BindView(R.id.sr_invite_qq)
    SquareRelativeLayout srInviteQq;
    @BindView(R.id.sr_invite_qzone)
    SquareRelativeLayout srInviteQzone;
    @BindView(R.id.sr_invite_website)
    SquareRelativeLayout srInviteWebsite;

    private String url;

    private InviteContract.Presenter presenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void initData() {
        getBaseLoadingView().showLoading();
        new InvitePresenter(this);
        presenter.getFriend(SpUtil.getToken(getActivity()));
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        return view;
    }

    @OnClick({R.id.sr_invite_wechat, R.id.sr_invite_wczone, R.id.sr_invite_two, R.id.sr_invite_qq, R.id.sr_invite_qzone, R.id.sr_invite_website})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sr_invite_wechat:
                UMShareUtil.getInstance(getActivity()).shareWebSite(getActivity(), url, "全新正版小说抢先看！限时免费看到爽！", "追阅小说，一款“应有尽有”的阅读软件，我们在奇幻大陆等你！！！", SHARE_MEDIA.WEIXIN);
                break;
            case R.id.sr_invite_wczone:
                UMShareUtil.getInstance(getActivity()).shareWebSite(getActivity(), url, "全新正版小说抢先看！限时免费看到爽！", "追阅小说，一款“应有尽有”的阅读软件，我们在奇幻大陆等你！！！", SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.sr_invite_two:
                UMShareUtil.getInstance(getActivity()).shareWebSite(getActivity(), url, "全新正版小说抢先看！限时免费看到爽！", "追阅小说，一款“应有尽有”的阅读软件，我们在奇幻大陆等你！！！", SHARE_MEDIA.WEIXIN);
                break;
            case R.id.sr_invite_qq:
                UMShareUtil.getInstance(getActivity()).shareWebSite(getActivity(), url, "全新正版小说抢先看！限时免费看到爽！", "追阅小说，一款“应有尽有”的阅读软件，我们在奇幻大陆等你！！！", SHARE_MEDIA.QQ);
                break;
            case R.id.sr_invite_qzone:
                UMShareUtil.getInstance(getActivity()).shareWebSite(getActivity(), url, "全新正版小说抢先看！限时免费看到爽！", "追阅小说，一款“应有尽有”的阅读软件，我们在奇幻大陆等你！！！", SHARE_MEDIA.QZONE);
                break;
            case R.id.sr_invite_website:
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                assert manager != null;
                manager.setText(url);
                ToastUtil.showShort(getActivity(), "复制链接成功！");
                break;
        }
    }

    @Override
    public void showUrl(InviteUrlBean data) {
        url = data.getUrl();
        getBaseLoadingView().hideLoading();
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

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void setPresenter(InviteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
