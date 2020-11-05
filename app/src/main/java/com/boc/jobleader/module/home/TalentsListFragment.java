package com.boc.jobleader.module.home;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

public class TalentsListFragment extends BaseFragment {

    public static com.boc.jobleader.module.home.TalentsListFragment newInstance() {
        return new com.boc.jobleader.module.home.TalentsListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_talentslist;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}