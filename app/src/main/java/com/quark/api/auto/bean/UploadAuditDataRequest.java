package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 18:17:38
 */
public class UploadAuditDataRequest {
    public String url = "/app/UserCenter/uploadAuditData";
    public String method = "get";
    private String type;//1-商家,2-创业者
    private String token;//token
    private String telephone;//手机号码-登陆
    private String identity_card;//身份证正面
    private String business_license;//营业执照
    private String hygiene_license;//卫生许可证
    private String real_name;//真实姓名
    private String tel_code;//验证码
    private String company_name;//公司名称
    private String province;//省
    private String city;//市
    private String area;//区
    private String short_area;//街道小区简称
    private String app_sign;//app的签名
    private String invoke;//Infer

    public String getApp_sign() {
        return app_sign;
    }

    public void setApp_sign(String app_sign) {
        this.app_sign = app_sign;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getHygiene_license() {
        return hygiene_license;
    }

    public void setHygiene_license(String hygiene_license) {
        this.hygiene_license = hygiene_license;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getTel_code() {
        return tel_code;
    }

    public void setTel_code(String tel_code) {
        this.tel_code = tel_code;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}
