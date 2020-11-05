package com.boc.jobleader.module.job;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

public class TopicFragment extends BaseFragment {

    public static com.boc.jobleader.module.job.TopicFragment newInstance() {
        return new com.boc.jobleader.module.job.TopicFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}