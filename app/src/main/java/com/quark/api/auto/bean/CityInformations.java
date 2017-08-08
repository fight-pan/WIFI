package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-10 09:14:01
 *
 */
public class CityInformations{
    /**
     * title : 西城酒吧
     * city_information_id : 3
     * images_01 :
     */

    private String title;
    private int city_information_id;
    private String images_01;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCity_information_id() {
        return city_information_id;
    }

    public void setCity_information_id(int city_information_id) {
        this.city_information_id = city_information_id;
    }

    public String getImages_01() {
        return images_01;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }


//   //信息id
//   public ListCityInformation listCityInformation;
//
//    public ListCityInformation getListCityInformation() {
//        return listCityInformation;
//    }
//
//    public void setListCityInformation(ListCityInformation listCityInformation) {
//        this.listCityInformation = listCityInformation;
//    }
}