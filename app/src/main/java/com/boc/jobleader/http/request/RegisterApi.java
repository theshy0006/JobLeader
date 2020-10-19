package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 用户注册
 */
public final class RegisterApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/register";
    }

    /** 手机号 */
    private String phone;
    /** 验证码 */
    private String captcha;
    /** 密码 */
    private String password;

    public RegisterApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public RegisterApi setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }

    public RegisterApi setPassword(String password) {
        this.password = password;
        return this;
    }
}