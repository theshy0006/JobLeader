package com.boc.jobleader.module.mine.set;

import android.content.Intent;
import android.view.View;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.module.mine.bind.BindActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @BindView(R.id.jobsearchView)
    SettingBar personalBar;

    @BindView(R.id.account)
    SettingBar accountBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
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

    @OnClick({R.id.jobsearchView, R.id.account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jobsearchView:
                startActivity(new Intent(this, PersonalActivity.class));
                break;
            case R.id.account:
                startActivity(new Intent(this, BindActivity.class));
                break;
        }
    }
}