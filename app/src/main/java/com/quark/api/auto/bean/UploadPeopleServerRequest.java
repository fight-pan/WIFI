package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class UploadPeopleServerRequest {
    public String url = "/app/PublishManage/uploadPeopleServer";
    public String method = "get";
    private String token;//token
    private String people_services_classify_02_id;//二级分类id
    private String images_01;//封面#号链接
    private String title;//标题
    private String province;//省
    private String city;//市
    private String area;//区
    private String short_area;//街道小区简称
    private String latitude;//维度
    private String longitude;//经度
    private String content;//内容
    private String app_sign;//app的签名
    private String invoke;//Infer

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPeople_services_classify_02_id() {
        return people_services_classify_02_id;
    }

    public void setPeople_services_classify_02_id(String people_services_classify_02_id) {
        this.people_services_classify_02_id = people_services_classify_02_id;
    }

    public String getImages_01() {
        return images_01;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApp_sign() {
        return app_sign;
    }

    public void setApp_sign(String app_sign) {
        this.app_sign = app_sign;
    }

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}
