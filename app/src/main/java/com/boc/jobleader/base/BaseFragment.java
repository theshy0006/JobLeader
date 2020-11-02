package com.boc.jobleader.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.boc.jobleader.R;
import com.boc.jobleader.action.HandlerAction;
import com.boc.jobleader.action.ToastAction;
import com.boc.jobleader.custom.WaitDialog;
import com.boc.jobleader.http.model.HttpData;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.http.listener.OnHttpListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


// BaseFragment适合用在Activity做沉浸式（fragment+viewpaper）

public abstract class BaseFragment extends Fragment implements OnHttpListener, ToastAction {

    Unbinder unbinder;

    protected String mTag = this.getClass().getSimpleName();

    protected Activity mActivity;
    protected View mRootView;
    protected View statusBarView;
    protected Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBeforeView(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mRootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusBarView = view.findViewById(R.id.status_bar_view);
        toolbar = view.findViewById(R.id.toolbar);

        fitsLayoutOverlap();
        initData();
        initView();
        setListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
    }

    protected void initDataBeforeView(Bundle savedInstanceState) {

    }

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    private void fitsLayoutOverlap() {
        if (statusBarView != null) {
            ImmersionBar.setStatusBarView(this, statusBarView);
        } else {
            ImmersionBar.setTitleBar(this, toolbar);
        }
    }

    @Override
    public void onStart(Call call) {
    }

    @Override
    public void onSucceed(Object result) {
        if (result instanceof HttpData) {
            toast(((HttpData) result).getMessage());
        }
    }

    @Override
    public void onFail(Exception e) {
        toast(e.getMessage());
    }

    @Override
    public void onEnd(Call call) {
    }
}
