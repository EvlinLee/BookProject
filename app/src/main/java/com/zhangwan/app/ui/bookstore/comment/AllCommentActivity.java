package com.zhangwan.app.ui.bookstore.comment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.adapter.AllCommentAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.AllCommentBean;
import com.zhangwan.app.recyclerview.RecyclerView;
import com.zhangwan.app.utils.LoginDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//全部评论
public class AllCommentActivity extends BaseTitleActivity implements AllCommentContract.View {
    @BindView(R.id.iv_all_pic)
    ImageView ivAllPic;
    @BindView(R.id.tv_all_bookname)
    TextView tvAllBookname;
    @BindView(R.id.tv_all_author)
    TextView tvAllAuthor;
    @BindView(R.id.tv_all_write)
    TextView tvAllWrite;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.rv_all_comment)
    RecyclerView rvAllComment;

    private int bookId;
    private String commentNum;//评论数量
    private String bookName;//书名
    private String author;//作者
    private String pic;//封面
    private AllCommentAdapter adapter;
    private int start = 0;

    private AllCommentContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcomment);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData() {
        getBaseHeadView().showTitle("全部评论").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bookId = getIntent().getIntExtra("bookId", 0);
        commentNum = getIntent().getStringExtra("commentNum");
        bookName = getIntent().getStringExtra("bookName");
        author = getIntent().getStringExtra("author");
        pic = getIntent().getStringExtra("pic");

        ImageHelper.getInstance().loadImage(this, pic, ivAllPic);
        tvAllBookname.setText(bookName);
        tvAllAuthor.setText(author);
        tvAllNum.setText(commentNum + "人评论");

        rvAllComment.setLoadMoreView(R.layout.model_footview_loadmore);
        rvAllComment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllCommentAdapter(this, new ArrayList<AllCommentBean>());
        rvAllComment.setAdapter(adapter);

        new AllCommentPresenter(this);
        presenter.getComment(SpUtil.getToken(AllCommentActivity.this), bookId, start, 10);
    }

    @Override
    public void initListener() {
        rvAllComment.setOnLoadMoreListener(new com.zhangwan.app.recyclerview.wrapper.LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                start = start + 1;
                presenter.getComment(SpUtil.getToken(AllCommentActivity.this), bookId, start, 10);
            }
        });
    }

    @OnClick(R.id.tv_all_write)
    public void onViewClicked() {
        if (SpUtil.getIsLogin(this)) {
            Intent intent = new Intent(this, WriteCommentActivity.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("bookName", bookName);
            intent.putExtra("author", author);
            intent.putExtra("pic", pic);
            startActivity(intent);
        } else {
            LoginDialogUtils utils = new LoginDialogUtils(this);
            utils.loginType(this);
        }
    }

    @Override
    public void showComment(List<AllCommentBean> data) {
        if (start == 0) {
            rvAllComment.notifyChangeData(data, adapter);
        } else {
            rvAllComment.changeData(data, adapter);
        }

        if (adapter.getList().size() >= Integer.parseInt(commentNum)) {
            rvAllComment.loadFinish();
        }
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
    public void setPresenter(AllCommentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
