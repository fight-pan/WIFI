package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityInfoListResponse {
    //2级分类ID
    public CityInfoListResult cityInfoListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CityInfoListResponse() {
    }

    public CityInfoListResponse(String json) {
        Map<String, CityInfoListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CityInfoListResponse>>() {
        });
        this.cityInfoListResult = map.get("CityInfoListResponse").getCityInfoListResult();
        this.message = map.get("CityInfoListResponse").getMessage();
        this.status = map.get("CityInfoListResponse").getStatus();
        this.code = map.get("CityInfoListResponse").getCode();
    }

    public CityInfoListResult getCityInfoListResult() {
        return cityInfoListResult;
    }

    public void setCityInfoListResult(CityInfoListResult cityInfoListResult) {
        this.cityInfoListResult = cityInfoListResult;
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