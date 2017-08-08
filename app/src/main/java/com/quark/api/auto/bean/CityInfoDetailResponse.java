package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-11 09:03:10
 */
public class CityInfoDetailResponse {
    //
    public CityInfoDetailResult cityInfoDetailResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CityInfoDetailResponse() {
    }

    public CityInfoDetailResponse(String json) {
        Map<String, CityInfoDetailResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CityInfoDetailResponse>>() {
        });
        this.cityInfoDetailResult = map.get("CityInfoDetailResponse").getCityInfoDetailResult();
        this.message = map.get("CityInfoDetailResponse").getMessage();
        this.status = map.get("CityInfoDetailResponse").getStatus();
        this.code = map.get("CityInfoDetailResponse").getCode();
    }

    public CityInfoDetailResult getCityInfoDetailResult() {
        return cityInfoDetailResult;
    }

    public void setCityInfoDetailResult(CityInfoDetailResult cityInfoDetailResult) {
        this.cityInfoDetailResult = cityInfoDetailResult;
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