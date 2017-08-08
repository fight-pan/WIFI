package com.quark.wificontrol.alipay;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-02-26 11:29:56
 */
public class GetAlipayInfoRequest {
    public String url = "/app/ChargeLogs/getAlipayInfo";
    public String method = "get";
    private String token;//token
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

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}
