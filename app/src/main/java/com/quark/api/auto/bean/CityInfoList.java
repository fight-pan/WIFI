package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityInfoList {

    String city_classify_02_name;

    int city_classify_02_id;

    public List<CityInformations> cityInformations;

    public String getCity_classify_02_name() {
        return city_classify_02_name;
    }

    public void setCity_classify_02_name(String city_classify_02_name) {
        this.city_classify_02_name = city_classify_02_name;
    }

    public int getCity_classify_02_id() {
        return city_classify_02_id;
    }

    public void setCity_classify_02_id(int city_classify_02_id) {
        this.city_classify_02_id = city_classify_02_id;
    }

    public List<CityInformations> getCityInformations() {
        return cityInformations;
    }

    public void setCityInformations(List<CityInformations> cityInformations) {
        this.cityInformations = cityInformations;
    }
}