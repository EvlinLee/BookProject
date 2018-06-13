package com.zhangwan.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.bean.BookTypeBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class BookTypeListAdater extends BaseRecyclerAdapter<BookTypeBean> {

    public BookTypeListAdater(Context context, List<BookTypeBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, BookTypeBean bean) {
        holder.setText(R.id.name, bean.getName());
        if ((position % 2) == 0 && (position % 2) == 0) {
            holder.getView(R.id.name).setBackgroundResource(R.drawable.textview_main_recommend_one);
        } else if ((position % 2) != 0 && (position % 3) == 0) {
            holder.getView(R.id.name).setBackgroundResource(R.drawable.textview_main_recommend_two);
        }else if ((position % 2) == 0 && (position % 4) == 0){
            holder.getView(R.id.name).setBackgroundResource(R.drawable.textview_main_recommend_three);
        }else {
            holder.getView(R.id.name).setBackgroundResource(R.drawable.textview_main_recommend_four);
        }
    }
}
