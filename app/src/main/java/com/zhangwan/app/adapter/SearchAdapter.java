package com.zhangwan.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.SearchBean;
import com.zhangwan.app.ui.BookInfoActivity;
import com.zhangwan.app.utils.ImageUtils;

import java.util.List;

/**
 * 搜索
 * Created by Administrator on 2018/3/24 0024.
 */

public class SearchAdapter extends BaseRecyclerAdapter<SearchBean.ListBean> {
    public SearchAdapter(Context context, List<SearchBean.ListBean> list) {
        super(context, list, R.layout.item_search);
    }

    @Override
    public void bindData(ViewHolder holder, int position, final SearchBean.ListBean bean) {
        holder.setText(R.id.tv_search_item_bookname, bean.getBookName());
        holder.setText(R.id.tv_search_item_bookintro, bean.getIntro());
        ImageHelper.getInstance().loadImage(context, bean.getBookPic(), (ImageView) holder.getView(R.id.iv_search_item_bookimg));

        holder.setOnClick(R.id.ll_search_item_detail, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookInfoActivity.gotoBookInfoActivity(context, bean.getBookId());
            }
        });
    }
}
