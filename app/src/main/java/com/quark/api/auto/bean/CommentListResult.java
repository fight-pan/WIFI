package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class CommentListResult{
   //page number
   public CommentList CommentList;

    public com.quark.api.auto.bean.CommentList getCommentList() {
        return CommentList;
    }

    public void setCommentList(com.quark.api.auto.bean.CommentList commentList) {
        CommentList = commentList;
    }
}