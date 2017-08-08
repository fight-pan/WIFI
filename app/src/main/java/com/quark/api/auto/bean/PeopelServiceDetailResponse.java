package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-18 15:03:03
 */
public class PeopelServiceDetailResponse {
    //
    public PeopelServiceDetailResult peopelServiceDetailResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PeopelServiceDetailResponse() {
    }

    public PeopelServiceDetailResponse(String json) {
        Map<String, PeopelServiceDetailResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PeopelServiceDetailResponse>>() {
        });
        this.peopelServiceDetailResult = map.get("PeopelServiceDetailResponse").getPeopelServiceDetailResult();
        this.message = map.get("PeopelServiceDetailResponse").getMessage();
        this.status = map.get("PeopelServiceDetailResponse").getStatus();
        this.code = map.get("PeopelServiceDetailResponse").getCode();
    }

    public PeopelServiceDetailResult getPeopelServiceDetailResult() {
        return peopelServiceDetailResult;
    }

    public void setPeopelServiceDetailResult(PeopelServiceDetailResult peopelServiceDetailResult) {
        this.peopelServiceDetailResult = peopelServiceDetailResult;
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