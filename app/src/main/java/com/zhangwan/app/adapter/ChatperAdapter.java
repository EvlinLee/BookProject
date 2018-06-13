package com.zhangwan.app.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.ChatperBean;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/27.
 * 章节目录适配器
 */

public class ChatperAdapter extends BaseRecyclerAdapter<ChatperBean.CListBean> {

    private Context mContext;
    private int chapterId;

    public ChatperAdapter(Context context, int chapterId, List<ChatperBean.CListBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.mContext = context;
        this.chapterId = chapterId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindData(ViewHolder holder, int position, ChatperBean.CListBean bean) {


        //章节名
        TextView tvName = (TextView) holder.getView(R.id.tv_item_chatper_name);
        tvName.setText(bean.getTitle());


        ImageView ivNofreeblock = (ImageView) holder.getView(R.id.iv_item_chatper_nofree);//未解锁
        ImageView ivNofreeDeblock = (ImageView) holder.getView(R.id.iv_item_chatper_nofree_deblock);//已解锁
        TextView tvFree = (TextView) holder.getView(R.id.tv_item_chatper_free);//已解锁

        if (chapterId == bean.getId()) {
            tvName.setTextColor(mContext.getResources().getColor(R.color.read_chatper_textcolor));
        } else {
            tvName.setTextColor(mContext.getResources().getColor(R.color.read_chatper_textcolor_2));
        }
        if (0 == bean.getFee()) {//免费

            tvFree.setVisibility(View.VISIBLE);
            ivNofreeblock.setVisibility(View.GONE);
            ivNofreeDeblock.setVisibility(View.GONE);
        } else if (0 != bean.getFee() && 0 == bean.getBought()) {//未解锁
            tvFree.setVisibility(View.GONE);
            ivNofreeblock.setVisibility(View.VISIBLE);
            ivNofreeDeblock.setVisibility(View.GONE);

        } else if (0 != bean.getFee() && 1 == bean.getBought()) {//已解锁
            tvFree.setVisibility(View.GONE);
            ivNofreeblock.setVisibility(View.GONE);
            ivNofreeDeblock.setVisibility(View.VISIBLE);
        }

    }

}
