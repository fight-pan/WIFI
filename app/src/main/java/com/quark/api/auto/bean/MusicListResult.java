package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class MusicListResult{
   //page number
   public MusicList MusicList;

    public com.quark.api.auto.bean.MusicList getMusicList() {
        return MusicList;
    }

    public void setMusicList(com.quark.api.auto.bean.MusicList musicList) {
        MusicList = musicList;
    }
}