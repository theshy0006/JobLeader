package com.boc.jobleader.module.forget;

import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.custom.CountdownView;
import com.boc.jobleader.custom.PasswordEditText;
import com.boc.jobleader.custom.RegexEditText;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.ForgetPasswordApi;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.response.ForgetPasswordBean;
import com.boc.jobleader.http.response.RegisterBean;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.cv_phone_reset_countdown)
    CountdownView mCountdownView;

    @BindView(R.id.verifyPhoneInput)
    RegexEditText phoneEditText;

    @BindView(R.id.et_phone_reset_code)
    AppCompatEditText codeEditText;

    @BindView(R.id.passwordCodeInput)
    PasswordEditText passwordEditText;

    @BindView(R.id.loginButton)
    Button loginButton;

    private Boolean agree = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
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

    @OnClick({R.id.back, R.id.cv_phone_reset_countdown,
            R.id.loginButton, R.id.et_phone_reset_code, R.id.passwordCodeInput})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
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

            case R.id.loginButton:
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

                // 提交注册
                EasyHttp.post(this)
                        .api(new ForgetPasswordApi()
                                .setPhone(phoneEditText.getText().toString())
                                .setCode(codeEditText.getText().toString())
                                .setPassword(passwordEditText.getText().toString()))
                        .request(new HttpCallback<HttpData<ForgetPasswordBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<ForgetPasswordBean> data) {
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