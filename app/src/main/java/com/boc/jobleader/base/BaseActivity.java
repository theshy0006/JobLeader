package com.boc.jobleader.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.boc.jobleader.R;
import com.boc.jobleader.action.HandlerAction;
import com.boc.jobleader.action.ToastAction;
import com.boc.jobleader.custom.WaitDialog;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.utils.AppManager;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.http.listener.OnHttpListener;

import java.util.Random;

import butterknife.ButterKnife;
import okhttp3.Call;

public abstract class BaseActivity extends AppCompatActivity implements OnHttpListener,
        HandlerAction, ToastAction {

    public interface OnActivityCallback {

        /**
         * 结果回调
         *
         * @param resultCode        结果码
         * @param data              数据
         */
        void onActivityResult(int resultCode, @Nullable Intent data);
    }

    private OnActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    protected String mTag = this.getClass().getSimpleName();
    protected Activity mActivity;

    /** 状态栏沉浸 */
    private ImmersionBar mImmersionBar;

    /** 加载对话框 */
    private BaseDialog mDialog;
    /** 对话框数量 */
    private int mDialogTotal;

    /**
     * 当前加载对话框是否在显示中
     */
    public boolean isShowDialog() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        mDialogTotal++;
        postDelayed(() -> {
            if (mDialogTotal > 0 && !isFinishing()) {
                if (mDialog == null) {
                    mDialog = new WaitDialog.Builder(this)
                            .setCancelable(false)
                            .create();
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        }, 300);
    }

    /**
     * 隐藏加载对话框
     */
    public void hideDialog() {
        if (mDialogTotal > 0) {
            mDialogTotal--;
        }

        if (mDialogTotal == 0 && mDialog != null && mDialog.isShowing() && !isFinishing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    protected void initData() {
    }

    protected void initView() {

    }

    protected void initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }


    /**
     * startActivityForResult 方法优化
     */
    public void startActivityForResult(Class<? extends Activity> clazz, OnActivityCallback callback) {
        startActivityForResult(new Intent(this, clazz), null, callback);
    }

    public void startActivityForResult(Intent intent, OnActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, @Nullable Bundle options, OnActivityCallback callback) {
        // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码必须在 2 的 16 次幂以内，也就是 0 - 65535
            mActivityRequestCode = new Random().nextInt((int) Math.pow(2, 16));
            startActivityForResult(intent, mActivityRequestCode, options);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        hideSoftKeyboard();
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null && manager.isActive(view)) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in_activity, R.anim.left_out_activity);
    }

    /**
     * {@link OnHttpListener}
     */

    @Override
    public void onStart(Call call) {
        showDialog();
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

        hideDialog();
    }

    @Override
    protected void onDestroy() {
        if (isShowDialog()) {
            hideDialog();
        }
        mDialog = null;
        super.onDestroy();
    }
}

