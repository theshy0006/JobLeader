package com.boc.jobleader.module.job;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.base.BaseFragmentAdapter;
import com.boc.jobleader.module.mine.help.FeedbackFragment;
import com.boc.jobleader.module.mine.help.HelpFragment;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class JobFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }
}

