package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class MoviesListResponse {
    //page number
    public MoviesListResult moviesListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MoviesListResponse() {
    }

    public MoviesListResponse(String json) {
        Map<String, MoviesListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MoviesListResponse>>() {
        });
        this.moviesListResult = map.get("MoviesListResponse").getMoviesListResult();
        this.message = map.get("MoviesListResponse").getMessage();
        this.status = map.get("MoviesListResponse").getStatus();
        this.code = map.get("MoviesListResponse").getCode();
    }

    public MoviesListResult getMoviesListResult() {
        return moviesListResult;
    }

    public void setMoviesListResult(MoviesListResult moviesListResult) {
        this.moviesListResult = moviesListResult;
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