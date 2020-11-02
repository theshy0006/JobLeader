package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class BannerApi implements IRequestApi {
    @Override
    public String getApi() {
        return "api/homepage/banner";
    }
}