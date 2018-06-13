package com.zhangwan.app.adapter;

import android.content.Context;
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

public class NewListAdater extends BaseRecyclerAdapter<BookBean> {

    View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public NewListAdater(Context context, List<BookBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }


    @Override
    public void bindData(ViewHolder holder, int position, final BookBean bean) {
        holder.setText(R.id.title,bean.bookName);
        holder.setText(R.id.author,bean.author);
        holder.setText(R.id.content,bean.intro);
        ImageHelper.getInstance().loadImage(context,bean.bookPic,(ImageView) holder.getView(R.id.iv_bookimg));
        holder.getView(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(mOnClickListener != null){
                        view.setTag(bean);
                        mOnClickListener.onClick(view);
                    }
            }
        });
    }
}
