package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityClassifysResponse {
    //
    public CityClassifysResult cityClassifysResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CityClassifysResponse() {
    }

    public CityClassifysResponse(String json) {
        Map<String, CityClassifysResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CityClassifysResponse>>() {
        });
        this.cityClassifysResult = map.get("CityClassifysResponse").getCityClassifysResult();
        this.message = map.get("CityClassifysResponse").getMessage();
        this.status = map.get("CityClassifysResponse").getStatus();
        this.code = map.get("CityClassifysResponse").getCode();
    }

    public CityClassifysResult getCityClassifysResult() {
        return cityClassifysResult;
    }

    public void setCityClassifysResult(CityClassifysResult cityClassifysResult) {
        this.cityClassifysResult = cityClassifysResult;
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