package com.boc.jobleader.module.mine.help;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpFragment extends BaseFragment {

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}

