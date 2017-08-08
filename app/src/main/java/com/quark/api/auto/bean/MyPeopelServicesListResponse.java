package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 16:47:35
 */
public class MyPeopelServicesListResponse {
    //page number
    public MyPeopelServicesListResult myPeopelServicesListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyPeopelServicesListResponse() {
    }

    public MyPeopelServicesListResponse(String json) {
        Map<String, MyPeopelServicesListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyPeopelServicesListResponse>>() {
        });
        this.myPeopelServicesListResult = map.get("MyPeopelServicesListResponse").getMyPeopelServicesListResult();
        this.message = map.get("MyPeopelServicesListResponse").getMessage();
        this.status = map.get("MyPeopelServicesListResponse").getStatus();
        this.code = map.get("MyPeopelServicesListResponse").getCode();
    }

    public MyPeopelServicesListResult getMyPeopelServicesListResult() {
        return myPeopelServicesListResult;
    }

    public void setMyPeopelServicesListResult(MyPeopelServicesListResult myPeopelServicesListResult) {
        this.myPeopelServicesListResult = myPeopelServicesListResult;
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