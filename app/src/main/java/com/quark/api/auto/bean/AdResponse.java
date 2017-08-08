package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 *
 */
public class AdResponse{
   //
   public Ad Ad;
   //
   public String message;
   //1-操作成功，0-无
   public int status;
   //200-正常返回，405-重新登陆
   public int code;
    public AdResponse() {
    }
    public AdResponse(String json) {
      Map<String, AdResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AdResponse>>() {
      });
      this.Ad = map.get("AdResponse").getAd();
      this.message = map.get("AdResponse").getMessage();
      this.status = map.get("AdResponse").getStatus();
      this.code = map.get("AdResponse").getCode();
    }

    public com.quark.api.auto.bean.Ad getAd() {
        return Ad;
    }

    public void setAd(com.quark.api.auto.bean.Ad ad) {
        Ad = ad;
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