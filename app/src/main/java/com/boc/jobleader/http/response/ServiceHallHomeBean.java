package com.boc.jobleader.http.response;

import java.util.List;

public class ServiceHallHomeBean {
    private List<BannerModel> banner;
    public List<BannerModel> getBanner() {
        return banner;
    }
    public void setBanner(List<BannerModel> banner) {
        this.banner = banner;
    }
}