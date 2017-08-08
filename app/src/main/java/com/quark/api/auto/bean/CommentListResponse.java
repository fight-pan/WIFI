package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CommentListResponse {
    //page number
    public CommentListResult CommentListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CommentListResponse() {
    }

    public CommentListResponse(String json) {
        Map<String, CommentListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CommentListResponse>>() {
        });
        this.CommentListResult = map.get("CommentListResponse").getCommentListResult();
        this.message = map.get("CommentListResponse").getMessage();
        this.status = map.get("CommentListResponse").getStatus();
        this.code = map.get("CommentListResponse").getCode();
    }

    public com.quark.api.auto.bean.CommentListResult getCommentListResult() {
        return CommentListResult;
    }

    public void setCommentListResult(com.quark.api.auto.bean.CommentListResult commentListResult) {
        CommentListResult = commentListResult;
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