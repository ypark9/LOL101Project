package com.entrepreneur.lolproject.bean;

public class HistoryBean {

	private int imageId;
	private String summonerId;
	private String teamId;
	
	public HistoryBean(int imageId, String summonerId, String teamId) {
		this.imageId = imageId;
		this.summonerId = summonerId;
		this.teamId = teamId;
	}
	
	public int getImageId(){
		return imageId;
	}
	
	public void setImageId(int imageId){
		this.imageId = imageId;
	}
	
	public String getDesc(){
		return teamId;
	}
	public void setDesc(String desc){
		this.teamId = desc;
	}
	
	public String getTitle(){
		return summonerId;
	}
	
	public void setTitle(String title){
		this.summonerId = title;
		
	}
	
	public String toString(){
		return summonerId;
	}
}
