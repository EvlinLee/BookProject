package com.zhangwan.app.adapter;

import android.content.Context;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by laoshiren on 2018/4/2.
 * 搜索历史
 */
public class SearchHistoryAdapter extends BaseRecyclerAdapter<String> {
    public SearchHistoryAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void bindData(ViewHolder holder, int position, String str) {
        holder.setText(R.id.tv_search_histoty, str);

    }
}
