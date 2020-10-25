package com.boc.jobleader.module.login;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.CountdownView;
import com.boc.jobleader.custom.PasswordEditText;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CodeLoginApi;
import com.boc.jobleader.http.request.GetAppAccessTokenApi;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.request.LoginApi;
import com.boc.jobleader.http.response.AppAccessToken;
import com.boc.jobleader.http.response.LoginBean;
import com.boc.jobleader.module.forget.ForgetActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.boc.jobleader.wechat.Constants;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.register)
    TextView register;

    @Nullable
    @BindView(R.id.passwordMode)
    TextView passwordMode;

    @Nullable
    @BindView(R.id.messageMode)
    TextView messageMode;

    // 密码登录模式
    @Nullable
    @BindView(R.id.passwordloginView)
    LinearLayout passwordloginView;

    @Nullable
    @BindView(R.id.passwordPhoneInput)
    AppCompatEditText passwordPhoneInput;

    @Nullable
    @BindView(R.id.passwordCodeInput)
    PasswordEditText passwordCodeInput;

    @Nullable
    // 验证码登录模式
    @BindView(R.id.verifyloginView)
    LinearLayout verifyloginView;

    @Nullable
    @BindView(R.id.verifyPhoneInput)
    AppCompatEditText verifyPhoneInput;

    @Nullable
    @BindView(R.id.et_phone_reset_code)
    AppCompatEditText verfyCodeInput;

    // 发送验证码

    @Nullable
    @BindView(R.id.cv_phone_reset_countdown)
    CountdownView mCountdownView;

    @Nullable
    @BindView(R.id.forget)
    TextView forgetTextView;

    @Nullable
    @BindView(R.id.loginButton)
    Button loginButton;

    @Nullable
    @BindView(R.id.imageView8)
    ImageView mWeChatView;


    // 0: 密码，1: 验证码
    private int type = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(false).init();
    }

    private void changeType(int type) {
        if(type ==0) {
            this.type = 0;
            passwordMode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            passwordMode.setTextColor(0xffffffff);
            TextPaint tpaint = passwordMode.getPaint();
            tpaint.setFakeBoldText(true);

            messageMode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            messageMode.setTextColor(0xb2ffffff);
            TextPaint gpaint = messageMode.getPaint();
            gpaint.setFakeBoldText(false);

            passwordloginView.setVisibility(View.VISIBLE);
            verifyloginView.setVisibility(View.INVISIBLE);
        } else {
            this.type = 1;
            messageMode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            messageMode.setTextColor(0xffffffff);
            TextPaint ttpaint = messageMode.getPaint();
            ttpaint.setFakeBoldText(true);

            passwordMode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            passwordMode.setTextColor(0xb2ffffff);
            TextPaint ggpaint = passwordMode.getPaint();
            ggpaint.setFakeBoldText(false);

            verifyloginView.setVisibility(View.VISIBLE);
            passwordloginView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        // 连接root服务器获取appAccessToken
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeRootServer(application);

        // 刷新用户信息
        EasyHttp.post(this)
                .api(new GetAppAccessTokenApi())
                .request(new HttpCallback<HttpData<AppAccessToken>>(this) {

                    @Override
                    public void onSucceed(HttpData<AppAccessToken> data) {
                        EasyConfig.getInstance()
                                .addParam("appAccessToken", data.getData().getAppAccessToken());
                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("appAccessToken",data.getData().getAppAccessToken());
                        editor.commit();

                        MyApplication application = ActivityStackManager.getInstance().getApplication();
                        application.changeUserServer(application);
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        changeType(0);

        Intent intent =getIntent();
        /*取出Intent中附加的数据*/
        String first = intent.getStringExtra("et1");
        String second = intent.getStringExtra("et2");
    }

    @OnClick({R.id.register, R.id.passwordMode, R.id.messageMode, R.id.loginButton,
        R.id.cv_phone_reset_countdown, R.id.forget, R.id.imageView8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                // 跳转到注册界面
                startActivityForResult(RegisterActivity.class, (resultCode, data) -> {
                    // 如果已经注册成功，就执行登录操作
                    if (resultCode == RESULT_OK && data != null) {
                        changeType(0);
                        passwordPhoneInput.setText(data.getStringExtra(IntentKey.PHONE));
                        passwordCodeInput.setText(data.getStringExtra(IntentKey.PASSWORD));
                    }
                });
                break;
            case R.id.forget:
                // 跳转到忘记密码界面
                startActivityForResult(ForgetActivity.class, (resultCode, data) -> {
                    // 如果已经注册成功，就执行登录操作
                    if (resultCode == RESULT_OK && data != null) {
                        changeType(0);
                        passwordPhoneInput.setText(data.getStringExtra(IntentKey.PHONE));
                        passwordCodeInput.setText(data.getStringExtra(IntentKey.PASSWORD));
                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.passwordMode:
                changeType(0);
                break;
            case R.id.messageMode:
                changeType(1);
                break;
            case R.id.cv_phone_reset_countdown:
                if (verifyPhoneInput.getText().toString().length() == 0) {
                    toast(R.string.common_phone_input_hint);
                    return;
                }

                if (verifyPhoneInput.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

                showDialog();
                // 获取验证码
                EasyHttp.post(this)
                        .api(new GetCodeApi()
                                .setPhone(verifyPhoneInput.getText().toString()))
                        .request(new HttpCallback<HttpData<Void>>(this) {

                            @Override
                            public void onSucceed(HttpData<Void> data) {
                                toast(R.string.common_code_send_hint);
                                mCountdownView.start();
                            }

                            @Override
                            public void onFail(Exception e) {
                                super.onFail(e);
                            }
                        });
                break;
            case R.id.loginButton:
                hideSoftKeyboard();
                if (type == 0) {
                    //密码登录模式
                    if (passwordPhoneInput.getText().toString().length() == 0) {
                        toast(R.string.common_phone_input_hint);
                        return;
                    }

                    if (passwordPhoneInput.getText().toString().length() != 11) {
                        toast(R.string.common_phone_input_error);
                        return;
                    }

                    if (passwordCodeInput.getText().toString().length() == 0) {
                        toast(R.string.common_password_input_error);
                        return;
                    }
                    showDialog();
                    // 用户名密码登录
                    EasyHttp.post(this)
                            .api(new LoginApi()
                                    .setLoginName(passwordPhoneInput.getText().toString())
                                    .setPassword(passwordCodeInput.getText().toString()))
                            .request(new HttpCallback<HttpData<LoginBean>>(this) {

                                @Override
                                public void onSucceed(HttpData<LoginBean> data) {
                                    super.onSucceed(data);
                                    EasyConfig.getInstance().addParam("token",data.getData().getAccessToken().getAccessToken().toString());
                                    EasyConfig.getInstance().addHeader("token",data.getData().getAccessToken().getAccessToken().toString());


                                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("token",data.getData().getAccessToken().getAccessToken().toString());
                                    editor.putString("userName",data.getData().getUser().getUserName().toString());
                                    editor.putString("phone",data.getData().getUser().getPhone().toString());
                                    editor.putString("nickname",data.getData().getUser().getNickname().toString());
                                    editor.putString("avator",data.getData().getUser().getAvator().toString());
                                    editor.putInt("gender",data.getData().getUser().getGender());
                                    editor.putString("brithday",data.getData().getUser().getBirthdate());

                                    editor.commit();

                                    Application application = ActivityStackManager.getInstance().getApplication();
                                    Intent intent = new Intent(application, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    application.startActivity(intent);
                                    // 销毁除了首页之外的界面
                                    ActivityStackManager.getInstance().finishAllActivities(MainActivity.class);
                                }
                            });
                } else {
                    // 验证码登录模式
                    if (verifyPhoneInput.getText().toString().length() == 0) {
                        toast(R.string.common_phone_input_hint);
                        return;
                    }

                    if (verifyPhoneInput.getText().toString().length() != 11) {
                        toast(R.string.common_phone_input_error);
                        return;
                    }


                    if (verfyCodeInput.getText().toString().length() == 0) {
                        toast(R.string.common_code_input_hint);
                        return;
                    }
                    showDialog();
                    // 验证码登录
                    EasyHttp.post(this)
                            .api(new CodeLoginApi()
                                    .setLoginName(verifyPhoneInput.getText().toString())
                                    .setCaptcha(verfyCodeInput.getText().toString()))
                            .request(new HttpCallback<HttpData<LoginBean>>(this) {

                                @Override
                                public void onSucceed(HttpData<LoginBean> data) {
                                    super.onSucceed(data);
                                    EasyConfig.getInstance().addParam("token",data.getData().getAccessToken().getAccessToken().toString());
                                    EasyConfig.getInstance().addHeader("token",data.getData().getAccessToken().getAccessToken().toString());


                                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("token",data.getData().getAccessToken().getAccessToken().toString());
                                    editor.putString("userName",data.getData().getUser().getUserName().toString());
                                    editor.putString("phone",data.getData().getUser().getPhone().toString());
                                    editor.putString("nickname",data.getData().getUser().getNickname().toString());
                                    editor.putString("avator",data.getData().getUser().getAvator().toString());
                                    editor.putInt("gender",data.getData().getUser().getGender());
                                    editor.putString("brithday",data.getData().getUser().getBirthdate());
                                    editor.commit();

                                    Application application = ActivityStackManager.getInstance().getApplication();
                                    Intent intent = new Intent(application, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    application.startActivity(intent);
                                    // 销毁除了首页之外的界面
                                    ActivityStackManager.getInstance().finishAllActivities(MainActivity.class);
                                }
                            });
                }
                break;
            case R.id.imageView8:
                // 判断用户当前有没有安装微信
                wake();
                break;
        }
    }

    public void wake() {
        // send oauth request

        if( !Constants.wx_api.isWXAppInstalled() ) {
            toast("您的设备未安装微信客户端");
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            Constants.wx_api.sendReq(req);
        }


    }

}