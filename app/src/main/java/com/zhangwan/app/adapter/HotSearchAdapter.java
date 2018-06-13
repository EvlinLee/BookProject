package com.zhangwan.app.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.HotBean;
import com.zhangwan.app.bean.SearchBean;

import java.util.List;

/**
 * 搜索 - 热搜榜
 * Created by laoshiren on 2018/4/2.
 */

public class HotSearchAdapter extends BaseRecyclerAdapter<HotBean> {

    public HotSearchAdapter(Context context, List<HotBean> list) {
        super(context, list, R.layout.item_search_hot);
    }

    @SuppressLint("SetTextI18n")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void bindData(ViewHolder holder, int position, HotBean bean) {
        TextView tvNum = (TextView) holder.getView(R.id.tv_item_hot_num);
        tvNum.setText("" + (position + 1));
        if (0 == position) {
            tvNum.setBackground(getContext().getDrawable(R.drawable.shape_search_hot_1));
        } else if (1 == position) {
            tvNum.setBackground(getContext().getDrawable(R.drawable.shape_search_hot_2));
        } else if (2 == position) {
            tvNum.setBackground(getContext().getDrawable(R.drawable.shape_search_hot_3));
        } else {
            tvNum.setBackground(getContext().getDrawable(R.drawable.shape_search_hot_4));
        }

        holder.setText(R.id.tv_item_hot_name, bean.getBookName());

    }
}
