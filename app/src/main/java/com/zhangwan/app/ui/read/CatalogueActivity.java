package com.zhangwan.app.ui.read;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.ChatperAdapter;
import com.zhangwan.app.adapter.ChatperMenuAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.ChatperBean;
import com.zhangwan.app.bean.ChatperMenuBean;
import com.zhangwan.app.presenter.CatalogueContract;
import com.zhangwan.app.presenter.CataloguePresenter;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.utils.ReadHistorySPUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laoshiren on 2018/3/30.
 * 目录
 * CatalogueActivity.startCatalogueActivity(this.getActivity(), "4046", "2416",2883077);
 */

public class CatalogueActivity extends BaseTitleActivity implements CatalogueContract.View {

    @BindView(R.id.rv_catalogue_1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.rv_catalogue_2)
    RecyclerView mRecyclerView2;
    ImageButton headRightImageButton;

    private final static String BOOK_ID = "book_id";
    private final static String TOTAL = "total";
    private final static String LAST_CHAPTER_ID = "chapter_id";
    private final static String PIC_URL = "pic_url";
    private final static String BOOK_NAME = "book_name";

    private CatalogueContract.Presenter mPresenter;
    private LinearLayoutManager linearLayoutManager;
    //章节
    private ChatperAdapter mAdapter1;
    private ChatperMenuAdapter mAdapter2;
    private boolean isShowChatperMenu;//是否显示章节筛选

    private int readId;//上次阅读章节ID
    private int chapterPos;//上次阅读章节
    private String bookId;
    private String total;
    private String picUrl;
    private String bookName;

    private ChatperBean chatperBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

    }

    @Override
    public void initData() {

        initHeadTitle();
        Intent intent = getIntent();
        bookId = intent.getStringExtra(BOOK_ID);
        total = intent.getStringExtra(TOTAL);
        readId = intent.getIntExtra(LAST_CHAPTER_ID, -1);
        picUrl = intent.getStringExtra(PIC_URL);
        bookName = intent.getStringExtra(BOOK_NAME);
        String token = "";
        if (SpUtil.getIsLogin(this)) {
            token = SpUtil.getToken(this);
        }
        initRecyclerView();

        new CataloguePresenter(this, token, bookId, total);
        mPresenter.getCalogue();
        mPresenter.getChatperMenu();

        mRecyclerView1.setVisibility(View.VISIBLE);
        mRecyclerView2.setVisibility(View.GONE);
    }

    private void initHeadTitle() {
        getBaseHeadView().showTitle(getString(R.string.title_catalogue));
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CatalogueActivity.this.finish();
            }
        });

        headRightImageButton = getBaseHeadView().getHeadRightImageButton();
        getBaseHeadView().showHeadRightImageButton(R.drawable.icon_catalogue_down, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowChatperMenu) {//显示章节菜单，则隐藏章节列表
                    isShowChatperMenu = false;
                    headRightImageButton.setImageResource(R.drawable.icon_catalogue_down);
                    mRecyclerView1.setVisibility(View.VISIBLE);
                    mRecyclerView2.setVisibility(View.GONE);

                } else {
                    isShowChatperMenu = true;
                    headRightImageButton.setImageResource(R.drawable.icon_catalogue_up);
                    mRecyclerView1.setVisibility(View.GONE);
                    mRecyclerView2.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void initRecyclerView() {
//
        linearLayoutManager = (new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyclerView1.setLayoutManager(linearLayoutManager);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void tokenOverdue() {
        ToastUtil.showShort(this, getString(R.string.text_token_over));
    }


    @Override
    public void showCalogue(final ChatperBean bean) {
        chatperBean = bean;
        final List<ChatperBean.CListBean> list = chatperBean.getCList();
        if (list != null && list.size() > 0) {

            if (ReadHistorySPUtils.getInstance().contains(bookId)) {
                readId = Integer.parseInt(ReadHistorySPUtils.getInstance().getString(bookId));
            }
            mAdapter1 = new ChatperAdapter(CatalogueActivity.this, readId, list, R.layout.item_chatper_type_1);
            mRecyclerView1.setAdapter(mAdapter1);
            //滚动到上次阅读的章节
            for (int i = 0; i < list.size(); i++) {
                if (readId == list.get(i).getId()) {
                    linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    chapterPos = i;
                }
            }

            mAdapter1.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    NovelReadActivity.startReadActivity(String.valueOf(bookId), position+"", Integer.valueOf(total), picUrl, bookName, true, CatalogueActivity.this);
                }
            });
        }
    }

    @Override
    public void showChatperMenu(final List<ChatperMenuBean> list) {
        mAdapter2 = new ChatperMenuAdapter(CatalogueActivity.this, chapterPos, list, R.layout.item_chatper_type_2);
        mRecyclerView2.setAdapter(mAdapter2);
        mAdapter2.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {//定位到章节那里
            @Override
            public void onItemClick(View v, int position) {


                isShowChatperMenu = false;
                headRightImageButton.setImageResource(R.drawable.icon_catalogue_down);
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
    public void setPresenter(CatalogueContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        getBaseEmptyView().showEmptyView();
    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void showNetError() {
        ToastUtil.showShort(this, getString(R.string.test_net_error));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chatperBean != null){
            showCalogue(chatperBean);
        }
    }

    /**
     * @param bookId   书籍id
     * @param readId   上次阅读章节id
     * @param total    章节总数
     * @param picUrl   封面
     * @param bookName 书名
     * @param activity
     */
    public static boolean startCatalogueActivity(String bookId, int readId, String total, String picUrl, String bookName, Activity activity) {

        Intent intent = new Intent(activity, CatalogueActivity.class);
        intent.putExtra(BOOK_ID, bookId);
        intent.putExtra(TOTAL, total);
        intent.putExtra(LAST_CHAPTER_ID, readId);
        intent.putExtra(PIC_URL, picUrl);
        intent.putExtra(BOOK_NAME, bookName);
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        activity.startActivity(intent);
        return true;
    }
}
