package com.zhangwan.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.zhangwan.app.R;
import com.zhangwan.app.ui.recommend.mouth.MouthContract;

import butterknife.ButterKnife;

/**
 * Created by zzg on 2018/3/26.
 */

public class RuleDialog extends Dialog implements View.OnClickListener{

    Context context;
    View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public RuleDialog(@NonNull Context context) {
        super(context, R.style.BottomDialog);
        this.context = context;
        initView();
    }

    private void initView() {
        setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.dialog_rule, null);
        setContentView(view);
        view.findViewById(R.id.btn_six_vip).setOnClickListener(this);
        view.findViewById(R.id.btn_three_vip).setOnClickListener(this);
        view.findViewById(R.id.close).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(mOnClickListener != null)
        mOnClickListener.onClick(view);
    }
}
