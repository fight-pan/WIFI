package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class ListCityInformation {
    //封面
    public String images_01;
    //距离
    public String distance;
    //
    public int city_information_id;
    //日期
    public String post_date;
    //标题
    public String title;
    //维度
    public String latitude;
    //经度
    public String longitude;

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getImages_01() {
        return this.images_01;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setCity_information_id(int city_information_id) {
        this.city_information_id = city_information_id;
    }

    public int getCity_information_id() {
        return this.city_information_id;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_date() {
        return this.post_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return this.longitude;
    }
}