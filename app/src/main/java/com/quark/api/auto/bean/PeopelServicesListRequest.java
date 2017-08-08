package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-17 15:30:47
 *
 */
public class PeopelServicesListRequest{
   public String url = "/app/PeopleServicess/peopelServicesList";
   public String method = "get";
   private String people_services_classify_02_id;//二级分类id
   private String latitude;//维度
   private String longitude;//经度
   private int pn;//Infer
   private int page_size;//Infer
   private String app_sign;//app的签名
   private String invoke;//Infer
   public void setUrl(String url){this.url = url;}
   public String getUrl(){return this.url;}
   public void setMethod(String method){this.method = method;}
   public String getMethod(){return this.method;}

   public void setPeople_services_classify_02_id(String people_services_classify_02_id){
     this.people_services_classify_02_id = people_services_classify_02_id;
   }
   public String getPeople_services_classify_02_id(){
     return this.people_services_classify_02_id;
   }

   public void setLatitude(String latitude){
     this.latitude = latitude;
   }
   public String getLatitude(){
     return this.latitude;
   }

   public void setLongitude(String longitude){
     this.longitude = longitude;
   }
   public String getLongitude(){
     return this.longitude;
   }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public void setApp_sign(String app_sign){
     this.app_sign = app_sign;
   }
   public String getApp_sign(){
     return this.app_sign;
   }

   public void setInvoke(String invoke){
     this.invoke = invoke;
   }
   public String getInvoke(){
     return this.invoke;
   }

}
