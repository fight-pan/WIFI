package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 16:47:35
 */
public class ListPeopelServices {
    //封面
    public String images_01;
    //距离
    public String distance;
    //日期
    public String post_date;
    //维度
    public String latitude;
    //经度
    public String longitude;
    //标题
    public String title;
    //1-app发布，2-后台发布
    public int type;
    //
    public int people_services_id;

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

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_date() {
        return this.post_date;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setPeople_services_id(int people_services_id) {
        this.people_services_id = people_services_id;
    }

    public int getPeople_services_id() {
        return this.people_services_id;
    }
}