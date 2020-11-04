package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class ServiceHallHomeApi implements IRequestApi {
    @Override
    public String getApi() {
        return "api/ServiceHallModel/getServiceHallModelPageData";
    }
}