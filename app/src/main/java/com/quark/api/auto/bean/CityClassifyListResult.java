package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class CityClassifyListResult{
   //一级分类ID
   public List<CityClassifyBeans> CityClassifyBeans;

    public List<com.quark.api.auto.bean.CityClassifyBeans> getCityClassifyBeans() {
        return CityClassifyBeans;
    }

    public void setCityClassifyBeans(List<com.quark.api.auto.bean.CityClassifyBeans> cityClassifyBeans) {
        CityClassifyBeans = cityClassifyBeans;
    }
}