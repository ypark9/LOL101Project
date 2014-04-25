package com.example.lolproject.bean;

public class CurrentlyNoGameBean {

	private String teamId;
	
	public CurrentlyNoGameBean(String teamId) {

		this.teamId = teamId;
	}

	public String getDesc(){
		return teamId;
	}
	public void setDesc(String desc){
		this.teamId = desc;
	}

}
