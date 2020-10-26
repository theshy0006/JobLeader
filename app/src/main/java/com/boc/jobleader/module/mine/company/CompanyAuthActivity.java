package com.boc.jobleader.module.mine.company;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.base.BaseDialog;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.dialog.MessageDialog;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.model.HttpListData;
import com.boc.jobleader.http.other.IntentKey;
import com.boc.jobleader.http.request.CompanyAddApi;
import com.boc.jobleader.http.request.FindCompanyApi;
import com.boc.jobleader.http.request.GetMyAuthCompanyApi;
import com.boc.jobleader.http.request.UpdateImageApi;
import com.boc.jobleader.http.request.UpdatePasswordApi;
import com.boc.jobleader.http.response.CompanyAddBean;
import com.boc.jobleader.http.response.CompanyAuthList;
import com.boc.jobleader.http.response.FindCompanyBean;
import com.boc.jobleader.http.response.UpdateImageBean;
import com.boc.jobleader.http.response.UpdatePasswordBean;
import com.boc.jobleader.module.image.ImageSelectActivity;
import com.boc.jobleader.module.mine.audit.AuditActivity;
import com.boc.jobleader.module.mine.authstatus.AuthStatusActivity;
import com.boc.jobleader.module.mine.changemobile.ChangeMobileActivity;
import com.boc.jobleader.module.mine.personal.PersonalActivity;
import com.boc.jobleader.module.register.RegisterActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class CompanyAuthActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.nameInput)
    EditText nameInput;
    @Nullable
    @BindView(R.id.licenseInput)
    EditText licenseInput;
    @Nullable
    @BindView(R.id.updateButton)
    Button updateButton;

    private String yingyeImageUrl = "";

    @BindView(R.id.zhengjianImageView)
    ImageView zhengjianImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_auth;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();

        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
            }
        });

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符即将被长度为after的新文本所取代。
                // 在这个方法里面改变s，会报错。
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这个方法被调用，说明在s字符串中，从start位置开始的count个字符刚刚取代了长度为before的旧文本。
                // 在这个方法里面改变s，会报错。
            }
            @Override
            public void afterTextChanged(Editable s) {
                //这个方法被调用，那么说明s字符串的某个地方已经被改变。
                FindCompany(s.toString());
            }
        });

    }

    private void hideSoftBoard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null && manager.isActive(view)) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void FindCompany(String name) {
        if(name.length() == 0) {
            return;
        }
        // 查询认证信息
        EasyHttp.post(this)
                .api(new FindCompanyApi()
                .setFullName(nameInput.getText().toString()))
                .request(new HttpCallback<HttpData<FindCompanyBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<FindCompanyBean> data) {
                        Integer exist = data.getData().getExist();
                        if (exist == 0) {
                            // 企业还没有认证过
                        } else {
                            // 企业已经认证过

                            hideSoftBoard();


                            String companyId = data.getData().getCompanyId();
                            new MessageDialog.Builder(CompanyAuthActivity.this)
                                    // 标题可以不用填写
                                    .setTitle("公司已认证")
                                    // 内容必须要填写
                                    .setMessage("请填写个人信息审核")
                                    // 确定按钮文本
                                    .setConfirm("去审核")
                                    // 设置 null 表示不显示取消按钮
                                    .setCancel(getString(R.string.common_cancel))
                                    // 设置点击按钮后不关闭对话框
                                    //.setAutoDismiss(false)
                                    .setListener(new MessageDialog.OnListener() {

                                        @Override
                                        public void onConfirm(BaseDialog dialog) {
                                            Intent intent = new Intent(CompanyAuthActivity.this, AuditActivity.class);
                                            intent.putExtra("companyId",companyId);
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onCancel(BaseDialog dialog) {

                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }



    @OnClick({R.id.zhengjianImageView, R.id.updateButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhengjianImageView:

                XXPermissions.with(this)
                        .permission(Permission.READ_EXTERNAL_STORAGE)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .permission(Permission.CAMERA)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean all) {
                                if (all) {
                                    openPhotos();
                                } else {
                                    ToastUtils.show("部分权限未正常授予");
                                    openPhotos();
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean never) {
                                if (never) {
                                    ToastUtils.show("被永久拒绝授权，请手动授予相册和拍照权限");
                                } else {
                                    ToastUtils.show("获取权限失败");
                                }
                            }
                        });
                break;
            case R.id.updateButton:
                //密码登录模式
                if (nameInput.getText().toString().length() == 0) {
                    toast("请输入公司名称");
                    return;
                }

                if (licenseInput.getText().toString().length() == 0) {
                    toast("请输入营业执照号");
                    return;
                }

                if (yingyeImageUrl.length() == 0) {
                    toast("请上传营业执照照片");
                    return;
                }

                MyApplication application = ActivityStackManager.getInstance().getApplication();
                application.changeAuthServer(application);
                // 企业认证
                EasyHttp.post(this)
                        .api(new CompanyAddApi()
                                .setFullName(nameInput.getText().toString())
                                .setBusinessLicence(yingyeImageUrl)
                                .setDutyParagraphNumber(licenseInput.getText().toString()))
                        .request(new HttpCallback<HttpData<CompanyAddBean>>(this) {

                            @Override
                            public void onSucceed(HttpData<CompanyAddBean> data) {

                                if (data.getCode() == 500) {
                                    //失败
                                    Intent intent = new Intent(CompanyAuthActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 2);
                                    startActivity(intent);
                                } else if (data.getMessage().contains("审核中")) {
                                    Intent intent = new Intent(CompanyAuthActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 1);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(CompanyAuthActivity.this, AuthStatusActivity.class);
                                    intent.putExtra("type", 0);
                                    startActivity(intent);
                                }


                            }
                        });

                break;
        }
    }

    public void openPhotos() {
        ImageSelectActivity.start(this, data -> {
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RoundedCorners roundedCorners= new RoundedCorners(6);
            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(200, 120);
            if (true) {
                yingyeImageUrl = data.get(0);
            }

            MyApplication application = ActivityStackManager.getInstance().getApplication();
            application.changeUpdateServer(application);
            // 上传头像
            EasyHttp.post(this)
                    .api(new UpdateImageApi()
                            .setFile(new File(yingyeImageUrl)))
                    .request(new HttpCallback<HttpData<UpdateImageBean>>(CompanyAuthActivity.this) {

                        @Override
                        public void onSucceed(HttpData<UpdateImageBean> data) {
                            yingyeImageUrl = data.getData().getUrl().toString();
                            Glide.with(CompanyAuthActivity.this)
                                    .load(yingyeImageUrl)
                                    .override(200,120)
                                    .apply(options)
                                    .into(zhengjianImageView);
                        }
                    });
        });
    }
}