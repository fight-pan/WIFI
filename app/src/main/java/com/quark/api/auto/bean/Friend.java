package com.quark.api.auto.bean;


/**
 * Created by XIAO-Y on 2016/8/25.
 * >#
 */
public class Friend implements Comparable<Friend>{
    private String name;
    private String pinyin;
    public Friend(String name) {
        super();
        this.name = name;
        //在数据初始化的时候去处理，而不要在每次用到的时候去处理
//        pinyin = PinYinUtil.getPinYin(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Friend another) {
        return this.getPinyin().compareTo(another.getPinyin());
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


}
