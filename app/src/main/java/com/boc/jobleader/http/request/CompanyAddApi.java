package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public class CompanyAddApi implements IRequestApi {
    @Override
    public String getApi() {
        return "api/company/add";
    }

    /** 公司名称 */
    private String fullName;
    /** 营业执照 */
    private String dutyParagraphNumber;
    /** 证件照片 */
    private String businessLicence;

    public CompanyAddApi setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public CompanyAddApi setDutyParagraphNumber(String dutyParagraphNumber) {
        this.dutyParagraphNumber = dutyParagraphNumber;
        return this;
    }

    public CompanyAddApi setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
        return this;
    }
}
