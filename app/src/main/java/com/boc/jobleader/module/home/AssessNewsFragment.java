package com.boc.jobleader.module.home;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

public class AssessNewsFragment extends BaseFragment {

    public static com.boc.jobleader.module.home.AssessNewsFragment newInstance() {
        return new com.boc.jobleader.module.home.AssessNewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_assessnews;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}