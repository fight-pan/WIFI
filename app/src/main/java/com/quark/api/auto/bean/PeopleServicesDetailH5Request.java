package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-18 15:03:03
 */
public class PeopleServicesDetailH5Request {
    public String url = "/app/PeopleServicess/peopleServicesDetailH5";
    public String method = "get";
    private String people_services_id;//

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setPeople_services_id(String people_services_id) {
        this.people_services_id = people_services_id;
    }

    public String getPeople_services_id() {
        return this.people_services_id;
    }

}
