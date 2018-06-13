package com.zhangwan.app.adapter;

import android.content.Context;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.HistoryBean;

import java.util.List;

//  每日分享
public class DayShareAdapter extends BaseRecyclerAdapter<HistoryBean.ListBean> {
    public DayShareAdapter(Context context, List<HistoryBean.ListBean> list) {
        super(context, list, R.layout.item_dayshare);
    }

    @Override
    public void bindData(ViewHolder holder, int position, HistoryBean.ListBean listBean) {
        ImageHelper.getInstance().loadImage(context, listBean.getBookPic(), holder.getImageView(R.id.iv_item_dayshare));
        holder.setText(R.id.tv_item_dayshare_name, listBean.getBookName());
    }
}
