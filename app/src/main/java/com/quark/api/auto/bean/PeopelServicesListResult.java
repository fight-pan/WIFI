package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-17 15:30:47
 *
 */
public class PeopelServicesListResult{
   //page number
   public PeopelServicesList PeopelServicesList;

    public com.quark.api.auto.bean.PeopelServicesList getPeopelServicesList() {
        return PeopelServicesList;
    }

    public void setPeopelServicesList(com.quark.api.auto.bean.PeopelServicesList peopelServicesList) {
        PeopelServicesList = peopelServicesList;
    }
}