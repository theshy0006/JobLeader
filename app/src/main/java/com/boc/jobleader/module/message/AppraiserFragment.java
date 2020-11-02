package com.boc.jobleader.module.message;

import androidx.fragment.app.Fragment;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppraiserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppraiserFragment extends BaseFragment {

    public static AppraiserFragment newInstance() {
        return new AppraiserFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_appraiser;
    }

    @Override
    protected void initView() {
        super.initView();
        // 不使用图标默认变色
    }
}