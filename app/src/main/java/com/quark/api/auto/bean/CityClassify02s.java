package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 */
public class CityClassify02s implements Serializable {

    /**
     * city_classify_02_name : KTV
     * city_classify_02_id : 1
     */

    private String city_classify_02_name;
    private int city_classify_02_id;

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
}