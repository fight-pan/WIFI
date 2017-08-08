package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class ListMovies {
    //
    public int movies_id;
    //影视名称
    public String title;
    //影视url
    public String movies_url;

    public void setMovies_id(int movies_id) {
        this.movies_id = movies_id;
    }

    public int getMovies_id() {
        return this.movies_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setMovies_url(String movies_url) {
        this.movies_url = movies_url;
    }

    public String getMovies_url() {
        return this.movies_url;
    }
}