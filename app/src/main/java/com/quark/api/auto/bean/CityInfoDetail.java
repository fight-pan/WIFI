package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-11 09:03:10
 */
public class CityInfoDetail {
    //
    public int city_information_id;
    //标题
    public String title;
    //封面#号链接
    public String images_01;
    //日期
    public String post_date;
    //发布者
    public int user_id;
    //内容
    public String content;
    //省
    public String province;
    //市
    public String city;
    //区
    public String area;
    //街道小区简称
    public String short_area;
    //维度
    public String latitude;
    //经度
    public String longitude;
    //公司名称
    public UserAuditData UserAuditData;
    //好评度
    public float average_star;
    //是否有个人信息：1-有，0-无
    public String is_have_user_data;

    public String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCity_information_id() {
        return city_information_id;
    }

    public void setCity_information_id(int city_information_id) {
        this.city_information_id = city_information_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages_01() {
        return images_01;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShort_area() {
        return short_area;
    }

    public void setShort_area(String short_area) {
        this.short_area = short_area;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public com.quark.api.auto.bean.UserAuditData getUserAuditData() {
        return UserAuditData;
    }

    public void setUserAuditData(com.quark.api.auto.bean.UserAuditData userAuditData) {
        UserAuditData = userAuditData;
    }

    public float getAverage_star() {
        return average_star;
    }

    public void setAverage_star(float average_star) {
        this.average_star = average_star;
    }

    public String getIs_have_user_data() {
        return is_have_user_data;
    }

    public void setIs_have_user_data(String is_have_user_data) {
        this.is_have_user_data = is_have_user_data;
    }
}