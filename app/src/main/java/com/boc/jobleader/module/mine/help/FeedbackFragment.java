package com.boc.jobleader.module.mine.help;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

public class FeedbackFragment extends BaseFragment {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色a
    }
}