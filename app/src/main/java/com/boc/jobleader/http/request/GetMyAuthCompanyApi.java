package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public class GetMyAuthCompanyApi implements IRequestApi {
    @Override
    public String getApi() {
        return "api/company/mine";
    }
}
