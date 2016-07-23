package edu.bsu.cs222.instagram;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class InstagramConnection {

	public InputStream buildUserInformationInputStream(String userID) throws IOException {
		URL url = new InstagramURLBuilder().buildUserInformationURL(userID);
		return getInputStream(url);
	}
	
	public InputStream buildSearchResultsInputStream(String searchRequest) throws IOException{
		URL url = new InstagramURLBuilder().buildSearchResultsURL(searchRequest);
		return getInputStream(url);
	}
	
	public InputStream buildUserFollowsInputStream(String userID) throws IOException{
		URL url = new InstagramURLBuilder().buildUserFollowsURL(userID);
		return getInputStream(url);
	}

	public InputStream getInputStream(URL url) throws IOException {
		URLConnection newConnection = url.openConnection();
		newConnection.connect();
		if (connectionExists(newConnection)){
			return newConnection.getInputStream();
		}
		return null;
	}
	
	private Boolean connectionExists(URLConnection connection) throws IOException{
		HttpURLConnection httpConnection = (HttpURLConnection) connection;
		if (httpConnection.getResponseCode() >= 400) {
			return false;
		}
		return true;
	}	
}