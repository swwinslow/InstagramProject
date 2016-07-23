package edu.bsu.cs222.instagram.tests;

import static org.junit.Assert.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import edu.bsu.cs222.instagram.InstagramURLBuilder;

public class InstagramURLTest {
	
	@Test
	public void testGetUserInformationURL() throws MalformedURLException{
		URL testURL = new InstagramURLBuilder().buildUserInformationURL("12345");
		String expectedUrl = "https://api.instagram.com/v1/users/12345/?client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		assertEquals(expectedUrl, testURL.toString());
	}
	
	@Test
	public void testGetSearchResultsURL() throws UnsupportedEncodingException, MalformedURLException{
		URL testURL = new InstagramURLBuilder().buildSearchResultsURL("bradley ridge");
		String expectedUrl = "https://api.instagram.com/v1/users/search?q=bradley+ridge&count=10&client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		assertEquals(expectedUrl,testURL.toString());
	}
	
	@Test
	public void testGetUserFollowsURL() throws MalformedURLException{
		URL testURL = new InstagramURLBuilder().buildUserFollowsURL("12345");
		String expectedUrlString = "https://api.instagram.com/v1/users/12345/follows?client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		assertEquals(expectedUrlString, testURL.toString());
	}
}
