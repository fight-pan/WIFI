package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class MusicClassifysResponse {
    //
    public List<MusicsClassifys> MusicsClassifys;
    //
    public String message;
    //1-操作成功，0-无
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MusicClassifysResponse() {
    }

    public MusicClassifysResponse(String json) {
        Map<String, MusicClassifysResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MusicClassifysResponse>>() {
        });
        this.MusicsClassifys = map.get("MusicClassifysResponse").getMusicsClassifys();
        this.message = map.get("MusicClassifysResponse").getMessage();
        this.status = map.get("MusicClassifysResponse").getStatus();
        this.code = map.get("MusicClassifysResponse").getCode();
    }

    public List<com.quark.api.auto.bean.MusicsClassifys> getMusicsClassifys() {
        return MusicsClassifys;
    }

    public void setMusicsClassifys(List<com.quark.api.auto.bean.MusicsClassifys> musicsClassifys) {
        MusicsClassifys = musicsClassifys;
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