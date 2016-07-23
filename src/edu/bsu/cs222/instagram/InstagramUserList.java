package edu.bsu.cs222.instagram;

import java.util.ArrayList;

public class InstagramUserList {	

	private ArrayList<InstagramUser> users = new ArrayList<InstagramUser>();
	
	public ArrayList<String> getUserIDs(){
		ArrayList<String> idList = new ArrayList<String>();
		for (InstagramUser eachUser: users){
			idList.add(eachUser.getUserID());
		}
		return idList;
	}
	
	public ArrayList<InstagramUser> getUsers(){
		return users;
	}
	
	public InstagramUser getUser(int index){
		return users.get(index);
	}
	
	public void addUser(InstagramUser user){
		if (user!=null){
			users.add(user);
		}
	}
	
	public void addAllUsers(InstagramUserList oldUserList){
		users.addAll(oldUserList.getUsers());
	}
	
	public InstagramUserList getCommonUsers(InstagramUserList otherUsers){
		InstagramUserList safeCopy =  new InstagramUserList();
		safeCopy.addAllUsers(this);
		this.users.retainAll(otherUsers.users);
		return this;
	}
	
	public int size(){
		return users.size();
	}
}