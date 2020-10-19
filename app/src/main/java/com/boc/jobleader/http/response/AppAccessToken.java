package com.boc.jobleader.http.response;

public final class AppAccessToken {

    public String getAppAccessToken() {
        return appAccessToken;
    }

    public void setAppAccessToken(String appAccessToken) {
        this.appAccessToken = appAccessToken;
    }

    private String appAccessToken;

    public AppAccessToken(String appAccessToken) {
        this.appAccessToken = appAccessToken;
    }
}