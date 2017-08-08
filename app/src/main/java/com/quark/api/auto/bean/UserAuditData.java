package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class UserAuditData {
    public int type;
    //
    public int user_audit_data_id;
    //真实姓名
    public String real_name;
    //公司名称
    public String company_name;
    //身份证正面
    public String identity_card;
    //营业执照
    public String business_license;
    //卫生许可证
    public String hygiene_license;
    //省
    public String province;
    //市
    public String city;
    //区
    public String area;
    //街道小区简称
    public String short_area;

    public String telephone;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUser_audit_data_id(int user_audit_data_id) {
        this.user_audit_data_id = user_audit_data_id;
    }

    public int getUser_audit_data_id() {
        return this.user_audit_data_id;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return this.real_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_name() {
        return this.company_name;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getIdentity_card() {
        return this.identity_card;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getBusiness_license() {
        return this.business_license;
    }

    public void setHygiene_license(String hygiene_license) {
        this.hygiene_license = hygiene_license;
    }

    public String getHygiene_license() {
        return this.hygiene_license;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setShort_area(String short_area) {
        this.short_area = short_area;
    }

    public String getShort_area() {
        return this.short_area;
    }
}