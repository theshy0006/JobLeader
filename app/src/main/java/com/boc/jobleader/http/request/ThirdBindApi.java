package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class ThirdBindApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/thirdParty/bind";
    }

    /** openId */
    private String providerUserId;
    /** 服务商Id */
    private String providerId;

    public ThirdBindApi setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public ThirdBindApi setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }
}
