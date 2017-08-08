package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class TryUseResponse {
    //
    public User user;
    //服務電話
    public String server_telephone;
    //
    public String message;
    //1-操作成功,2-验证码不正确,3-密码长度不合格,请输入不少于6位的数字、字母
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public TryUseResponse() {
    }

    public TryUseResponse(String json) {
        Map<String, TryUseResponse> map = JSON.parseObject(json, new TypeReference<Map<String, TryUseResponse>>() {
        });
        this.user = map.get("TryUseResponse").getUser();
        this.server_telephone = map.get("TryUseResponse").getServer_telephone();
        this.message = map.get("TryUseResponse").getMessage();
        this.status = map.get("TryUseResponse").getStatus();
        this.code = map.get("TryUseResponse").getCode();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setServer_telephone(String server_telephone) {
        this.server_telephone = server_telephone;
    }

    public String getServer_telephone() {
        return this.server_telephone;
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