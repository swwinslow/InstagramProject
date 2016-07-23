package edu.bsu.cs222.instagram.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import edu.bsu.cs222.instagram.InstagramUser;

public class InstagramUserTest {

	@Test
	public void testUserBuilderID(){
		assertEquals("testID", buildTestUser().getUserID());
	}
	
	@Test
	public void testUserBuilderUsername(){
		assertEquals("testUsername", buildTestUser().getUsername());
	}
	
	@Test
	public void testUserBuilderFullname(){
		assertEquals("testFullname", buildTestUser().getUserFullname());

	}
	
	@Test
	public void testEquals(){
		InstagramUser userOne = InstagramUser.withUserID("lol").andFollowsCount(44).andFullname("my name");
		InstagramUser userTwo = InstagramUser.withUserID("lol").andFollowsCount(44).andFullname("my name");
		assertTrue(userOne.equals(userTwo));
	}
	
	private InstagramUser buildTestUser(){
		InstagramUser testUser = InstagramUser.withUserID("testID").//
				andUsername("testUsername").//
				andFullname("testFullname");
		return testUser;
	}
}
