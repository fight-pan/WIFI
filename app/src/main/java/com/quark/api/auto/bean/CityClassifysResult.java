package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class CityClassifysResult{
   //
   public List<CityClassifys> CityClassifys;

    public List<com.quark.api.auto.bean.CityClassifys> getCityClassifys() {
        return CityClassifys;
    }

    public void setCityClassifys(List<com.quark.api.auto.bean.CityClassifys> cityClassifys) {
        CityClassifys = cityClassifys;
    }
}