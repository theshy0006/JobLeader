package com.boc.jobleader.module.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.CountdownView;
import com.boc.jobleader.custom.PasswordEditText;
import com.boc.jobleader.custom.RegexEditText;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.request.RegisterApi;
import com.boc.jobleader.http.response.RegisterBean;
import com.boc.jobleader.module.browser.BrowserActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.back2)
    ImageView back;
    @Nullable
    @BindView(R.id.cv_phone_reset_countdown)
    CountdownView mCountdownView;
    @Nullable
    @BindView(R.id.verifyPhoneInput)
    RegexEditText phoneEditText;
    @Nullable
    @BindView(R.id.et_phone_reset_code)
    AppCompatEditText codeEditText;
    @Nullable
    @BindView(R.id.passwordCodeInput)
    PasswordEditText passwordEditText;
    @Nullable
    @BindView(R.id.registerButton)
    Button registerButton;
    @Nullable
    @BindView(R.id.agreeLabel)
    TextView agreeLabel;

    @Nullable
    @BindView(R.id.forget2)
    TextView forget2;

    @Nullable
    @BindView(R.id.checkImage)
    ImageView checkImage;

    private Boolean agree = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(false).init();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick({R.id.back2, R.id.cv_phone_reset_countdown, R.id.agreeLabel, R.id.checkImage,
            R.id.registerButton, R.id.et_phone_reset_code, R.id.forget2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back2:
                finish();
                break;
            case R.id.agreeLabel:
            case R.id.checkImage:
                this.agree = !agree;
                if( agree ) {
                    this.checkImage.setImageResource(R.mipmap.check);
                } else {
                    this.checkImage.setImageResource(R.mipmap.uncheck);
                }
                break;
            case R.id.cv_phone_reset_countdown:
                if (phoneEditText.getText().toString().length() == 0) {
                    toast(R.string.common_phone_input_hint);
                    return;
                }

                if (phoneEditText.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }

                showDialog();
                // 获取验证码
                EasyHttp.post(this)
                        .api(new GetCodeApi()
                                .setPhone(phoneEditText.getText().toString()))
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

            case R.id.forget2:
                BrowserActivity.start(this, "https://www.buick.com.cn/privacy.html");
                break;

            case R.id.registerButton:

                if( !agree ) {
                    toast("请同意隐私政策和服务协议");
                    return;
                }

                if (phoneEditText.getText().toString().length() == 0) {
                    toast(R.string.common_phone_input_hint);
                    return;
                }

                if (phoneEditText.getText().toString().length() != 11) {
                    toast(R.string.common_phone_input_error);
                    return;
                }


                if (codeEditText.getText().toString().length() == 0) {
                    toast(R.string.common_code_input_hint);
                    return;
                }

                if (passwordEditText.getText().toString().length() == 0) {
                    toast(R.string.common_password_input_error);
                    return;
                }
                showDialog();
                // 提交注册
                EasyHttp.post(this)
                        .api(new RegisterApi()
                                .setPhone(phoneEditText.getText().toString())
                                .setCaptcha(codeEditText.getText().toString())
                                .setPassword(passwordEditText.getText().toString()))
                        .request(new HttpCallback<HttpData<RegisterBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<RegisterBean> data) {
                                toast(R.string.register_succeed);
                                setResult(RESULT_OK, new Intent()
                                        .putExtra(IntentKey.PHONE, phoneEditText.getText().toString())
                                        .putExtra(IntentKey.PASSWORD, passwordEditText.getText().toString()));
                                finish();
                            }
                        });
        }

    }
}