package com.boc.jobleader.module.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.module.mine.bind.BindActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.register)
    TextView register;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    @OnClick({R.id.register, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}