package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-02 10:26:24
 *
 */
public class AvatarsRequest{
   public String url = "/app/UserCenter/avatars";
   public String method = "get";
   private String telephones;//环信用户ID,例如：{1、2}
   private String token;//token
   private String invoke;//Infer
   public void setUrl(String url){this.url = url;}
   public String getUrl(){return this.url;}
   public void setMethod(String method){this.method = method;}
   public String getMethod(){return this.method;}

    public String getTelephones() {
        return telephones;
    }

    public void setTelephones(String telephones) {
        this.telephones = telephones;
    }

    public void setToken(String token){
     this.token = token;
   }
   public String getToken(){
     return this.token;
   }

   public void setInvoke(String invoke){
     this.invoke = invoke;
   }
   public String getInvoke(){
     return this.invoke;
   }

}
