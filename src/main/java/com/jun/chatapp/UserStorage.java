package com.jun.chatapp;

import java.util.HashSet;
import java.util.Set;

public class UserStorage {
	private static UserStorage instance;
	private Set<String> users;
	
	public UserStorage() {
		users = new HashSet<String>();
	}
	public static synchronized UserStorage getInstance() {
		if (instance == null) instance = new UserStorage();
		return instance;
	}
	
	public Set<String> getUsers() {
		return users;
	}
	
	public void setUser(String userName) {
		if (users.contains(userName)) throw new RuntimeException("Duplicated user name");
		users.add(userName);
	}
}
