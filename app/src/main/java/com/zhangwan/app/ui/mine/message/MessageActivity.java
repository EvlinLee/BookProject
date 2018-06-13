package com.zhangwan.app.ui.mine.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zhangwan.app.R;
import com.zhangwan.app.adapter.MessageAdapter;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.MessageBean;
import com.zhangwan.app.read.NovelReadActivity;
import com.zhangwan.app.recyclerview.RecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zzg on 2018/3/26.
 */
public class MessageActivity extends BaseTitleActivity implements MessageContract.View {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.sf_message)
    SwipeRefreshLayout sfMessage;

    private MessageContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mesage);
    }

    @Override
    public void initData() {
        getBaseHeadView().showTitle("我的消息").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        new MessagePresenter(this);
        presenter.getMessageListData(1, 10);
    }

    @Override
    public void showData(final List<MessageBean> datas) {
        MessageAdapter adapter = new MessageAdapter(this, datas);
        rvMessage.setAdapter(adapter);
        adapter.setOnReItemOnClickListener(new BaseRecyclerAdapter.OnReItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NovelReadActivity.startReadActivity(datas.get(position).getBookId() + "", datas.get(position).getChapter_id() + "", datas.get(position).getTotal(),
                        datas.get(position).getPic(), datas.get(position).getBook_name(),false, MessageActivity.this);
            }
        });
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
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
