package com.boc.jobleader.module.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @Nullable
    @BindView(R.id.settingButton)
    ImageView setImageView;
    @Nullable
    @BindView(R.id.sb_setting_authentication)
    SettingBar setBar;
    @Nullable
    @BindView(R.id.userName)
    TextView userName;
    @Nullable
    @BindView(R.id.imageView3)
    ImageView mAvatarView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
        ImmersionBar.with(this).titleBar(R.id.tb_home_title).statusBarDarkFont(false).init();
    }

    @OnClick({R.id.settingButton, R.id.sb_setting_authentication,
            R.id.sb_setting_about, R.id.sb_setting_feedback, R.id.imageView3})
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
            case R.id.imageView3:
                startActivity(new Intent(getContext(), PersonalActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);

        SharedPreferences settings = getActivity().getSharedPreferences("UserInfo", 0);
        String nickname =  settings.getString("nickname", "").toString();
        String avator =  settings.getString("avator", "").toString();
        userName.setText(nickname);

        Glide.with(this)
                .load(avator)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mAvatarView);
    }
}

