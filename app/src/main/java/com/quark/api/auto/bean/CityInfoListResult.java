package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityInfoListResult {
    //2级分类ID
    public List<CityInfoList> CityInfoList;

    public List<com.quark.api.auto.bean.CityInfoList> getCityInfoList() {
        return CityInfoList;
    }

    public void setCityInfoList(List<com.quark.api.auto.bean.CityInfoList> cityInfoList) {
        CityInfoList = cityInfoList;
    }
}