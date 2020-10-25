package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;


public final class CheckUpdateApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/update/check";
    }

    /** 手机号 */
    private String versionCode;
    /** 0:安卓，1:iOS */
    private Integer system;

    public CheckUpdateApi setVersionCode(String versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public CheckUpdateApi setSystem(Integer system) {
        this.system = system;
        return this;
    }
}