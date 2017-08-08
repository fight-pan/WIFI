package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class PeopleClassifysResult{
   //
   public List<PeopleClassifys> PeopleClassifys;

    public List<com.quark.api.auto.bean.PeopleClassifys> getPeopleClassifys() {
        return PeopleClassifys;
    }

    public void setPeopleClassifys(List<com.quark.api.auto.bean.PeopleClassifys> peopleClassifys) {
        PeopleClassifys = peopleClassifys;
    }
}