package com.quark.citylistview;

public class SortModel {
	private String province;   //显示的数据
	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
