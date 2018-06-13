package com.zhangwan.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.RecommendBean;

import java.util.List;

/**
 * 书城主编推荐
 * Created by Administrator on 2018/3/21 0021.
 */

public class GridViewAdapter extends BaseAdapter{

    private List<RecommendBean> resultBeans;

    private LayoutInflater layoutInflater;

    //当前页索引
    private int currentIndex;

    //占满屏幕时每页展示的主题个数
    private int pageSize;

    private Context mContext;

    public GridViewAdapter(List<RecommendBean> resultBeans, int currentIndex, int pageSize, Context mContext) {
        this.layoutInflater = LayoutInflater.from(mContext);
        this.resultBeans = resultBeans;
        this.currentIndex = currentIndex;
        this.pageSize = pageSize;
        this.mContext = mContext;
    }

    /**
     * 如果剩余数据能够完全占满当前页，则返回 pageSize
     * 如果不能，则返回剩余的数据个数
     */
    @Override
    public int getCount() {
        return resultBeans.size() > (currentIndex + 1) * pageSize ? pageSize : (resultBeans.size() - currentIndex * pageSize);
    }

    @Override
    public Object getItem(int i) {
        return resultBeans.get(i + currentIndex * pageSize);
    }

    @Override
    public long getItemId(int i) {
        return i + currentIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_book_name, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.iv_item_bookname_name);
            viewHolder.iv_subject = (ImageView) convertView.findViewById(R.id.iv_item_bookname_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int pos = position + currentIndex * pageSize;
        viewHolder.tv_subject.setText(resultBeans.get(pos).getBookName());
        ImageHelper.getInstance().loadImage(mContext,resultBeans.get(pos).getBookPic(),viewHolder.iv_subject);
        return convertView;
    }

    private class ViewHolder {

        private TextView tv_subject;

        private ImageView iv_subject;

    }
}
