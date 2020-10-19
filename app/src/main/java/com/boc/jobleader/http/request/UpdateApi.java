package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class UpdateApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/update";
    }

    /** 登录密码 */
    private String phone;

    public UpdateApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}