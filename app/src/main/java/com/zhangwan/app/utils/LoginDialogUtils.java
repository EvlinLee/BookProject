package com.zhangwan.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxtc.commlibrary.utils.EventBusUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangwan.app.MyApplication;
import com.zhangwan.app.R;
import com.zhangwan.app.bean.CodeBean;
import com.zhangwan.app.bean.LoginBean;
import com.zhangwan.app.bean.event.LoginEvent;
import com.zhangwan.app.http.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//  登录弹窗逻辑
public class LoginDialogUtils {

    private Dialog myDialog;
    private Context context;

    public LoginDialogUtils(Context context) {
        this.context = context;
    }

    //登录方式
    public Dialog loginType(final Activity activity) {
        myDialog = new Dialog(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_login_type, null);
        myDialog.setContentView(view);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        view.findViewById(R.id.ll_logintype_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMShareAPI.get(context).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
            }
        });
        view.findViewById(R.id.ll_logintype_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMShareAPI.get(context).getPlatformInfo(activity, SHARE_MEDIA.QQ, authListener);
            }
        });
        view.findViewById(R.id.ll_logintype_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                showLogin(0, "");
            }
        });
        view.findViewById(R.id.iv_logintype_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        return myDialog;
    }

    //手机登录（微信绑定手机号）
    private Dialog showLogin(final int type, final String key) {//0为手机登录3为微信绑定手机号
        myDialog = new Dialog(context, R.style.common_dialog);
        View view = View.inflate(context, R.layout.dialog_login, null);
        myDialog.setContentView(view);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
        Window window = myDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        final EditText code = (EditText) view.findViewById(R.id.et_login_code);
        final EditText phone = (EditText) view.findViewById(R.id.et_login_phone);
        final TextView login = (TextView) view.findViewById(R.id.tv_login);
        final TextView getcode = (TextView) view.findViewById(R.id.tv_login_code);
        final TextView title = (TextView) view.findViewById(R.id.tv_dialog);
        if (type == 0) {
            title.setText("请登录");
        } else if (type == 3) {
            title.setText("请绑定手机号");
        }

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() < 4) {
                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.loginbutton_normal);
                } else {
                    login.setClickable(true);
                    login.setBackgroundResource(R.drawable.loginbutton_click);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RegexUtils.isMobileExact(phone.getText().toString())) {
                    ToastUtil.showShort(context, "请输入正确的手机号！");
                } else if (StringUtils.isEmpty(code.getText().toString().trim())) {
                    ToastUtil.showShort(context, "请输入验证码！");
                } else {
                    if (type == 0) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("phone", phone.getText().toString());
                        map.put("code", code.getText().toString());
                        ToastUtil.showShort(context, "正在登录中...");
                        apiPhoneLogin(type, map);
                    } else if (type == 3) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("phone", phone.getText().toString());
                        map.put("code", code.getText().toString());
                        map.put("key", key);
                        ToastUtil.showShort(context, "正在登录中...");
                        apiPhoneLogin(type, map);
                    }
                }
            }
        });
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegexUtils.isMobileExact(phone.getText().toString())) {
                    getPhoneCode(phone.getText().toString(), getcode);
                } else {
                    ToastUtil.showShort(context, "请输入正确的手机号！");
                }
            }
        });
        view.findViewById(R.id.iv_login_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        return myDialog;
    }

    //关闭窗口
    public Dialog dissmiss() {
        myDialog.dismiss();
        return myDialog;
    }

    //获取手机验证码
    private void getPhoneCode(String phone, final TextView textView) {
        ApiService.getInstance().apiPhoneCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        if (codeBean.getCode().equals("200")) {
                            CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(context, textView, 60000, 1000, 0, 0, 0, 0);
                            countDownTimerUtils.start();
                        }
                        ToastUtil.showShort(context, codeBean.getMsg());
                    }
                });
    }

    //手机登录接口
    private void apiPhoneLogin(int type, final Map<String, Object> map) {
        ApiService.getInstance().apiLogin(type + "", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("MyDialogUtil", "onError: " + e);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getCode().equals("200")) {
                            SpUtil.setIsLogin(context, true);
                            SpUtil.setToken(context, loginBean.getResult().getToken());
                            SpUtil.setUserName(context, loginBean.getResult().getUserName());
                            SpUtil.setUserId(context, loginBean.getResult().getUserId() + "");
                            SpUtil.setUserPic(context, loginBean.getResult().getUserPic());
                            SpUtil.setUserBalacne(context, loginBean.getResult().getUserBalacne());
                            if (loginBean.getResult().getIsVip() == 0) {
                                SpUtil.setUserVip(context, false);
                            } else {
                                SpUtil.setUserVip(context, true);
                            }
                            //登录成功之后通知其他界面刷新
                            EventBusUtil.postStickyEvent(new LoginEvent());
                            myDialog.dismiss();
                        } else if (loginBean.getCode().equals("102")) {
                            myDialog.dismiss();
                            showLogin(3, loginBean.getMsg());
                            Log.d("LoginDialogUtils", "onNext: " + loginBean.getMsg());
                            return;
                        }
                        ToastUtil.showShort(context, loginBean.getMsg());
                    }
                });
    }

    // 登录回调
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(context, "正在启动...", Toast.LENGTH_SHORT).show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(context, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.d("LoginDialogUtils", "onComplete:data " + data.toString());
            Map<String, Object> map = new HashMap<>();
            map.put("unionid", data.get("uid"));
            map.put("city", data.get("city"));
            map.put("nickname", data.get("name"));
            map.put("openid", data.get("openid"));
            if (data.get("gender").equals("男")) {
                map.put("sex", 1);
            } else if (data.get("gender").equals("女")) {
                map.put("sex", 2);
            }
            map.put("country", data.get("country"));
            map.put("province", data.get("province"));
            map.put("language", data.get("language"));
            map.put("headimgurl", data.get("iconurl"));
//            map.put("subscribe",data.get(""));
            apiPhoneLogin(1, map);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(context, "授权失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(context, "授权取消！", Toast.LENGTH_SHORT).show();
        }
    };
}
