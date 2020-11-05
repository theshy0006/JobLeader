package com.boc.jobleader.module.home;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

public class InviteNewsFragment extends BaseFragment {

    public static com.boc.jobleader.module.home.InviteNewsFragment newInstance() {
        return new com.boc.jobleader.module.home.InviteNewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invitenews;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}