package com.quark.api.auto.bean;
import java.util.ArrayList;
import java.util.List;
import com.quark.api.auto.bean.*;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 *
 */
public class Ad{
   //
   public int ad_id;
   //图
   public String image_01;
   //广告链接
   public String ad_url;

   public void setAd_id(int ad_id){
     this.ad_id = ad_id;
   }
   public int getAd_id(){
     return this.ad_id;
   }
   public void setImage_01(String image_01){
     this.image_01 = image_01;
   }
   public String getImage_01(){
     return this.image_01;
   }
   public void setAd_url(String ad_url){
     this.ad_url = ad_url;
   }
   public String getAd_url(){
     return this.ad_url;
   }
}