package com.boc.jobleader.http.request;

import com.hjq.http.config.IRequestApi;

import java.io.File;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 上传图片
 */
public final class UpdateImageApi implements IRequestApi {

    @Override
    public String getApi() {
        return "api/file/upload";
    }

    /** 图片文件 */
    private File file;

    public UpdateImageApi setFile(File image) {
        this.file = image;
        return this;
    }
}