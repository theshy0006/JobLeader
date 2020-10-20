package com.boc.jobleader.http.server;

import com.hjq.http.config.IRequestServer;


public class UpdateServer implements IRequestServer {

    @Override
    public String getHost() {
        return "http://122.192.73.178:7008/";
    }


//    public String getHost() {
//        return "http://yapi.todder.cn/mock/62/";
//    }
}