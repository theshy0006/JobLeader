package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

public final class AddUserApi implements IRequestApi {



    @Override
    public String getApi() {
        return "api/company/addUser";
    }


    private String name;
    private String department;
    private String type;
    private String companyId;

    public AddUserApi setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public AddUserApi setName(String name) {
        this.name = name;
        return this;
    }

    public AddUserApi setDepartment(String department) {
        this.department = department;
        return this;
    }

    public AddUserApi setType(String type) {
        this.type = type;
        return this;
    }
}