package com.boc.jobleader.module.mine.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.custom.PasswordEditText;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.GetCodeApi;
import com.boc.jobleader.http.request.RegisterApi;
import com.boc.jobleader.http.request.UpdatePasswordApi;
import com.boc.jobleader.http.response.RegisterBean;
import com.boc.jobleader.http.response.UpdatePasswordBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.old_password)
    PasswordEditText old_password;

    @BindView(R.id.new_password)
    PasswordEditText new_password;

    @BindView(R.id.again_password)
    PasswordEditText again_password;

    @BindView(R.id.okButton)
    Button okButton;


    protected int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();

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

    @OnClick({R.id.okButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.okButton:
                if (old_password.getText().toString().length() == 0) {
                    toast("请输入旧密码");
                    return;
                }

                if (new_password.getText().toString().length() == 0) {
                    toast("请输入新密码");
                    return;
                }


                if (again_password.getText().toString().length() == 0) {
                    toast("请再次输入新密码");
                    return;
                }

                if (!new_password.getText().toString().equals(again_password.getText().toString())) {
                    toast("两次密码不一致");
                    return;
                }

                // 修改密码
                EasyHttp.post(this)
                        .api(new UpdatePasswordApi()
                                .setOldPassword(old_password.getText().toString())
                                .setPassword(new_password.getText().toString()))
                        .request(new HttpCallback<HttpData<UpdatePasswordBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<UpdatePasswordBean> data) {
                                toast("密码修改成功");
                            }
                        });
        }

    }

}