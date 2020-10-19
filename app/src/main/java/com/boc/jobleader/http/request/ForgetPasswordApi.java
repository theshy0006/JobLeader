package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class ForgetPasswordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/retrievePassword";
    }

    /** 手机号 */
    private String phone;
    /** 验证码 */
    private String code;
    /** 密码 */
    private String password;

    public ForgetPasswordApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ForgetPasswordApi setCode(String code) {
        this.code = code;
        return this;
    }

    public ForgetPasswordApi setPassword(String password) {
        this.password = password;
        return this;
    }
}