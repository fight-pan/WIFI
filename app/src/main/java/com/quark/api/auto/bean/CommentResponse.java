package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class CommentResponse {
    //
    public String message;
    //0-失败，1-操作成功，2-错误信息
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CommentResponse() {
    }

    public CommentResponse(String json) {
        Map<String, CommentResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CommentResponse>>() {
        });
        this.message = map.get("CommentResponse").getMessage();
        this.status = map.get("CommentResponse").getStatus();
        this.code = map.get("CommentResponse").getCode();
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