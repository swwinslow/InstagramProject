package edu.bsu.cs222.instagram;

public class InstagramUser {
	
	public static Builder withUserID(String inputID){
		return new Builder(inputID);
	}
	
	public static final class Builder{	
		private String id;
		private String username;
		private String fullname;
		private int followsCount;
		private String profilePictureURL;
		
		public Builder(String inputID){
			this.id = inputID;
		}
		
		public Builder andUsername(String inputUsername){
			this.username = inputUsername;
			return this;
		}
		
		public Builder andFollowsCount(int inputFollowsCount){
			this.followsCount = inputFollowsCount;
			return this;
		}
		
		public Builder andProfilePictureURL(String urlString){
			this.profilePictureURL = urlString;
			this.profilePictureURL = profilePictureURL.replaceAll("150", "100");
			return this;
		}
		
		public InstagramUser andFullname(String inputFullname){
			this.fullname = inputFullname;
			return new InstagramUser(this);
		}
	}

	private String userID;
	private String username;
	private String userFullname;
	private int userFollowsCount = 0;
	private String profilePictureURL;
	
	private InstagramUser(Builder builder){
		this.userID = builder.id;
		this.username = builder.username;
		this.userFullname = builder.fullname;
		this.userFollowsCount = builder.followsCount;
		this.profilePictureURL = builder.profilePictureURL;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getUserFullname(){
		return userFullname;
	}
	
	public int getUserFollowsCount(){
		return userFollowsCount;
	}
	
	public String getProfilePictureURL(){
		return profilePictureURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userFollowsCount;
		result = prime * result + ((userFullname == null) ? 0 : userFullname.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstagramUser other = (InstagramUser) obj;
		if (userFollowsCount != other.userFollowsCount)
			return false;
		if (userFullname == null) {
			if (other.userFullname != null)
				return false;
		} else if (!userFullname.equals(other.userFullname))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}