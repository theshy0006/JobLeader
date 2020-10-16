package com.boc.jobleader.module.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @BindView(R.id.passwordMode)
    TextView passwordMode;

    @BindView(R.id.messageMode)
    TextView messageMode;

    @BindView(R.id.passwordloginView)
    LinearLayout passwordloginView;

    @BindView(R.id.verifyloginView)
    LinearLayout verifyloginView;




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
    protected void initView() {
        super.initView();
        changeType(0);
    }

    @OnClick({R.id.register, R.id.back, R.id.passwordMode, R.id.messageMode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
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

        }
    }
}