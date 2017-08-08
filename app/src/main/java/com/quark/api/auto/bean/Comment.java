package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class Comment {
    //用户头像
    public String user_image_01;
    //用户昵称
    public String user_nickname;

    public void setUser_image_01(String user_image_01) {
        this.user_image_01 = user_image_01;
    }

    public String getUser_image_01() {
        return this.user_image_01;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_nickname() {
        return this.user_nickname;
    }
}