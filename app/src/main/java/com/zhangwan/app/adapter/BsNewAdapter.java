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
import com.zhangwan.app.bean.RecommendBean;
import com.zhangwan.app.ui.BookInfoActivity;

import java.util.List;

/**
 * 书城新书推荐
 * Created by Administrator on 2018/3/27 0027.
 */

public class BsNewAdapter extends BaseRecyclerAdapter<RecommendBean> {

    public BsNewAdapter(Context context, List<RecommendBean> list) {
        super(context, list, R.layout.item_book_name);
    }

    @Override
    public void bindData(ViewHolder holder, int position,final RecommendBean recommendBean) {
        ImageHelper.getInstance().loadImage(context, recommendBean.getBookPic(), (ImageView) holder.getView(R.id.iv_item_bookname_pic));
        holder.setText(R.id.iv_item_bookname_name, recommendBean.getBookName());
        holder.getView(R.id.ll_item_bookname_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(context)){
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    intent.putExtra("bookId", recommendBean.getBookId());
                    context.startActivity(intent);
                }else {
                    ToastUtil.showShort(getContext(),getContext().getString(R.string.empty_net_error));
                }

            }
        });
    }
}
