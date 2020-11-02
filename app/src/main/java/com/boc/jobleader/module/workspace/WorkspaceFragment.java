package com.boc.jobleader.module.workspace;

import androidx.annotation.Nullable;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class WorkspaceFragment extends BaseFragment {

    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_workspace;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }
}

