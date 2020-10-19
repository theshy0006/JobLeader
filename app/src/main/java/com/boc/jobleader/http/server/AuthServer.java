package com.boc.jobleader.http.server;

import com.hjq.http.config.IRequestServer;

public class AuthServer implements IRequestServer {

    @Override
    public String getHost() {
        return "http://122.192.73.178:7018/";
        //return "https://mock.yonyoucloud.com/mock/15896/";

    }

}