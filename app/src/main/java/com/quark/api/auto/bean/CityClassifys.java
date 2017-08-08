package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class CityClassifys{

    private String city_classify_01_name;
    private int city_classify_01_id;
    private int is_have_02;
    private List<CityClassify02s> CityClassify02s;

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

    public int getIs_have_02() {
        return is_have_02;
    }

    public void setIs_have_02(int is_have_02) {
        this.is_have_02 = is_have_02;
    }

    public List<com.quark.api.auto.bean.CityClassify02s> getCityClassify02s() {
        return CityClassify02s;
    }

    public void setCityClassify02s(List<com.quark.api.auto.bean.CityClassify02s> cityClassify02s) {
        CityClassify02s = cityClassify02s;
    }

    //   //
//   public ListCityClassify01 listCityClassify01;
//
//    public ListCityClassify01 getListCityClassify01() {
//        return listCityClassify01;
//    }
//
//    public void setListCityClassify01(ListCityClassify01 listCityClassify01) {
//        this.listCityClassify01 = listCityClassify01;
//    }
}