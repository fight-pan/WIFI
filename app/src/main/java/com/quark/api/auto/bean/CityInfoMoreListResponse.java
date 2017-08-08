package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class CityInfoMoreListResponse {
    //page number
    public CityInfoMoreListResult CityInfoMoreListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CityInfoMoreListResponse() {
    }

    public CityInfoMoreListResponse(String json) {
        Map<String, CityInfoMoreListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CityInfoMoreListResponse>>() {
        });
        this.CityInfoMoreListResult = map.get("CityInfoMoreListResponse").getCityInfoMoreListResult();
        this.message = map.get("CityInfoMoreListResponse").getMessage();
        this.status = map.get("CityInfoMoreListResponse").getStatus();
        this.code = map.get("CityInfoMoreListResponse").getCode();
    }

    public com.quark.api.auto.bean.CityInfoMoreListResult getCityInfoMoreListResult() {
        return CityInfoMoreListResult;
    }

    public void setCityInfoMoreListResult(com.quark.api.auto.bean.CityInfoMoreListResult cityInfoMoreListResult) {
        CityInfoMoreListResult = cityInfoMoreListResult;
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