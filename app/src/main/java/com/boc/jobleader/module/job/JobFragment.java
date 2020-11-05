package com.boc.jobleader.module.job;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.boc.jobleader.base.BaseFragmentAdapter;
import com.boc.jobleader.module.home.JobListFragment;
import com.boc.jobleader.module.job.notice.NoticeActivity;
import com.boc.jobleader.module.message.ApplyFragment;
import com.boc.jobleader.module.mine.help.FeedbackFragment;
import com.boc.jobleader.module.mine.help.HelpFragment;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class JobFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @Nullable
    @BindView(R.id.noticeView)
    AppCompatImageView noticeView;

    @Nullable
    @BindView(R.id.tl_home_tab)
    TabLayout mTabLayout;
    @Nullable
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;

    private BaseFragmentAdapter<BaseFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    protected void initView() {
        super.initView();

        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(MarketFragment.newInstance(), "职场");
        mPagerAdapter.addFragment(TopicFragment.newInstance(), "话题");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @OnClick({R.id.noticeView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.noticeView:
                startActivity(new Intent(getContext(), NoticeActivity.class));
                break;
        }
    }
}

