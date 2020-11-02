package com.boc.jobleader.module.message;

import androidx.fragment.app.Fragment;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends BaseFragment {

    public static InviteFragment newInstance() {
        return new InviteFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}