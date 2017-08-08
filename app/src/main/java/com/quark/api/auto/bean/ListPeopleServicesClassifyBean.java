package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-09 10:25:27
 */
public class ListPeopleServicesClassifyBean {
    //一级分类ID
    public int people_services_classify_01_id;
    //一级分类名称
    public String people_services_classify_01_name;
    //二级分类Id
    public PeopleServicesClassify02 peopleServicesClassify02;

    public void setPeople_services_classify_01_id(int people_services_classify_01_id) {
        this.people_services_classify_01_id = people_services_classify_01_id;
    }

    public int getPeople_services_classify_01_id() {
        return this.people_services_classify_01_id;
    }

    public void setPeople_services_classify_01_name(String people_services_classify_01_name) {
        this.people_services_classify_01_name = people_services_classify_01_name;
    }

    public String getPeople_services_classify_01_name() {
        return this.people_services_classify_01_name;
    }

    public PeopleServicesClassify02 getPeopleServicesClassify02() {
        return peopleServicesClassify02;
    }

    public void setPeopleServicesClassify02(PeopleServicesClassify02 peopleServicesClassify02) {
        this.peopleServicesClassify02 = peopleServicesClassify02;
    }
}