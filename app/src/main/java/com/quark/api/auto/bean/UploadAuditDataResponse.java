package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 18:17:38
 */
public class UploadAuditDataResponse {
    //
    public String message;
    //1-获取成功
    public int status;
    //200-正常返回，405-重新登陆
    public String code;

    public UploadAuditDataResponse() {
    }

    public UploadAuditDataResponse(String json) {
        Map<String, UploadAuditDataResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UploadAuditDataResponse>>() {
        });
        this.message = map.get("UploadAuditDataResponse").getMessage();
        this.status = map.get("UploadAuditDataResponse").getStatus();
        this.code = map.get("UploadAuditDataResponse").getCode();
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}