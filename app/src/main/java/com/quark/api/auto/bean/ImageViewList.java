package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * Created by pan on 2016/8/4 0004.
 * >#
 * >#
 */
public class ImageViewList implements Serializable{
    String iv;

    public ImageViewList(String iv) {
        this.iv = iv;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
