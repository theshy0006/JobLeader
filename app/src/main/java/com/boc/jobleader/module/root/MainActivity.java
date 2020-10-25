package com.boc.jobleader.module.root;

import com.boc.jobleader.BuildConfig;
import com.boc.jobleader.R;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.module.job.JobFragment;
import com.boc.jobleader.module.workspace.WorkspaceFragment;
import com.boc.jobleader.module.home.HomeFragment;
import com.boc.jobleader.module.message.MessageFragment;
import com.boc.jobleader.module.mine.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    @Nullable
    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;
    @Nullable
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;

    private ArrayList<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private MyAdapter myAdapter;

    @Override
    protected void initData() {
        super.initData();

        ButterKnife.bind(this);
        mFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        JobFragment jobFragment = new JobFragment();
        WorkspaceFragment workspaceFragment = new WorkspaceFragment();
        MessageFragment messageFragment = new MessageFragment();
        MineFragment mineFragment = new MineFragment();

        mFragments.add(homeFragment);
        mFragments.add(jobFragment);
        mFragments.add(workspaceFragment);
        mFragments.add(messageFragment);
        mFragments.add(mineFragment);
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOffscreenPageLimit(myAdapter.getCount());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(4);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.setSelectedItemId(position);
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_home:
            mViewPager.setCurrentItem(0);
            return true;
        case R.id.menu_job:
            mViewPager.setCurrentItem(1);
            return true;
        case R.id.menu_workspace:
            mViewPager.setCurrentItem(2);
            return true;
        case R.id.menu_message:
            mViewPager.setCurrentItem(3);
            return true;
        case R.id.menu_mine:
            mViewPager.setCurrentItem(4);
            return true;
        default:
            break;
        }
        return false;
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager manager) {
            this(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        public MyAdapter(FragmentManager manager, int behavior) {
            super(manager, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }
}