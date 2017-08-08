package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 17:32:58
 */
public class DeleteMyPublicRequest {
    public String url = "/app/PublishManage/deleteMyPublic";
    public String method = "get";
    private String token;//token
    private String type;//1-同城信息，2-其他
    private String info_id;//选择type=1:info_id=city_information_id,type=2:info_id=people_services_id
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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
    }

    public String getInfo_id() {
        return this.info_id;
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
