package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityInfoListRequest {
    public String url = "/app/CityInformations/cityInfoList";
    public String method = "get";
    private String city_classify_01_id;//
    private String city;//市
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

    public void setCity_classify_01_id(String city_classify_01_id) {
        this.city_classify_01_id = city_classify_01_id;
    }

    public String getCity_classify_01_id() {
        return this.city_classify_01_id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}
