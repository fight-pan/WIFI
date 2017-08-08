package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class PeopleClassifysResponse {
    //
    public PeopleClassifysResult peopleClassifysResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PeopleClassifysResponse() {
    }

    public PeopleClassifysResponse(String json) {
        Map<String, PeopleClassifysResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PeopleClassifysResponse>>() {
        });
        this.peopleClassifysResult = map.get("PeopleClassifysResponse").getPeopleClassifysResult();
        this.message = map.get("PeopleClassifysResponse").getMessage();
        this.status = map.get("PeopleClassifysResponse").getStatus();
        this.code = map.get("PeopleClassifysResponse").getCode();
    }

    public PeopleClassifysResult getPeopleClassifysResult() {
        return peopleClassifysResult;
    }

    public void setPeopleClassifysResult(PeopleClassifysResult peopleClassifysResult) {
        this.peopleClassifysResult = peopleClassifysResult;
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