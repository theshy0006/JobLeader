package com.boc.jobleader.module.mine.personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.boc.jobleader.R;
import com.boc.jobleader.base.BaseActivity;
import com.boc.jobleader.base.BaseDialog;
import com.boc.jobleader.common.MyApplication;
import com.boc.jobleader.custom.SettingBar;
import com.boc.jobleader.dialog.DateDialog;
import com.boc.jobleader.dialog.InputDialog;
import com.boc.jobleader.dialog.MessageDialog;
import com.boc.jobleader.dialog.SelectDialog;
import com.boc.jobleader.dialog.TimeDialog;
import com.boc.jobleader.help.ActivityStackManager;
import com.boc.jobleader.http.model.HttpData;
import com.boc.jobleader.http.request.UpdateApi;
import com.boc.jobleader.http.request.UpdateImageApi;
import com.boc.jobleader.http.response.UpdateBean;
import com.boc.jobleader.http.response.UpdateImageBean;
import com.boc.jobleader.module.image.ImageSelectActivity;
import com.boc.jobleader.module.mine.aboutme.AboutmeActivity;
import com.boc.jobleader.module.mine.authentication.AuthenticationActivity;
import com.boc.jobleader.module.mine.changemobile.ChangeMobileActivity;
import com.boc.jobleader.module.mine.help.HelpActivity;
import com.boc.jobleader.module.mine.set.SettingActivity;
import com.boc.jobleader.module.root.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalActivity extends BaseActivity {
    @Nullable
    @BindView(R.id.commonTitleBar)
    TitleBar mTitleBar;
    @Nullable
    @BindView(R.id.imageView2)
    ImageView headerImageView;
    @Nullable
    @BindView(R.id.name)
    SettingBar nameBar;
    @Nullable
    @BindView(R.id.sex)
    SettingBar sexBar;
    @Nullable
    @BindView(R.id.brithday)
    SettingBar brithdayBar;

    private String headerUrl = "";

    private int sexIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.commonTitleBar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initView() {
        super.initView();

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String nickname =  settings.getString("nickname", "").toString();
        String avator =  settings.getString("avator", "").toString();
        String brithday =  settings.getString("brithday", "").toString().substring(0,10);
        Integer gender =  settings.getInt("gender", 0);
        headerUrl = avator;
        nameBar.setRightText(nickname);
        if (gender == 0 || gender == 1) {
            sexBar.setRightText("男");
        } else {
            sexBar.setRightText("女");
        }
        brithdayBar.setRightText(brithday);

        Glide.with(this)
                .load(avator)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(headerImageView);


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
    }

    @OnClick({R.id.name, R.id.sex, R.id.brithday, R.id.imageView2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView2:

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

            case R.id.name:
                new InputDialog.Builder(this)
                        // 标题可以不用填写
                        .setTitle("设置昵称")
                        .setContent(nameBar.getRightText().toString())
                        .setListener((dialog, content) -> {
                            if (!nameBar.getRightText().equals(content)) {
                                if( content.length() >= 10 ) {
                                    nameBar.setRightText(content.substring(0,10));
                                    updateNickName(content.substring(0,10));
                                } else if ( content.length() == 0 ) {
                                    toast("昵称不可为空");
                                } else {
                                    nameBar.setRightText(content);
                                    updateNickName(content);
                                }
                            }
                        })
                        .show();
                break;
            case R.id.sex:
                String sex = sexBar.getRightText().toString();
                if (sex.equals("男")) {
                    sexIndex = 0;
                } else {
                    sexIndex = 1;
                }
                new SelectDialog.Builder(this)
                        .setTitle("请选择你的性别")
                        .setList("男", "女")
                        // 设置单选模式
                        .setSingleSelect()
                        // 设置默认选中
                        .setSelect(sexIndex)
                        .setListener(new SelectDialog.OnListener<String>() {

                            @Override
                            public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {

                                if ( data.toString().contains("男") ) {
                                    sexBar.setRightText("男");
                                    updateSex(1);
                                } else {
                                    sexBar.setRightText("女");
                                    updateSex(2);
                                }
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            case R.id.brithday:
                // 日期选择对话框
                new DateDialog.Builder(this)
                        .setTitle(getString(R.string.date_title))
                        // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel))
                        // 设置日期
                        //.setDate("2018-12-31")
                        //.setDate("20181231")
                        //.setDate(1546263036137)
                        // 设置年份
                        //.setYear(2018)
                        // 设置月份
                        //.setMonth(2)
                        // 设置天数
                        //.setDay(20)
                        // 不选择天数
                        //.setIgnoreDay()
                        .setListener(new DateDialog.OnListener() {
                            @Override
                            public void onSelected(BaseDialog dialog, int year, int month, int day) {
                                String time = year + "-" + month +
                                        "-" + day;
                                brithdayBar.setRightText(time);
                                updateBrithday(time);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {

                            }
                        })
                        .show();
                break;
        }
    }

    public void openPhotos() {
        ImageSelectActivity.start(this, data -> {

            if (true) {
                headerUrl = data.get(0);
                Glide.with(this)
                        .load(headerUrl)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(headerImageView);
            }

            MyApplication application = ActivityStackManager.getInstance().getApplication();
            application.changeUpdateServer(application);
            // 上传头像
            EasyHttp.post(this)
                    .api(new UpdateImageApi()
                            .setFile(new File(headerUrl)))
                    .request(new HttpCallback<HttpData<UpdateImageBean>>(PersonalActivity.this) {

                        @Override
                        public void onSucceed(HttpData<UpdateImageBean> data) {
                            headerUrl = data.getData().getUrl().toString();
                            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("avator",headerUrl);
                            editor.commit();

                            updateHeaderImage(headerUrl);


                        }
                    });
        });
    }


    public void updateHeaderImage(String imageUrl) {
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);
        EasyHttp.post(this)
                .api(new UpdateApi()
                        .setAvator(imageUrl))
                .request(new HttpCallback<HttpData<UpdateBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<UpdateBean> data) {
                        super.onSucceed(data);
                    }
                });
    }

    public void updateNickName(String nickName) {
        showDialog();
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);
        EasyHttp.post(this)
                .api(new UpdateApi()
                        .setNickname(nickName))
                .request(new HttpCallback<HttpData<UpdateBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<UpdateBean> data) {
                        super.onSucceed(data);

                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("nickname",nickName);
                        editor.commit();
                    }
                });
    }

    public void updateSex(Integer sex) {
        showDialog();
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);
        EasyHttp.post(this)
                .api(new UpdateApi()
                        .setGender(sex))
                .request(new HttpCallback<HttpData<UpdateBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<UpdateBean> data) {
                        super.onSucceed(data);

                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("gender",sex);
                        editor.commit();
                    }
                });
    }

    public void updateBrithday(String brithday) {
        showDialog();
        MyApplication application = ActivityStackManager.getInstance().getApplication();
        application.changeUserServer(application);
        EasyHttp.post(this)
                .api(new UpdateApi()
                        .setBirthdate(brithday))
                .request(new HttpCallback<HttpData<UpdateBean>>(this) {
                    @Override
                    public void onSucceed(HttpData<UpdateBean> data) {
                        super.onSucceed(data);

                        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("brithday",brithday);
                        editor.commit();
                    }
                });
    }

}