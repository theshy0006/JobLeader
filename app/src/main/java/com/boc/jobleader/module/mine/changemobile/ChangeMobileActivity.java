package com.boc.jobleader.module.mine.changemobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.CountdownView;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CodeLoginApi;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.request.LoginApi;
import com.boc.jobleader.http.request.UpdateApi;
import com.boc.jobleader.http.response.LoginBean;
import com.boc.jobleader.http.response.UpdateBean;
import com.boc.jobleader.module.forget.ForgetActivity;
import com.boc.jobleader.module.login.LoginActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeMobileActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.textView14)
    TextView oldPhone;

    @BindView(R.id.verifyPhoneInput)
    AppCompatEditText verifyPhoneInput;

    @BindView(R.id.veryfyCodeInput)
    AppCompatEditText veryfyCodeInput;

    // 发送验证码
    @BindView(R.id.cv_password_forget_countdown)
    CountdownView mCountdownView;

    @BindView(R.id.okButton)
    Button okButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_mobile;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String nickname =  settings.getString("nickname", "").toString();
        String phone =  settings.getString("phone", "").toString();
        String maskNumber = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
        oldPhone.setText(maskNumber);


        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @OnClick({R.id.cv_password_forget_countdown, R.id.okButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cv_phone_reset_countdown:
                if (verifyPhoneInput.getText().toString().length() == 0) {
                    toast(R.string.common_phone_input_hint);
                    return;
                }

                if (verifyPhoneInput.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }


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
            case R.id.okButton:

                // 验证码登录模式
                if (verifyPhoneInput.getText().toString().length() == 0) {
                    toast(R.string.common_phone_input_hint);
                    return;
                }

                if (verifyPhoneInput.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }


                if (veryfyCodeInput.getText().toString().length() == 0) {
                    toast(R.string.common_code_input_hint);
                    return;
                }

                // 验证码登录
                EasyHttp.post(this)
                        .api(new UpdateApi()
                                .setPhone(verifyPhoneInput.getText().toString()))
                        .request(new HttpCallback<HttpData<UpdateBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<UpdateBean> data) {
                                EasyConfig.getInstance().setParams(new HashMap<>());
                                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("token","");
                                editor.putString("userName","");
                                editor.putString("phone","");
                                editor.putString("nickname","");
                                editor.putString("avator","");
                                editor.commit();

                                MyApplication application = ActivityStackManager.getInstance().getApplication();
                                application.changeRootServer(application);

                                Intent intent = new Intent(application, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                application.startActivity(intent);
                                // 销毁除了登录之外的界面
                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                            }
                        });

                break;

        }
    }

}