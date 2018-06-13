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
import com.zhangwan.app.bean.BookBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 书城限时免费
 * Created by Administrator on 2018/3/30 0030.
 */

public class BsFreeAdapter extends BaseRecyclerAdapter<BookBean> {

    public BsFreeAdapter(Context context, List<BookBean> list) {
        super(context, list, R.layout.item_book_name);
    }

    @Override
    public void bindData(ViewHolder holder, int position, final BookBean bookBean) {
        ImageHelper.getInstance().loadImage(context, bookBean.getBookPic(), (ImageView) holder.getView(R.id.iv_item_bookname_pic));
        holder.setText(R.id.iv_item_bookname_name, bookBean.getBookName());
        holder.getView(R.id.ll_item_bookname_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(context)) {
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    intent.putExtra("bookId", Integer.parseInt(bookBean.getBookId()));
                    context.startActivity(intent);
                } else {
                    ToastUtil.showShort(getContext(), getContext().getString(R.string.empty_net_error));
                }

            }
        });
    }
}
