package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class ListComment {
    //
    public int comment_id;
    //用户ID
    public int user_id;
    //星星
    public int star;
    //评论内容
    public String content;
    //评论日期
    public String post_date;

    public String user_nickname;

    public String user_image_01;

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_image_01() {
        return user_image_01;
    }

    public void setUser_image_01(String user_image_01) {
        this.user_image_01 = user_image_01;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getComment_id() {
        return this.comment_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getStar() {
        return this.star;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_date() {
        return this.post_date;
    }
}