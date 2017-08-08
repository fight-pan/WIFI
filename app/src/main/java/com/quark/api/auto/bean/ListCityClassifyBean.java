package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class ListCityClassifyBean{
   //一级分类ID
   public int city_classify_01_id;
   //一级分类名称
   public String city_classify_01_name;
   //二级分类Id
   public CityClassify02s cityClassify02s;

    public int getCity_classify_01_id() {
        return city_classify_01_id;
    }

    public void setCity_classify_01_id(int city_classify_01_id) {
        this.city_classify_01_id = city_classify_01_id;
    }

    public String getCity_classify_01_name() {
        return city_classify_01_name;
    }

    public void setCity_classify_01_name(String city_classify_01_name) {
        this.city_classify_01_name = city_classify_01_name;
    }

    public CityClassify02s getCityClassify02s() {
        return cityClassify02s;
    }

    public void setCityClassify02s(CityClassify02s cityClassify02s) {
        this.cityClassify02s = cityClassify02s;
    }
}