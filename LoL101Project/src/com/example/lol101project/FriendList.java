package com.example.lol101project;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("FriendList")
public class FriendList extends ParseObject {
	public FriendList() {

	}

	public boolean isCompleted() {
		return getBoolean("completed");
	}

	public void setCompleted(boolean complete) {
		put("completed", complete);
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}
}
