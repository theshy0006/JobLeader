package com.boc.jobleader.http.response;

public final class FindCompanyBean {
    Integer exist;
    String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }
}