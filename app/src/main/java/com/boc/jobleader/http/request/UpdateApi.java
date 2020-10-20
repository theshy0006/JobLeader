package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class UpdateApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/user/update";
    }

    /** 登录密码 */
    private String phone;

    private String nickname;

    private Integer gender;

    private String birthdate;

    private String avator;

    public UpdateApi setAvator(String avator) {
        this.avator = avator;
        return this;
    }

    public UpdateApi setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UpdateApi setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public UpdateApi setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        return this;
    }


    public UpdateApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}