package com.quark.api.auto.bean;
import java.util.ArrayList;
import java.util.List;
import com.quark.api.auto.bean.*;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-02 10:26:24
 *
 */
public class Avatars{
   //环信用户ID
   public String uid;
   //环信用户昵称
   public String name;
   //环信用户头像
   public String avatar;

   public void setUid(String uid){
     this.uid = uid;
   }
   public String getUid(){
     return this.uid;
   }
   public void setName(String name){
     this.name = name;
   }
   public String getName(){
     return this.name;
   }
   public void setAvatar(String avatar){
     this.avatar = avatar;
   }
   public String getAvatar(){
     return this.avatar;
   }
}