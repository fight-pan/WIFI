package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class User {
    //token
    public String token;
    //
    public int user_id;
    //手机号码-登陆
    public String telephone;
    //用户头像
    public String image_01;
    //昵称
    public String nickname;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setImage_01(String image_01) {
        this.image_01 = image_01;
    }

    public String getImage_01() {
        return this.image_01;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}