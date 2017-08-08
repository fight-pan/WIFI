package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-11 09:03:10
 */
public class UpdatePicResponse {
    //图片名称
    public String fileName;
    //
    public String message;
    //1-操作成功，2-上傳無null
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UpdatePicResponse() {
    }

    public UpdatePicResponse(String json) {
        Map<String, UpdatePicResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UpdatePicResponse>>() {
        });
        this.fileName = map.get("UpdatePicResponse").getFileName();
        this.message = map.get("UpdatePicResponse").getMessage();
        this.status = map.get("UpdatePicResponse").getStatus();
        this.code = map.get("UpdatePicResponse").getCode();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}