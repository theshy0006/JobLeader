package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public class CompanyAddApi implements IRequestApi {
    @Override
    public String getApi() {
        return "api/company/add";
    }

    /** 公司名称 */
    private String companyName;
    /** 营业执照 */
    private String businessLicence;
    /** 证件照片 */
    private String faceCardImg;

    public CompanyAddApi setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public CompanyAddApi setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
        return this;
    }

    public CompanyAddApi setFaceCardImg(String faceCardImg) {
        this.faceCardImg = faceCardImg;
        return this;
    }
}
