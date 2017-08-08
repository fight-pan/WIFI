package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class MyCityInfoListResponse {
    //page number
    public MyCityInfoListResult myCityInfoListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyCityInfoListResponse() {
    }

    public MyCityInfoListResponse(String json) {
        Map<String, MyCityInfoListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyCityInfoListResponse>>() {
        });
        this.myCityInfoListResult = map.get("MyCityInfoListResponse").getMyCityInfoListResult();
        this.message = map.get("MyCityInfoListResponse").getMessage();
        this.status = map.get("MyCityInfoListResponse").getStatus();
        this.code = map.get("MyCityInfoListResponse").getCode();
    }

    public MyCityInfoListResult getMyCityInfoListResult() {
        return myCityInfoListResult;
    }

    public void setMyCityInfoListResult(MyCityInfoListResult myCityInfoListResult) {
        this.myCityInfoListResult = myCityInfoListResult;
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