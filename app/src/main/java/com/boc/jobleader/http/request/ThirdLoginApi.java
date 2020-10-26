package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class ThirdLoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/thirdParty/login";
    }

    /** openId */
    private String providerUserId;
    /** 服务商Id */
    private String providerId;

    public ThirdLoginApi setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public ThirdLoginApi setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }
}