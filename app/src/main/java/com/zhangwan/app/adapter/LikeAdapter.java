package com.zhangwan.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.LikeBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 猜你喜欢
 * Created by Administrator on 2018/3/27 0027.
 */

public class LikeAdapter extends BaseRecyclerAdapter<LikeBean> {

    private Context mContext;

    public LikeAdapter(Context context, List<LikeBean> list) {
        super(context, list, R.layout.item_book_name);
        this.mContext = context;
    }

    @Override
    public void bindData(ViewHolder holder, int position, final LikeBean likeBean) {
        ImageHelper.getInstance().loadImage(context, likeBean.getBookPic(), (ImageView) holder.getView(R.id.iv_item_bookname_pic));
        holder.setText(R.id.iv_item_bookname_name, likeBean.getBookName());
        holder.getView(R.id.ll_item_bookname_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(mContext)) {
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    intent.putExtra("bookId", likeBean.getBookId());
                    context.startActivity(intent);
                } else {
                    ToastUtil.showShort(mContext, mContext.getString(R.string.empty_net_error));
                }

            }
        });
    }
}
