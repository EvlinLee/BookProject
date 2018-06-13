package com.zhangwan.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.BookTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class BookTypeAdapter extends BaseRecyclerAdapter<BookTypeBean> {

    public int choosePosition;
    public boolean isExtends;

    public BookTypeAdapter(Context context, List<BookTypeBean> list) {
        super(context, list, R.layout.item_filtr_booktype);
    }

    @Override
    public int getItemCount() {
        if (isExtends) {
            return super.getItemCount();
        } else {
            return 4;
        }
    }

    @Override
    public void bindData(ViewHolder holder, int position, BookTypeBean bookTypeBean) {
        TextView item = (TextView) holder.getView(R.id.tv_filtr_booktype_item);
        if (choosePosition == position) {
            item.setBackgroundResource(R.drawable.filter_textview_bg);
            item.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            item.setBackgroundResource(R.drawable.filter_textview_bg_normal);
            item.setTextColor(context.getResources().getColor(R.color.text_color_black_1));
        }
        holder.setText(R.id.tv_filtr_booktype_item, bookTypeBean.getName());
    }
}
