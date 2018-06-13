package com.zhangwan.app.adapter;

import android.content.Context;

import com.gxtc.commlibrary.utils.DateUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.FriendBean;

import java.util.List;

public class FriendAdapter extends BaseRecyclerAdapter<FriendBean.ListBean> {
    public FriendAdapter(Context context, List<FriendBean.ListBean> list) {
        super(context, list, R.layout.item_friend);
    }

    @Override
    public void bindData(ViewHolder holder, int position, FriendBean.ListBean listBean) {
        holder.setText(R.id.tv_item_friend_title, "成功邀请" + listBean.getNikename() + "加入");
        holder.setText(R.id.tv_item_friend_reward, "+" + listBean.getReward() + "书券");
        holder.setText(R.id.tv_item_friend_time, DateUtil.stampToDate(listBean.getCreateTime()));
    }
}
