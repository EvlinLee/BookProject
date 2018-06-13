package com.zhangwan.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.BookBean;

import java.util.List;

/**
 * Created by zzg on 2018/3/23.
 */

public class LimitListAdater extends BaseRecyclerAdapter<BookBean> {

    public LimitListAdater(Context context, List<BookBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, BookBean bean) {
        holder.setText(R.id.name,bean.bookName);
        if(!TextUtils.isEmpty(bean.readNum)){
            holder.getView(R.id.read_count).setVisibility(View.VISIBLE);
            holder.setText(R.id.read_count,bean.readNum + "人阅读");
        }else {
            holder.getView(R.id.read_count).setVisibility(View.GONE);
        }
        ImageHelper.getInstance().loadImage(context,bean.bookPic,(ImageView) holder.getView(R.id.iv_bookimg));
    }
}
