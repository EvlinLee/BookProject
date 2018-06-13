package com.zhangwan.app.adapter;

import android.content.Context;
import android.view.View;

import com.gxtc.commlibrary.utils.DateUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class TicketAdater extends BaseRecyclerAdapter<String> {

    View.OnClickListener mListener;

    public void setListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public TicketAdater(Context context, List<String> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, String bean) {
        holder.setText(R.id.news_ticket,bean + position);
        holder.setText(R.id.valite_time, "有效期"+DateUtil.stampToDate(String.valueOf(System.currentTimeMillis()),"yyyy-MM-dd")+"至"
                +DateUtil.stampToDate(String.valueOf(2*System.currentTimeMillis()),"yyyy-MM-dd"));
        if(position % 5 == 0){
            holder.getView(R.id.line_layout) .setBackgroundResource(R.drawable.bg_get);
            holder.setText(R.id.cash,"￥8888" );
            holder.setText(R.id.ticket_status,"立即领取" );
        }else {
            holder.setText(R.id.cash,"￥8888" );
            holder.setText(R.id.ticket_status,"已领取" );
            holder.getView(R.id.line_layout) .setBackgroundResource(R.drawable.bg_got);
        }

    }
}
