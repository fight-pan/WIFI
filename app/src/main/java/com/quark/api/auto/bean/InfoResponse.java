package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class InfoResponse {
    //
    public BaseInfoResult baseInfoResult;
    //
    public String message;
    //1-操作成功，0-失败
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public InfoResponse() {
    }

    public InfoResponse(String json) {
        Map<String, InfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, InfoResponse>>() {
        });
        this.baseInfoResult = map.get("InfoResponse").getBaseInfoResult();
        this.message = map.get("InfoResponse").getMessage();
        this.status = map.get("InfoResponse").getStatus();
        this.code = map.get("InfoResponse").getCode();
    }

    public BaseInfoResult getBaseInfoResult() {
        return baseInfoResult;
    }

    public void setBaseInfoResult(BaseInfoResult baseInfoResult) {
        this.baseInfoResult = baseInfoResult;
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