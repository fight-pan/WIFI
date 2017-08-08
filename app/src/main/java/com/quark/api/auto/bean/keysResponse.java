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
public class keysResponse{
   //200-正常返回，405-重新登陆
   public int code;
    public keysResponse() {
    }
    public keysResponse(String json) {
      Map<String, keysResponse> map = JSON.parseObject(json, new TypeReference<Map<String, keysResponse>>() {
      });
      this.code = map.get("keysResponse").getCode();
    }

   public void setCode(int code){
     this.code = code;
   }
   public int getCode(){
     return this.code;
   }
}