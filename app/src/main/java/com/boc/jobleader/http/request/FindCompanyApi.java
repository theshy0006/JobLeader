package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class FindCompanyApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/company/find";
    }

    /** 名称 */
    private String fullName;

    public FindCompanyApi setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}