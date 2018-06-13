package com.zhangwan.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseRecyclerAdapter;
import com.zhangwan.app.bean.ChatperMenuBean;

import java.util.List;

/**
 * Created by laoshiren on 2018/3/27.
 * 章节目录选择适配器
 */

public class ChatperMenuAdapter extends BaseRecyclerAdapter<ChatperMenuBean> {

    private Context mContext;
    private int pos;

    public ChatperMenuAdapter(Context context, int pos, List<ChatperMenuBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.mContext = context;
        this.pos = pos;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void bindData(ViewHolder holder, int position, ChatperMenuBean bean) {


        //章节名
        TextView tvName = (TextView) holder.getView(R.id.tv_chatper_menu);

        pos+=1;
        for (int i = bean.getName1(); i < bean.getName2(); i++) {
            if (pos == i) {
                tvName.setTextColor(mContext.getResources().getColor(R.color.read_chatper_textcolor));
                tvName.setBackground(mContext.getResources().getDrawable(R.drawable.shape_read_dialog_recyclerview_press));
            }
        }

        tvName.setText(bean.getName1() + "~" + bean.getName2() + "章");
    }

}
