package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class ListCityClassify01{
   //
   public int city_classify_01_id;
   //同城1级分类
   public String city_classify_01_name;
   //
   public CityClassify02s CityClassify02s;
   //是否有二级分类：1-有，0-无
   public int is_have_02;

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

    public com.quark.api.auto.bean.CityClassify02s getCityClassify02s() {
        return CityClassify02s;
    }

    public void setCityClassify02s(com.quark.api.auto.bean.CityClassify02s cityClassify02s) {
        CityClassify02s = cityClassify02s;
    }

    public int getIs_have_02() {
        return is_have_02;
    }

    public void setIs_have_02(int is_have_02) {
        this.is_have_02 = is_have_02;
    }
}