package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityClassifyBeans {


    public List<CityClassify02s> cityClassify02s;

    public String city_classify_01_name;

    public int city_classify_01_id;

    public List<CityClassify02s> getCityClassify02s() {
        return cityClassify02s;
    }

    public void setCityClassify02s(List<CityClassify02s> cityClassify02s) {
        this.cityClassify02s = cityClassify02s;
    }

    public String getCity_classify_01_name() {
        return city_classify_01_name;
    }

    public void setCity_classify_01_name(String city_classify_01_name) {
        this.city_classify_01_name = city_classify_01_name;
    }

    public int getCity_classify_01_id() {
        return city_classify_01_id;
    }

    public void setCity_classify_01_id(int city_classify_01_id) {
        this.city_classify_01_id = city_classify_01_id;
    }
}