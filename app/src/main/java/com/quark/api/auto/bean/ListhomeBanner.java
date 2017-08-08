package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-11 09:03:10
 *
 */
public class ListhomeBanner{
   //banner
   public int index_banner_id;
   //封面
   public String image_01;

    int position ;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIndex_banner_id(int index_banner_id){
     this.index_banner_id = index_banner_id;
   }
   public int getIndex_banner_id(){
     return this.index_banner_id;
   }
   public void setImage_01(String image_01){
     this.image_01 = image_01;
   }
   public String getImage_01(){
     return this.image_01;
   }
}