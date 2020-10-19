package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class UpdatePasswordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/updatePassword";
    }

    //  新密码
    private String password;
    //  旧密码
    private String oldPassword;

    public UpdatePasswordApi setPassword(String password) {
        this.password = password;
        return this;
    }

    public UpdatePasswordApi setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

}