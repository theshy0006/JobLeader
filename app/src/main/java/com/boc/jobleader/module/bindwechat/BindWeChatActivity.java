package com.boc.jobleader.module.bindwechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.custom.CountdownView;
import com.boc.jobleader.custom.PasswordEditText;
import com.boc.jobleader.custom.RegexEditText;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CodeLoginApi;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.request.RegisterApi;
import com.boc.jobleader.http.request.ThirdBindApi;
import com.boc.jobleader.http.response.LoginBean;
import com.boc.jobleader.http.response.RegisterBean;
import com.boc.jobleader.module.browser.BrowserActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.boc.jobleader.wechat.Constants;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class BindWeChatActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.back)
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
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_we_chat;
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
            R.id.registerButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Constants.openId = "";
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
            case R.id.registerButton:

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

                showDialog();
                // 验证码登录
                EasyHttp.post(this)
                        .api(new CodeLoginApi()
                                .setLoginName(phoneEditText.getText().toString())
                                .setCaptcha(codeEditText.getText().toString()))
                        .request(new HttpCallback<HttpData<LoginBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<LoginBean> data) {
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
                                editor.putString("inviterUserPid",data.getData().getUser().getInviterUserPid().toString());

                                editor.commit();

                                bindWechat();
                            }
                        });
        }

    }

    private void bindWechat() {
        EasyHttp.post(this)
                .api(new ThirdBindApi()
                        .setProviderId("wechat")
                        .setProviderUserId(Constants.openId))
                .request(new HttpCallback<HttpData<LoginBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<LoginBean> data) {
                        super.onSucceed(data);

                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("inviterUserPid",Constants.openId);
                        editor.commit();
                        Constants.openId = "";



                        Application application = ActivityStackManager.getInstance().getApplication();
                        Intent intent = new Intent(application, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        application.startActivity(intent);
                        // 销毁除了首页之外的界面
                        ActivityStackManager.getInstance().finishAllActivities(MainActivity.class);
                    }
                });
    }
}