package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class ListMusicClassify {
    //
    public int music_classify_id;
    //分类名称
    public String classify_name;
    //封面
    public String images_01;

    public void setMusic_classify_id(int music_classify_id) {
        this.music_classify_id = music_classify_id;
    }

    public int getMusic_classify_id() {
        return this.music_classify_id;
    }

    public void setClassify_name(String classify_name) {
        this.classify_name = classify_name;
    }

    public String getClassify_name() {
        return this.classify_name;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getImages_01() {
        return this.images_01;
    }
}