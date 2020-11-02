package com.boc.jobleader.module.message;

import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.base.BaseFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

public class MessageFragment extends BaseFragment {


    @BindView(R.id.tl_home_tab)
    TabLayout mTabLayout;

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;

    private BaseFragmentAdapter<BaseFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        super.initView();
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(ApplyFragment.newInstance(), "求职");
        mPagerAdapter.addFragment(InviteFragment.newInstance(), "招聘");
        mPagerAdapter.addFragment(AppraiserFragment.newInstance(), "评估师");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).titleBar(R.id.tl_home_tab).statusBarDarkFont(true).init();
    }

}



