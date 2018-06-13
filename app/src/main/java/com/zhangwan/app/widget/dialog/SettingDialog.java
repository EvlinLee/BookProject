package com.zhangwan.app.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhangwan.app.R;
import com.zhangwan.app.bean.Config;
import com.zhangwan.app.utils.DisplayUtils;
import com.zhangwan.app.widget.ReadCircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class SettingDialog extends Dialog {

    @BindView(R.id.tv_dark)
    TextView tvDark;
    @BindView(R.id.sb_brightness)
    SeekBar sbBrightness;
    @BindView(R.id.tv_bright)
    TextView tvBright;
    @BindView(R.id.tv_xitong)
    TextView tvXitong;
    @BindView(R.id.tv_subtract)
    TextView tvSubtract;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_qihei)
    TextView tvQihei;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.iv_bg_default)
    ReadCircleImageView ivBgDefault;
    @BindView(R.id.iv_bg_1)
    ReadCircleImageView ivBg1;
    @BindView(R.id.iv_bg_2)
    ReadCircleImageView ivBg2;
    @BindView(R.id.iv_bg_3)
    ReadCircleImageView ivBg3;
    @BindView(R.id.iv_bg_4)
    ReadCircleImageView ivBg4;
    @BindView(R.id.tv_size_default)
    TextView tvSizeDefault;
    @BindView(R.id.tv_fzxinghei)
    TextView tvFzxinghei;
    @BindView(R.id.tv_fzkatong)
    TextView tvFzkatong;
    @BindView(R.id.tv_bysong)
    TextView tvBysong;


    private Config config;
    private Boolean isSystem;
    private SettingListener mSettingListener;
    private int FONT_SIZE_MIN;
    private int FONT_SIZE_MAX;
    private int currentFontSize;

    private SettingDialog(Context context, boolean flag, OnCancelListener listener) {
        super(context, flag, listener);
    }

    public SettingDialog(Context context) {
        this(context, R.style.setting_dialog);
    }

    public SettingDialog(Context context, int themeResId) {
        super(context, themeResId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.dialog_setting);
        // 初始化View注入
        ButterKnife.bind(this);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);

        FONT_SIZE_MIN = (int) getContext().getResources().getDimension(R.dimen.reading_min_text_size);
        FONT_SIZE_MAX = (int) getContext().getResources().getDimension(R.dimen.reading_max_text_size);

        config = Config.getInstance();

        //初始化亮度
        isSystem = config.isSystemLight();
        setTextViewSelect(tvXitong, isSystem);
        setBrightness(config.getLight());

        //初始化字体大小
        currentFontSize = (int) config.getFontSize();
        tvSize.setText(currentFontSize + "");

        //初始化字体
        tvDefault.setTypeface(config.getTypeface(Config.FONTTYPE_DEFAULT));
        tvQihei.setTypeface(config.getTypeface(Config.FONTTYPE_QIHEI));
//        tvFzxinghei.setTypeface(config.getTypeface(Config.FONTTYPE_FZXINGHEI));
        tvFzkatong.setTypeface(config.getTypeface(Config.FONTTYPE_FZKATONG));
