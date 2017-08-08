package com.quark.wificontrol.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-02-26 11:29:56
 *
 */
public class GetAlipayInfoResponse{
   //
   public String alipay_account;
   //
   public String alipay_partner;
   //
   public String alipay_private_key_pkcs8;
   //
   public String alipay_private_key;
   //1-操作成功
   public int status;
   //200-正常返回，405-重新登陆
   public int code;
    public GetAlipayInfoResponse() {
    }
    public GetAlipayInfoResponse(String json) {
      Map<String, GetAlipayInfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, GetAlipayInfoResponse>>() {
      });
      this.alipay_account = map.get("GetAlipayInfoResponse").getAlipay_account();
      this.alipay_partner = map.get("GetAlipayInfoResponse").getAlipay_partner();
      this.alipay_private_key_pkcs8 = map.get("GetAlipayInfoResponse").getAlipay_private_key_pkcs8();
      this.alipay_private_key = map.get("GetAlipayInfoResponse").getAlipay_private_key();
      this.status = map.get("GetAlipayInfoResponse").getStatus();
      this.code = map.get("GetAlipayInfoResponse").getCode();
    }

   public void setAlipay_account(String alipay_account){
     this.alipay_account = alipay_account;
   }
   public String getAlipay_account(){
     return this.alipay_account;
   }
   public void setAlipay_partner(String alipay_partner){
     this.alipay_partner = alipay_partner;
   }
   public String getAlipay_partner(){
     return this.alipay_partner;
   }
   public void setAlipay_private_key_pkcs8(String alipay_private_key_pkcs8){
     this.alipay_private_key_pkcs8 = alipay_private_key_pkcs8;
   }
   public String getAlipay_private_key_pkcs8(){
     return this.alipay_private_key_pkcs8;
   }
   public void setAlipay_private_key(String alipay_private_key){
     this.alipay_private_key = alipay_private_key;
   }
   public String getAlipay_private_key(){
     return this.alipay_private_key;
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