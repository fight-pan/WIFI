package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class PeopleServicesClassifyListResponse {
    //一级分类ID
    public PeopleServicesClassifyListResult peopleServicesClassifyListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PeopleServicesClassifyListResponse() {
    }

    public PeopleServicesClassifyListResponse(String json) {
        Map<String, PeopleServicesClassifyListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PeopleServicesClassifyListResponse>>() {
        });
        this.peopleServicesClassifyListResult = map.get("PeopleServicesClassifyListResponse").getPeopleServicesClassifyListResult();
        this.message = map.get("PeopleServicesClassifyListResponse").getMessage();
        this.status = map.get("PeopleServicesClassifyListResponse").getStatus();
        this.code = map.get("PeopleServicesClassifyListResponse").getCode();
    }

    public PeopleServicesClassifyListResult getPeopleServicesClassifyListResult() {
        return peopleServicesClassifyListResult;
    }

    public void setPeopleServicesClassifyListResult(PeopleServicesClassifyListResult peopleServicesClassifyListResult) {
        this.peopleServicesClassifyListResult = peopleServicesClassifyListResult;
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