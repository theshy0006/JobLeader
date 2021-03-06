package com.boc.jobleader.module.mine.help;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.base.BaseFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;


public class HelpActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private BaseFragmentAdapter<BaseFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
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


        mTabLayout = findViewById(R.id.tl_home_tab);
        mViewPager = findViewById(R.id.vp_home_pager);

        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(HelpFragment.newInstance(), "帮助");
        mPagerAdapter.addFragment(FeedbackFragment.newInstance(), "反馈");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}