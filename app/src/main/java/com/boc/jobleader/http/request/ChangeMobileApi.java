package com.boc.jobleader.http.request;


import com.hjq.http.config.IRequestApi;

public final class ChangeMobileApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/changeMobile";
    }

    /** 手机号 */
    private String phone;
    /** 验证码 */
    private String code;

    public ChangeMobileApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public ChangeMobileApi setCode(String code) {
        this.code = code;
        return this;
    }
}
