package com.zhangwan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.HistoryBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 阅读记录
 * Created by Administrator on 2018/3/27 0027.
 */

public class HistoryAdapter extends BaseRecyclerAdapter<HistoryBean.ListBean> {
    View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public HistoryAdapter(Context context, List<HistoryBean.ListBean> list) {
        super(context, list, R.layout.item_history);
    }

    @Override
    public void bindData(final ViewHolder holder, int position, final HistoryBean.ListBean listBean) {
        ImageHelper.getInstance().loadImage(context, listBean.getBookPic(), (ImageView) holder.getView(R.id.iv_item_history));
        holder.setText(R.id.tv_item_history_name, listBean.getBookName());
        holder.setText(R.id.tv_item_history_look, "上次看到：" + listBean.getNewChapter());
        holder.setText(R.id.tv_item_history_new, "更新至" + listBean.getTotalNum() + "章");
        holder.setOnClick(R.id.tv_item_history_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.onDelete(holder.getPosition(), listBean.getId());
            }
        });
        holder.getView(R.id.tv_item_history_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    view.setTag(listBean);
                    mOnClickListener.onClick(view);
                }
            }
        });
        holder.setOnClick(R.id.rl_item_history, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(context)) {
                    BookInfoActivity.gotoBookInfoActivity(context, Integer.parseInt(listBean.getBookId()));
                } else {
                    ToastUtil.showShort(context, context.getString(R.string.empty_net_error));
                }
            }
        });
    }

    private OnDeleteListenter listenter;

    public interface OnDeleteListenter {
        void onDelete(int position, String id);
    }

    public void setOnDeleteListenter(OnDeleteListenter listenter) {
        this.listenter = listenter;
    }
}
