package com.boc.jobleader.module.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.module.root.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @BindView(R.id.settingButton)
    ImageView setImageView;

    @BindView(R.id.sb_setting_authentication)
    SettingBar setBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }

    @OnClick({R.id.settingButton, R.id.sb_setting_authentication,
            R.id.sb_setting_about, R.id.sb_setting_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settingButton:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.sb_setting_authentication:
                startActivity(new Intent(getContext(), AuthenticationActivity.class));
                break;
            case R.id.sb_setting_about:
                startActivity(new Intent(getContext(), AboutmeActivity.class));
                break;
            case R.id.sb_setting_feedback:
                startActivity(new Intent(getContext(), HelpActivity.class));
                break;
        }
    }
}

