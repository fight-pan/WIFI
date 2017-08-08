package com.quark.api.auto.bean;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;

public class ListCustomerBanner implements Serializable{

    public int customer_banner_id;
    public String image_01;
    Bitmap bitmap;
    File file1;
    boolean ismodify; //true  编辑了
    boolean isadd;//增加 按钮
    public String imageFilePath;

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public boolean isadd() {
        return isadd;
    }

    public void setIsadd(boolean isadd) {
        this.isadd = isadd;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public boolean ismodify() {
        return ismodify;
    }

    public void setIsmodify(boolean ismodify) {
        this.ismodify = ismodify;
    }

    public void setCustomer_banner_id(int customer_banner_id) {
        this.customer_banner_id = customer_banner_id;
    }

    public int getCustomer_banner_id() {
        return this.customer_banner_id;
    }

    public void setImage_01(String image_01) {
        this.image_01 = image_01;
    }

    public String getImage_01() {
        return this.image_01;
    }

}
