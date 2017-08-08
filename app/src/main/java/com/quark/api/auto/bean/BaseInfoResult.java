package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 */
public class BaseInfoResult {
    //
    public UserInfo UserInfo;
    //
    public List<PeopleServicesClassify01s> PeopleServicesClassify01s;

    public com.quark.api.auto.bean.UserInfo getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(com.quark.api.auto.bean.UserInfo userInfo) {
        UserInfo = userInfo;
    }

    public List<com.quark.api.auto.bean.PeopleServicesClassify01s> getPeopleServicesClassify01s() {
        return PeopleServicesClassify01s;
    }

    public void setPeopleServicesClassify01s(List<com.quark.api.auto.bean.PeopleServicesClassify01s> peopleServicesClassify01s) {
        PeopleServicesClassify01s = peopleServicesClassify01s;
    }
}