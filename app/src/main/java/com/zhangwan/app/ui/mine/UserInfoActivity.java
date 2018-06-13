package com.zhangwan.app.ui.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.DateUtil;
import com.gxtc.commlibrary.utils.FileUtil;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.GsonUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.imnjh.imagepicker.SImagePicker;
import com.imnjh.imagepicker.activity.PhotoPickerActivity;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.base.BaseTitleActivity;
import com.zhangwan.app.bean.ModifyInfoBean;
import com.zhangwan.app.cache.CacheManager;
import com.zhangwan.app.http.ApiCallBack;
import com.zhangwan.app.http.ApiObserver;
import com.zhangwan.app.http.ApiResponseBean;
import com.zhangwan.app.http.service.MineApiService;
import com.zhangwan.app.presenter.UserInfoContract;
import com.zhangwan.app.presenter.UserInfoPresenter;
import com.zhangwan.app.utils.MyDialogUtil;
import com.zhangwan.app.utils.StringUtils;
import com.zhangwan.app.widget.CircleImageView;
import com.zhangwan.app.widget.ClearEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * 个人信息
 * Created by Administrator on 2018/3/29 0029.
 */

public class UserInfoActivity extends BaseTitleActivity implements UserInfoContract.View {
    @BindView(R.id.iv_info_pic)
    CircleImageView ivInfoPic;
    @BindView(R.id.iv_info_more1)
    ImageView ivInfoMore1;
    @BindView(R.id.rl_info_pic)
    RelativeLayout rlInfoPic;
    @BindView(R.id.tv_info_nickname)
    TextView tvInfoNickname;
    @BindView(R.id.iv_info_more2)
    ImageView ivInfoMore2;
    @BindView(R.id.rl_info_nickname)
    RelativeLayout rlInfoNickname;
    @BindView(R.id.tv_info_sex)
    TextView tvInfoSex;
    @BindView(R.id.iv_info_more3)
    ImageView ivInfoMore3;
    @BindView(R.id.rl_info_sex)
    RelativeLayout rlInfoSex;
    @BindView(R.id.tv_info_birth)
    TextView tvInfoBirth;
    @BindView(R.id.iv_info_more4)
    ImageView ivInfoMore4;
    @BindView(R.id.rl_info_birth)
    RelativeLayout rlInfoBirth;
    @BindView(R.id.tv_info_city)
    TextView tvInfoCity;
    @BindView(R.id.iv_info_more5)
    ImageView ivInfoMore5;
    @BindView(R.id.rl_info_city)
    RelativeLayout rlInfoCity;
    CityPickerView mCityPickerView = new CityPickerView();
    private UserInfoContract.Presenter presenter;
    private Map<String, String> map;
    private int resultCode = 1;
    private int sex;
    private TimePickerView   timePickerView;
    private SimpleDateFormat sdf;
    public final static int REQUEST_CODE_AVATAR = 1000;
    public static final String AVATAR_FILE_NAME = "avatar.png";
    public static final long maxLen_500k = 1024 * 1024 / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_userinfo);
    }

    @Override
    public void initData() {
        mCityPickerView.init(this);
        getBaseHeadView().showTitle("个人信息").showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(resultCode);
                finish();
            }
        });
        map = new HashMap<>();
        new UserInfoPresenter(this);
        if (SpUtil.getIsLogin(this)) {
            presenter.getUserInfo(SpUtil.getToken(this));
        }
    }

    @OnClick({R.id.rl_info_pic, R.id.rl_info_nickname, R.id.rl_info_sex, R.id.rl_info_birth, R.id.rl_info_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_info_pic:
                chooseImg();
                break;
            case R.id.rl_info_nickname:
                showNameDialog();
                break;
            case R.id.rl_info_sex:
                showSex();
                break;
            case R.id.rl_info_birth:
                showTimePop();
                break;
            case R.id.rl_info_city:
                wheel();
                break;
        }
    }

    //选择照片
    private void chooseImg() {
        String[] pers = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        performRequestPermissions("此应用需要读取相机和文件夹权限", pers, 100,
                new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        SImagePicker
                                .from(UserInfoActivity.this)
                                .pickMode(SImagePicker.MODE_AVATAR)
                                .showCamera(true)
                                .cropFilePath(CacheManager.getInstance().getImageInnerCache()
                                                            .getAbsolutePath(AVATAR_FILE_NAME))
                                .forResult(REQUEST_CODE_AVATAR);
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtil.showShort(MyApplication.getInstance(),getString(R.string.pre_scan_notice_msg));

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AVATAR) {
            final ArrayList<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT_SELECTION);
            if(pathList.size() > 0)
            compression(pathList.get(0));
        }
    }

    public void compression(String path) {
        //将图片进行压缩
        final File file = new File(path);
        Luban.get(MyApplication.getInstance())                //传人要压缩的图片
             .putGear(Luban.THIRD_GEAR)
             .load(file)
             .launch()
             .asObservable()
             .subscribeOn(Schedulers.newThread())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new Action1<File>() {
                 @Override
                 public void call(File compressFile) {
                     if(FileUtil.getSize(file) > maxLen_500k ){
                         setFile(compressFile);
                     }else {
                         setFile(file);
                     }
                 }
             });
    }

    private void setFile(File file){
        if(file == null || !file.exists()) return;
        String token = SpUtil.getToken(this);
        presenter.UpLoadFile(file);
    }


    /**
     * 弹出选择器
     */
    private void wheel() {
        CityConfig cityConfig = new CityConfig.Builder().title("选择城市").build();//标题
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName());
                }

                if (city != null) {
                    sb.append(city.getName());
                }

                if (district != null) {
                    sb.append(district.getName());
                }
                if (!StringUtils.isEmpty(sb.toString())) {
                    map.put("token", SpUtil.getToken(UserInfoActivity.this));
                    map.put("city",sb.toString());
                    presenter.getResult(map);
                }

            }

            @Override
            public void onCancel() {
                ToastUtil.showShort(MyApplication.getInstance(), "已取消");
            }
        });
        mCityPickerView.showCityPicker();
    }

    //显示选择时间弹窗
    private void showTimePop() {
        if (timePickerView == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            TimePickerView.Builder builder = new TimePickerView.Builder(this,
                    new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            map.put("token", SpUtil.getToken(UserInfoActivity.this));
                            map.put("birthDate",String.valueOf(date.getTime()));
                            presenter.getResult(map);
                        }
                    }).setType(TimePickerView.Type.YEAR_MONTH_DAY).setDate(
                    new Date()).setOutSideCancelable(true);

            timePickerView = new TimePickerView(builder);
        }
        timePickerView.show();
    }

    @Override
    public void showResult(Object data) {
        if(SpUtil.getIsLogin(this)){
            presenter.getUserInfo(SpUtil.getToken(this));
        }
    }

    @Override
    public void showUserInfo(ModifyInfoBean data) {
        if(!TextUtils.isEmpty(data.getNikeName())){
            SpUtil.setUserName(this,  data.getNikeName());
            tvInfoNickname.setText((String)data.getNikeName());
        }
        if(!TextUtils.isEmpty(data.getSex())){
            SpUtil.setUserSex(this,Integer.parseInt(data.getSex()));
            if (SpUtil.getUserSex(this) == 0) {
                tvInfoSex.setText("未知");
            } else if (SpUtil.getUserSex(this) == 1) {
                tvInfoSex.setText("汉子");
            } else {
                tvInfoSex.setText("妹子");
            }
        }
        if(!TextUtils.isEmpty(data.getCity())){
            tvInfoCity.setText(data.getCity());
            SpUtil.setCity(this,data.getCity());
        }
        if(!TextUtils.isEmpty(data.getBirthDate())){
            tvInfoBirth.setText(DateUtil.stampToDate(data.getBirthDate(),"yyyy-MM-dd"));
            SpUtil.setBirthDat(this,DateUtil.stampToDate(data.getBirthDate(),"yyyy-MM-dd"));
        }
        if(!TextUtils.isEmpty(data.getPic())){
            ImageHelper.getInstance().loadImage(MyApplication.getInstance(),data.getPic(),ivInfoPic);
            SpUtil.setUserPic(this,data.getPic());
        }
        resultCode = 2;
    }

    @Override
    public void showPicUrl(Object data) {
        String url = (String) GsonUtil.getJsonValue(GsonUtil.objectToJson(data),"url");
        map.put("token", SpUtil.getToken(UserInfoActivity.this));
        map.put("pic",url);
        presenter.getResult(map);
    }

    @Override
    public void setPresenter(UserInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ToastUtil.showShort(MyApplication.getInstance(),msg);
    }

    @Override
    public void showNetError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onBackPressed() {
        setResult(resultCode);
        finish();
    }

    //  修改昵称
    private void showNameDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_modifyname, null);
        MyDialogUtil.modifyDialog(this, view);
        final NameViewHolder viewHolder = new NameViewHolder(view);
        viewHolder.tvModifynameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogUtil.dissmiss();
            }
        });
        viewHolder.tvModifynameSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(viewHolder.ceModifyname.getText().toString())) {
                    map.put("token", SpUtil.getToken(UserInfoActivity.this));
                    map.put("nikeName", viewHolder.ceModifyname.getText().toString());
                    presenter.getResult(map);
                    MyDialogUtil.dissmiss();
                } else {
                    ToastUtil.showShort(UserInfoActivity.this, "请输入昵称！");
                }
            }
        });
    }

    //  修改性别
    private void showSex() {
        sex = SpUtil.getUserSex(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_modifysex, null);
        MyDialogUtil.modifyDialog(this, view);
        final SexViewHolder viewHolder = new SexViewHolder(view);
        switch (sex) {
            case 0:
                viewHolder.tvModifysexSpecies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.species_press, 0, 0, 0);
                break;
            case 1:
                viewHolder.tvModifysexMan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.man_press, 0, 0, 0);
                break;
            default:
                viewHolder.tvModifysexGirl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.girl_press, 0, 0, 0);
                break;
        }
        viewHolder.tvModifsexCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogUtil.dissmiss();
            }
        });
        viewHolder.tvModifsexSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                map.put("token", SpUtil.getToken(UserInfoActivity.this));
                map.put("sex", sex+"");
                presenter.getResult(map);
                MyDialogUtil.dissmiss();
            }
        });
        viewHolder.llModifysexMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 1;
                viewHolder.tvModifysexMan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.man_press, 0, 0, 0);
                viewHolder.tvModifysexGirl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.girl__normal, 0, 0, 0);
                viewHolder.tvModifysexSpecies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.species__normal, 0, 0, 0);
            }
        });
        viewHolder.llModifysexGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 2;
                viewHolder.tvModifysexMan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.man_normal, 0, 0, 0);
                viewHolder.tvModifysexGirl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.girl_press, 0, 0, 0);
                viewHolder.tvModifysexSpecies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.species__normal, 0, 0, 0);
            }
        });
        viewHolder.llModifysexSpecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = 0;
                viewHolder.tvModifysexMan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.man_normal, 0, 0, 0);
                viewHolder.tvModifysexGirl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.girl__normal, 0, 0, 0);
                viewHolder.tvModifysexSpecies.setCompoundDrawablesWithIntrinsicBounds(R.drawable.species_press, 0, 0, 0);
            }
        });
    }

    static class NameViewHolder {
        @BindView(R.id.ce_modifyname)
        ClearEditText ceModifyname;
        @BindView(R.id.tv_modifyname_cancel)
        TextView tvModifynameCancel;
        @BindView(R.id.tv_modifyname_sure)
        TextView tvModifynameSure;

        NameViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class SexViewHolder {
        @BindView(R.id.tv_modifysex_man)
        TextView tvModifysexMan;
        @BindView(R.id.ll_modifysex_man)
        LinearLayout llModifysexMan;
        @BindView(R.id.tv_modifysex_girl)
        TextView tvModifysexGirl;
        @BindView(R.id.ll_modifysex_girl)
        LinearLayout llModifysexGirl;
        @BindView(R.id.tv_modifysex_species)
        TextView tvModifysexSpecies;
        @BindView(R.id.ll_modifysex_species)
        LinearLayout llModifysexSpecies;
        @BindView(R.id.tv_modifsex_cancel)
        TextView tvModifsexCancel;
        @BindView(R.id.tv_modifsex_sure)
        TextView tvModifsexSure;

        SexViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
