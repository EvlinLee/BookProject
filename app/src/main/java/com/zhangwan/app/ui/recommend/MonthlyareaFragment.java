package com.zhangwan.app.ui.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleFragment;
import com.zhangwan.app.recyclerview.RecyclerView;

import butterknife.BindView;

/**
 * 包月专区
 * Created by Administrator on 2018/3/23.
 */

public class MonthlyareaFragment extends BaseTitleFragment {
    @BindView(R.id.fragment_monthlyrea_top)
    RelativeLayout top;
    @BindView(R.id.fragment_monthlyrea_through)
    TextView mThrough;
    @BindView(R.id.fragment_monthlyrea_vertical_list)
    RecyclerView mVerticalList;
    @BindView(R.id.fragment_monthlyrea_form_list)
    RecyclerView mFormList;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_monthlyarea,container,false);
    }

    @Override
    public void initData() {
        mVerticalList.setNestedScrollingEnabled(false);
        mFormList.setNestedScrollingEnabled(false);
    }
}
