package com.boc.jobleader.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.boc.jobleader.R;
import com.boc.jobleader.utils.AppManager;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected String mTag = this.getClass().getSimpleName();

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        mActivity = this;
        setContentView(getLayoutId());
        //绑定控件
        ButterKnife.bind(this);
        //初始化沉浸式
        initImmersionBar();
        //初始化数据
        initData();
        //view与数据绑定
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }

    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }

    protected void initData() {
    }

    protected void initView() {
    }
}

