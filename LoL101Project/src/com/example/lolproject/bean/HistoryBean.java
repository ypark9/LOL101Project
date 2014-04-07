package com.example.lolproject.bean;

public class HistoryBean {

	private int imageId;
	private String title;
	private String desc;
	
	public HistoryBean(int imageId, String title, String desc) {
		this.imageId = imageId;
		this.title = title;
		this.desc = desc;
	}
	
	public int getImageId(){
		return imageId;
	}
	
	public void setImageId(int imageId){
		this.imageId = imageId;
	}
	
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
		
	}
	
	public String toString(){
		return title + "\n" + desc;
	}
}
