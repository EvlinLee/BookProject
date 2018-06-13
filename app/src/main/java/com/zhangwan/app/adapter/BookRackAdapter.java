package com.zhangwan.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.EventBusUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.BookRackBean;
import com.zhangwan.app.bean.event.BookShelfBeanEven;
import com.zhangwan.app.bean.event.ToFragmentEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 书架
 * Created by Administrator on 2018/3/26 0026.
 */

public class BookRackAdapter extends BaseRecyclerAdapter<BookRackBean.BsBean> {

    private List<String> deleteId = new ArrayList<>();
    private List<Integer> deletePosition = new ArrayList<>();

    private int state;
    private int tag;

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public BookRackAdapter(Context context, List<BookRackBean.BsBean> list, int state, int tag) {
        super(context, list, R.layout.item_book_name);
        this.state = state;
        this.tag = tag;
    }

    @Override
    public void bindData(final ViewHolder holder, final int position, final BookRackBean.BsBean bsBean) {
        ((TextView) holder.getView(R.id.iv_item_bookname_name)).setTag(tag);//根据tag判断当前item是否为选中状态 1为未选中2为选中
        if ((int) ((TextView) holder.getView(R.id.iv_item_bookname_name)).getTag() == 2){
            ((ImageView) holder.getView(R.id.iv_item_bookname_choose)).setImageResource(R.drawable.bookshelf_true);
            deleteId.add(bsBean.getId());
            deletePosition.add(position);
        }
        holder.setText(R.id.iv_item_bookname_name, bsBean.getBookName());
        if (bsBean.getBookName().equals("")) {
            ((ImageView) holder.getView(R.id.iv_item_bookname_pic)).setImageResource(R.drawable.addbook);
            ((ImageView) holder.getView(R.id.iv_item_bookname_pic)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBusUtil.post(new ToFragmentEvent(2));
                }
            });
        } else {
            ImageHelper.getInstance().loadImage(context, bsBean.getBookPic(), (ImageView) holder.getView(R.id.iv_item_bookname_pic));
        }

        switch (state) {
            case 1://删除状态下
                holder.getView(R.id.iv_item_bookname_choose).setVisibility(View.VISIBLE);
                break;
            case 2://正常状态下
                holder.getView(R.id.iv_item_bookname_choose).setVisibility(View.GONE);
                break;
        }

        holder.getView(R.id.ll_item_bookname_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state) {
                    case 1://删除状态下
                        if ((int) ((TextView) holder.getView(R.id.iv_item_bookname_name)).getTag() == 1) {
                            ((ImageView) holder.getView(R.id.iv_item_bookname_choose)).setImageResource(R.drawable.bookshelf_true);
                            ((TextView) holder.getView(R.id.iv_item_bookname_name)).setTag(2);
                            deleteId.add(bsBean.getId());
                            deletePosition.add(position);
                            listenter.onChange(deleteId);
                            EventBusUtil.post(new BookShelfBeanEven(deletePosition));
                        } else if ((int) ((TextView) holder.getView(R.id.iv_item_bookname_name)).getTag() == 2) {
                            ((ImageView) holder.getView(R.id.iv_item_bookname_choose)).setImageResource(R.drawable.bookshelf_cancel);
                            ((TextView) holder.getView(R.id.iv_item_bookname_name)).setTag(1);
                            for (int i = 0; i < deleteId.size(); i++) {
                                if (deleteId.get(i).equals(bsBean.getId())) {
                                    deleteId.remove(i);
                                    deletePosition.remove(i);
                                }
                            }
                            listenter.onChange(deleteId);
                        }
                        break;
                    case 2://正常状态下
                        if(mOnClickListener != null){
                            view.setTag(bsBean);
                            mOnClickListener.onClick(view);
                        }
                        break;
                }

            }
        });
    }

    private OnIdChangeListenter listenter;

    public interface OnIdChangeListenter {
        void onChange(List<String> id);
    }

    public void setOnIdChangeListenter(OnIdChangeListenter listenter) {
        this.listenter = listenter;
    }
}
