package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class MusicListResponse {
    //page number
    public MusicListResult musicListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MusicListResponse() {
    }

    public MusicListResponse(String json) {
        Map<String, MusicListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MusicListResponse>>() {
        });
        this.musicListResult = map.get("MusicListResponse").getMusicListResult();
        this.message = map.get("MusicListResponse").getMessage();
        this.status = map.get("MusicListResponse").getStatus();
        this.code = map.get("MusicListResponse").getCode();
    }

    public MusicListResult getMusicListResult() {
        return musicListResult;
    }

    public void setMusicListResult(MusicListResult musicListResult) {
        this.musicListResult = musicListResult;
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