//        tvBysong.setTypeface(config.getTypeface(Config.FONTTYPE_BYSONG));
//        tv_xinshou.setTypeface(config.getTypeface(Config.FONTTYPE_XINSHOU));
//        tv_wawa.setTypeface(config.getTypeface(Config.FONTTYPE_WAWA));
        selectTypeface(config.getTypefacePath());

        selectBg(config.getBookBgType());

        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 10) {
                    changeBright(false, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //选择背景
    private void selectBg(int type) {
        switch (type) {
            case Config.BOOK_BG_DEFAULT:
                ivBgDefault.setBorderWidth(DisplayUtils.dp2px(getContext(), 2));
                ivBg1.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg2.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg3.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg4.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                break;
            case Config.BOOK_BG_1:
                ivBgDefault.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg1.setBorderWidth(DisplayUtils.dp2px(getContext(), 2));
                ivBg2.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg3.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg4.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                break;
            case Config.BOOK_BG_2:
                ivBgDefault.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg1.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg2.setBorderWidth(DisplayUtils.dp2px(getContext(), 2));
                ivBg3.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg4.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                break;
            case Config.BOOK_BG_3:
                ivBgDefault.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg1.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg2.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg3.setBorderWidth(DisplayUtils.dp2px(getContext(), 2));
                ivBg4.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                break;
            case Config.BOOK_BG_4:
                ivBgDefault.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg1.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg2.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg3.setBorderWidth(DisplayUtils.dp2px(getContext(), 0));
                ivBg4.setBorderWidth(DisplayUtils.dp2px(getContext(), 2));
                break;
        }
    }

    //设置字体
    public void setBookBg(int type) {
        config.setBookBg(type);
        if (mSettingListener != null) {
            mSettingListener.changeBookBg(type);
        }
    }

    //选择字体
    private void selectTypeface(String typeface) {
        if (typeface.equals(Config.FONTTYPE_DEFAULT)) {
            setTextViewSelect(tvDefault, true);
            setTextViewSelect(tvQihei, false);
            setTextViewSelect(tvFzxinghei, false);
            setTextViewSelect(tvFzkatong, false);
            setTextViewSelect(tvBysong, false);
//            setTextViewSelect(tv_xinshou, false);
//            setTextViewSelect(tv_wawa, false);
        } else if (typeface.equals(Config.FONTTYPE_QIHEI)) {
            setTextViewSelect(tvDefault, false);
            setTextViewSelect(tvQihei, true);
            setTextViewSelect(tvFzxinghei, false);
            setTextViewSelect(tvFzkatong, false);
            setTextViewSelect(tvBysong, false);
//            setTextViewSelect(tv_xinshou, false);
//            setTextViewSelect(tv_wawa, false);
        } else if (typeface.equals(Config.FONTTYPE_FZXINGHEI)) {
            setTextViewSelect(tvDefault, false);
            setTextViewSelect(tvQihei, false);
            setTextViewSelect(tvFzxinghei, true);
            setTextViewSelect(tvFzkatong, false);
            setTextViewSelect(tvBysong, false);
//            setTextViewSelect(tv_xinshou, true);
//            setTextViewSelect(tv_wawa, false);
        } else if (typeface.equals(Config.FONTTYPE_FZKATONG)) {
            setTextViewSelect(tvDefault, false);
            setTextViewSelect(tvQihei, false);
            setTextViewSelect(tvFzxinghei, false);
            setTextViewSelect(tvFzkatong, true);
            setTextViewSelect(tvBysong, false);
//            setTextViewSelect(tv_xinshou, false);
//            setTextViewSelect(tv_wawa, true);
        } else if (typeface.equals(Config.FONTTYPE_BYSONG)) {
            setTextViewSelect(tvDefault, false);
            setTextViewSelect(tvQihei, false);
            setTextViewSelect(tvFzxinghei, false);
            setTextViewSelect(tvFzkatong, false);
            setTextViewSelect(tvBysong, true);
//            setTextViewSelect(tv_xinshou, false);
//            setTextViewSelect(tv_wawa, true);
        }
    }

    //设置字体
    public void setTypeface(String typeface) {
        config.setTypeface(typeface);
        Typeface tface = config.getTypeface(typeface);
        if (mSettingListener != null) {
            mSettingListener.changeTypeFace(tface);
        }
    }

    //设置亮度
    public void setBrightness(float brightness) {
        sbBrightness.setProgress((int) (brightness * 100));
    }

    //设置按钮选择的背景
    private void setTextViewSelect(TextView textView, Boolean isSelect) {
        if (isSelect) {
            textView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_select_bg));
            textView.setTextColor(getContext().getResources().getColor(R.color.read_dialog_button_select));
        } else {
            textView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_bg));
            textView.setTextColor(getContext().getResources().getColor(R.color.white));
        }
    }

    private void applyCompat() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }

    public Boolean isShow() {
        return isShowing();
    }


    @OnClick({R.id.tv_dark, R.id.tv_bright, R.id.tv_xitong, R.id.tv_subtract, R.id.tv_add, R.id.tv_size_default,
            R.id.tv_qihei, R.id.tv_fzxinghei, R.id.tv_fzkatong, R.id.tv_bysong,
            R.id.tv_default, R.id.iv_bg_default, R.id.iv_bg_1, R.id.iv_bg_2, R.id.iv_bg_3, R.id.iv_bg_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dark:
                break;
            case R.id.tv_bright:
                break;
            case R.id.tv_xitong:
                isSystem = !isSystem;
                changeBright(isSystem, sbBrightness.getProgress());
                break;
            case R.id.tv_subtract:
                subtractFontSize();
                break;
            case R.id.tv_add:
                addFontSize();
                break;
            case R.id.tv_size_default:
                defaultFontSize();
                break;
            case R.id.tv_qihei:
                selectTypeface(Config.FONTTYPE_QIHEI);
                setTypeface(Config.FONTTYPE_QIHEI);
                break;
            case R.id.tv_fzxinghei:
                selectTypeface(Config.FONTTYPE_FZXINGHEI);
                setTypeface(Config.FONTTYPE_FZXINGHEI);
                break;
            case R.id.tv_fzkatong:
                selectTypeface(Config.FONTTYPE_FZKATONG);
                setTypeface(Config.FONTTYPE_FZKATONG);
                break;
            case R.id.tv_bysong:
                selectTypeface(Config.FONTTYPE_BYSONG);
                setTypeface(Config.FONTTYPE_BYSONG);
                break;
//            case R.id.tv_xinshou:
//                selectTypeface(Config.FONTTYPE_XINSHOU);
//                setTypeface(Config.FONTTYPE_XINSHOU);
//                break;
//            case R.id.tv_wawa:
//                selectTypeface(Config.FONTTYPE_WAWA);
//                setTypeface(Config.FONTTYPE_WAWA);
//                break;
            case R.id.tv_default:
                selectTypeface(Config.FONTTYPE_DEFAULT);
                setTypeface(Config.FONTTYPE_DEFAULT);
                break;
            case R.id.iv_bg_default:
                setBookBg(Config.BOOK_BG_DEFAULT);
                selectBg(Config.BOOK_BG_DEFAULT);
                break;
            case R.id.iv_bg_1:
                setBookBg(Config.BOOK_BG_1);
                selectBg(Config.BOOK_BG_1);
                break;
            case R.id.iv_bg_2:
                setBookBg(Config.BOOK_BG_2);
                selectBg(Config.BOOK_BG_2);
                break;
            case R.id.iv_bg_3:
                setBookBg(Config.BOOK_BG_3);
                selectBg(Config.BOOK_BG_3);
                break;
            case R.id.iv_bg_4:
                setBookBg(Config.BOOK_BG_4);
                selectBg(Config.BOOK_BG_4);
                break;
        }
    }

    //变大书本字体
    private void addFontSize() {
        if (currentFontSize < FONT_SIZE_MAX) {
            currentFontSize += 1;
            tvSize.setText(currentFontSize + "");
            config.setFontSize(currentFontSize);
            if (mSettingListener != null) {
                mSettingListener.changeFontSize(currentFontSize);
            }
        }
    }

    private void defaultFontSize() {
        currentFontSize = (int) getContext().getResources().getDimension(R.dimen.reading_default_text_size);
        tvSize.setText(currentFontSize + "");
        config.setFontSize(currentFontSize);
        if (mSettingListener != null) {
            mSettingListener.changeFontSize(currentFontSize);
        }
    }

    //变小书本字体
    private void subtractFontSize() {
        if (currentFontSize > FONT_SIZE_MIN) {
            currentFontSize -= 1;
            tvSize.setText(currentFontSize + "");
            config.setFontSize(currentFontSize);
            if (mSettingListener != null) {
                mSettingListener.changeFontSize(currentFontSize);
            }
        }
    }

    //改变亮度
    public void changeBright(Boolean isSystem, int brightness) {
        float light = (float) (brightness / 100.0);
        setTextViewSelect(tvXitong, isSystem);
        config.setSystemLight(isSystem);
        config.setLight(light);
        if (mSettingListener != null) {
            mSettingListener.changeSystemBright(isSystem, light);
        }
    }

    public void setSettingListener(SettingListener settingListener) {
        this.mSettingListener = settingListener;
    }

    public interface SettingListener {
        void changeSystemBright(Boolean isSystem, float brightness);

        void changeFontSize(int fontSize);

        void changeTypeFace(Typeface typeface);

        void changeBookBg(int type);
    }

}