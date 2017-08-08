package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class ListMusic {
    //
    public int music_id;
    //歌名称
    public String title;
    //播放URL
    public String music_url;

    public boolean isplaying;

    public boolean isplaying() {
        return isplaying;
    }

    public void setIsplaying(boolean isplaying) {
        this.isplaying = isplaying;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public int getMusic_id() {
        return this.music_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getMusic_url() {
        return this.music_url;
    }
}