package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class UserInfo {
    //
    public int user_id;
    //用户头像
    public String image_01;
    //昵称
    public String nickname;
    //手机号码-登陆
    public String telephone;
    //0-未申请，1-待审核，20-商家，21-创业者，3-重新审核
    public int user_level;
    //
    public UserAuditData UserAuditData;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage_01() {
        return image_01;
    }

    public void setImage_01(String image_01) {
        this.image_01 = image_01;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public com.quark.api.auto.bean.UserAuditData getUserAuditData() {
        return UserAuditData;
    }

    public void setUserAuditData(com.quark.api.auto.bean.UserAuditData userAuditData) {
        UserAuditData = userAuditData;
    }
}