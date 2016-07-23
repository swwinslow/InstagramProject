package edu.bsu.cs222.instagram;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class InstagramURLBuilder {

	public URL buildUserInformationURL(String inputID) throws MalformedURLException {
		String userInformationURLString = "https://api.instagram.com/v1/users/" + inputID
				+ "/?client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		return buildURL(userInformationURLString);
	}

	public URL buildSearchResultsURL(String searchRequest) throws UnsupportedEncodingException, MalformedURLException {
		searchRequest = URLEncoder.encode(searchRequest, "UTF-8");
		String searchURL = "https://api.instagram.com/v1/users/search?q=" + searchRequest
				+ "&count=10&client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		return buildURL(searchURL);
	}

	public URL buildUserFollowsURL(String userID) throws MalformedURLException {
		String followsURLString = "https://api.instagram.com/v1/users/" + userID
				+ "/follows?client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		return buildURL(followsURLString);
	}

	private URL buildURL(String inputURL) throws MalformedURLException {
		return new URL(inputURL);
	}
}