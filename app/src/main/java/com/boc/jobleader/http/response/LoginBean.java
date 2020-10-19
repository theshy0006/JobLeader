package com.boc.jobleader.http.response;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 登录返回
 */
public final class LoginBean {
    private AccessTokenBean accessToken;
    private UserBean user;

    public AccessTokenBean getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessTokenBean accessToken) {
        this.accessToken = accessToken;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
