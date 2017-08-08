package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-17 15:30:47
 */
public class PeopelServicesListResponse {
    //page number
    public PeopelServicesListResult peopelServicesListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PeopelServicesListResponse() {
    }

    public PeopelServicesListResponse(String json) {
        Map<String, PeopelServicesListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PeopelServicesListResponse>>() {
        });
        this.peopelServicesListResult = map.get("PeopelServicesListResponse").getPeopelServicesListResult();
        this.message = map.get("PeopelServicesListResponse").getMessage();
        this.status = map.get("PeopelServicesListResponse").getStatus();
        this.code = map.get("PeopelServicesListResponse").getCode();
    }

    public PeopelServicesListResult getPeopelServicesListResult() {
        return peopelServicesListResult;
    }

    public void setPeopelServicesListResult(PeopelServicesListResult peopelServicesListResult) {
        this.peopelServicesListResult = peopelServicesListResult;
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