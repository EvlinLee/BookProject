package com.zhangwan.app.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.NetworkUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.BookShelfBannerBean;
import com.zhangwan.app.read.NovelReadActivity;

import java.util.List;

public class BsBannerAdapter extends BaseAdapter {

    private List<BookShelfBannerBean> resultBeans;

    private LayoutInflater layoutInflater;

    //当前页索引
    private int currentIndex;

    //占满屏幕时每页展示的主题个数
    private int pageSize;

    private Activity mContext;

    public BsBannerAdapter(List<BookShelfBannerBean> resultBeans, int currentIndex, int pageSize, Activity mContext) {
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_bsbanner, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_bsbanner_name);
            viewHolder.tv_intro = (TextView) convertView.findViewById(R.id.tv_bsbanner_intro);
            viewHolder.iv_subject = (ImageView) convertView.findViewById(R.id.iv_bsbanner);
            viewHolder.goToRead = (TextView) convertView.findViewById(R.id.go_to_read);
            viewHolder.tvReading = (TextView) convertView.findViewById(R.id.tv_reading);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final int pos = position + currentIndex * pageSize;
        viewHolder.tv_subject.setText(resultBeans.get(pos).getBookName());
        viewHolder.tv_intro.setText(resultBeans.get(pos).getAuthor());
        viewHolder.tvReading.setText("上次阅读至" + resultBeans.get(pos).getTitle());
        ImageHelper.getInstance().loadImage(mContext, resultBeans.get(pos).getBookPic(), viewHolder.iv_subject);
        viewHolder.goToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookShelfBannerBean bean = resultBeans.get(pos);
                if (NetworkUtil.isConnected(mContext)) {
                    NovelReadActivity.startReadActivity(String.valueOf(bean.getBookId()), bean.getChapterId(), bean.getTotal(), bean.getBookPic(), bean.getBookName(), false, mContext);
                } else {
                    ToastUtil.showShort(mContext, mContext.getString(R.string.empty_net_error));
                }

            }
        });
//        ViewTreeObserver observer = viewHolder.tv_intro.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                ViewTreeObserver obs = viewHolder.tv_intro.getViewTreeObserver();
//                obs.removeGlobalOnLayoutListener(this);
//                if (viewHolder.tv_intro.getText().length() > 35) {
//                    int lineEndIndex = viewHolder.tv_intro.getLayout().getLineEnd(2);
//                    String html = viewHolder.tv_intro.getText().subSequence(0, lineEndIndex - 8)
//                            + "……<font color= '#FF0000'>"
//                            + "<big>"+"立即阅读>>"
//                            +"</big></font> ";
//                    viewHolder.tv_intro.setText(Html.fromHtml(html));
//                }
//            }
//        });
        return convertView;
    }

    private static class ViewHolder {

        private TextView tv_subject;
        private TextView tv_intro;
        private TextView goToRead;
        private TextView tvReading;

        private ImageView iv_subject;

    }
}
