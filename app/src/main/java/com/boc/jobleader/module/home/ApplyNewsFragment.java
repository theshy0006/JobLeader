package com.boc.jobleader.module.home;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;


public class ApplyNewsFragment extends BaseFragment {

    public static com.boc.jobleader.module.home.ApplyNewsFragment newInstance() {
        return new com.boc.jobleader.module.home.ApplyNewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_applynews;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}