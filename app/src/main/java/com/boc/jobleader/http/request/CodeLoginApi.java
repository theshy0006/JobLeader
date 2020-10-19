package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;


public final class CodeLoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/captcha/login";
    }

    /** 手机号 */
    private String loginName;
    /** 验证码 */
    private String captcha;

    public CodeLoginApi setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public CodeLoginApi setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }
}