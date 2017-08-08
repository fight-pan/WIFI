package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class PeopleServicesClassifyListResult {
    //一级分类ID
    public List<PeopleServicesClassifyBeans> PeopleServicesClassifyBeans;

    public List<com.quark.api.auto.bean.PeopleServicesClassifyBeans> getPeopleServicesClassifyBeans() {
        return PeopleServicesClassifyBeans;
    }

    public void setPeopleServicesClassifyBeans(List<com.quark.api.auto.bean.PeopleServicesClassifyBeans> peopleServicesClassifyBeans) {
        PeopleServicesClassifyBeans = peopleServicesClassifyBeans;
    }
}