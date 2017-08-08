package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 17:32:58
 */
public class DeleteMyPublicResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public DeleteMyPublicResponse() {
    }

    public DeleteMyPublicResponse(String json) {
        Map<String, DeleteMyPublicResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DeleteMyPublicResponse>>() {
        });
        this.message = map.get("DeleteMyPublicResponse").getMessage();
        this.status = map.get("DeleteMyPublicResponse").getStatus();
        this.code = map.get("DeleteMyPublicResponse").getCode();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}