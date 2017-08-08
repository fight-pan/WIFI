package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class KeysRequest {
    public String url = "/app/Setting/keys";
    public String method = "get";
    private String app_quark_id;//Infer
    private String app_quark_secret;//Infer
    private String app_quark_key;//Infer

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

    public void setApp_quark_id(String app_quark_id) {
        this.app_quark_id = app_quark_id;
    }

    public String getApp_quark_id() {
        return this.app_quark_id;
    }

    public void setApp_quark_secret(String app_quark_secret) {
        this.app_quark_secret = app_quark_secret;
    }

    public String getApp_quark_secret() {
        return this.app_quark_secret;
    }

    public void setApp_quark_key(String app_quark_key) {
        this.app_quark_key = app_quark_key;
    }

    public String getApp_quark_key() {
        return this.app_quark_key;
    }

}
