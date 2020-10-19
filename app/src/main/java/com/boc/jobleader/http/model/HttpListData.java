package com.boc.jobleader.http.model;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2020/10/07
 *    desc   : 统一接口列表数据结构
 */
public class HttpListData<T> {

    /** 返回码 */
    private int code;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private List<T> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public List<T> getData() {
        return data;
    }
}