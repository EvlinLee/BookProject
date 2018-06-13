package com.zhangwan.app.adapter;

import android.content.Context;

import com.gxtc.commlibrary.utils.DateUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.MessageBean;

import java.util.List;

//  我的消息
public class MessageAdapter extends BaseRecyclerAdapter<MessageBean> {
    public MessageAdapter(Context context, List<MessageBean> list) {
        super(context, list, R.layout.item_message);
    }

    @Override
    public void bindData(ViewHolder holder, int position, MessageBean messageBean) {
        holder.setText(R.id.tv_item_message_content, messageBean.getContent());
        holder.setText(R.id.tv_item_message_time, DateUtil.stampToDate(messageBean.getCreate_time() + ""));
    }
}
