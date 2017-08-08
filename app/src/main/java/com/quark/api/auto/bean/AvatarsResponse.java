package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-02 10:26:24
 *
 */
public class AvatarsResponse{
   //环信用户ID
   public List<Avatars> avatars;
   //
   public String message;
   //1-操作成功
   public int status;
   //200-正常返回，405-重新登陆
   public int code;
    public AvatarsResponse() {
    }
    public AvatarsResponse(String json) {
      Map<String, AvatarsResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AvatarsResponse>>() {
      });
      this.avatars = map.get("AvatarsResponse").getAvatars();
      this.message = map.get("AvatarsResponse").getMessage();
      this.status = map.get("AvatarsResponse").getStatus();
      this.code = map.get("AvatarsResponse").getCode();
    }

    public List<Avatars> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Avatars> avatars) {
        this.avatars = avatars;
    }

    public void setMessage(String message){
     this.message = message;
   }
   public String getMessage(){
     return this.message;
   }
   public void setStatus(int status){
     this.status = status;
   }
   public int getStatus(){
     return this.status;
   }
   public void setCode(int code){
     this.code = code;
   }
   public int getCode(){
     return this.code;
   }
}