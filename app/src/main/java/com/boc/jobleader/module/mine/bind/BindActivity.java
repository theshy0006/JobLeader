package com.boc.jobleader.module.mine.bind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.help.CacheDataManager;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.changemobile.ChangeMobileActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.password.PasswordActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class BindActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.name)
    SettingBar name;

    @BindView(R.id.phone)
    SettingBar phoneBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind;
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
        phoneBar.setRightText(maskNumber);

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

    @OnClick({R.id.name, R.id.phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.name:
                startActivity(new Intent(this, PasswordActivity.class));
                break;

            case R.id.phone:
                startActivity(new Intent(this, ChangeMobileActivity.class));
                break;
        }
    }
}