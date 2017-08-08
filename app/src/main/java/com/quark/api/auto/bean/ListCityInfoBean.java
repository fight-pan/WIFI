package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class ListCityInfoBean {
    //2级分类ID
    public int city_classify_02_id;
    //2级分类名称
    public String city_classify_02_name;
    //信息id
    public CityInformations cityInformations;

    public int getCity_classify_02_id() {
        return city_classify_02_id;
    }

    public void setCity_classify_02_id(int city_classify_02_id) {
        this.city_classify_02_id = city_classify_02_id;
    }

    public String getCity_classify_02_name() {
        return city_classify_02_name;
    }

    public void setCity_classify_02_name(String city_classify_02_name) {
        this.city_classify_02_name = city_classify_02_name;
    }

    public void setCityInformations(CityInformations cityInformations) {
        this.cityInformations = cityInformations;
    }

    public CityInformations getCityInformations() {
        return cityInformations;
    }
}