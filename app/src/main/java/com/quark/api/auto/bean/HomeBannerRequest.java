package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-11 09:03:10
 */
public class HomeBannerRequest {
    public String url = "/app/IndexBannerManage/homeBanner";
    public String method = "get";
    private String position_index;//1-首页页面，2-发现页面
    private String position_faxian;//40-首页，41-发现-音乐轮播，42-发现-影城轮播，43-发现-同城轮播，44-发现-便民服务轮播，45-发现-游戏轮播
    private String invoke;//Infer

    public String getPosition_index() {
        return position_index;
    }

    public void setPosition_index(String position_index) {
        this.position_index = position_index;
    }

    public String getPosition_faxian() {
        return position_faxian;
    }

    public void setPosition_faxian(String position_faxian) {
        this.position_faxian = position_faxian;
    }

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

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}
