package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class PeopleClassifys{
   //
   public List<PeopleServicesClassify02s> PeopleServicesClassify02s;

    public int is_have_02;
    public int people_services_classify_01_id;
    public String people_services_classify_01_name;

    public int getIs_have_02() {
        return is_have_02;
    }

    public void setIs_have_02(int is_have_02) {
        this.is_have_02 = is_have_02;
    }

    public int getPeople_services_classify_01_id() {
        return people_services_classify_01_id;
    }

    public void setPeople_services_classify_01_id(int people_services_classify_01_id) {
        this.people_services_classify_01_id = people_services_classify_01_id;
    }

    public String getPeople_services_classify_01_name() {
        return people_services_classify_01_name;
    }

    public void setPeople_services_classify_01_name(String people_services_classify_01_name) {
        this.people_services_classify_01_name = people_services_classify_01_name;
    }

    public List<com.quark.api.auto.bean.PeopleServicesClassify02s> getPeopleServicesClassify02s() {
        return PeopleServicesClassify02s;
    }

    public void setPeopleServicesClassify02s(List<com.quark.api.auto.bean.PeopleServicesClassify02s> peopleServicesClassify02s) {
        PeopleServicesClassify02s = peopleServicesClassify02s;
    }
}