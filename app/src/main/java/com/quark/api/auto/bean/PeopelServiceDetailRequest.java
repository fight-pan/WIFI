package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-18 15:03:03
 */
public class PeopelServiceDetailRequest {
    public String url = "/app/PeopleServicess/peopelServiceDetail";
    public String method = "get";
    private String people_services_id;//
    private String latitude;//维度
    private String longitude;//经度
    private String app_sign;//app的签名
    private String invoke;//Infer



    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setPeople_services_id(String people_services_id) {
        this.people_services_id = people_services_id;
    }

    public String getPeople_services_id() {
        return this.people_services_id;
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

    public void setApp_sign(String app_sign) {
        this.app_sign = app_sign;
    }

    public String getApp_sign() {
        return this.app_sign;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}
