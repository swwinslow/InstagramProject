package edu.bsu.cs222.instagram.tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import edu.bsu.cs222.instagram.InstagramUser;
import edu.bsu.cs222.instagram.InstagramUserList;

public class InstagramUserListTest {

	private InstagramUserList buildTestList() {
		InstagramUserList testList = new InstagramUserList();
		for (int i = 0; i <= 5; i++) {
			testList.addUser(buildTestUser("" + i));
		}
		return testList;
	}
	
	private InstagramUser buildTestUser(String id) {
		return new InstagramUser.Builder(id)//
				.andUsername("testUser")//
				.andFullname("user test");
	}

	@Test
	public void testGetUserIDs() {
		InstagramUserList testList = buildTestList();
		ArrayList<String> testUserIDList = testList.getUserIDs();
		assertEquals("3", testUserIDList.get(3));
	}
		
	@Test
	public void testGetUsers(){
		InstagramUserList testList = buildTestList();
		ArrayList<InstagramUser> testUsers = testList.getUsers();
		assertEquals(testUsers.get(2).getUserID(), "2");
	}
	
	@Test
	public void testGetUser() {
		InstagramUserList testList = buildTestList();
		InstagramUser testUser = testList.getUser(2);
		assertEquals("2", testUser.getUserID());
	}
	
	@Test
	public void testAddUser(){
		InstagramUserList testList = buildTestList();
		int initialCount = testList.getUsers().size();
		testList.addUser(buildTestUser("6"));
		int finalCount = testList.getUsers().size();
		assertEquals(initialCount, finalCount - 1);
	}
	
	@Test
	public void testAddNullUser(){
		InstagramUserList testList = buildTestList();
		int initialCount = testList.getUsers().size();
		testList.addUser(null);
		int finalCount = testList.getUsers().size();
		assertEquals(initialCount, finalCount);
	}
	
	@Test
	public void testAddAllUsers(){
		InstagramUserList testList = buildTestList();
		int initialCount = testList.getUsers().size();
		testList.addAllUsers(buildTestList());
		int finalCount = testList.getUsers().size();
		assertEquals(initialCount, finalCount/2);
	}
	
	@Test
	public void testGetCommonUsers(){
		InstagramUserList testList = buildTestList();
		InstagramUserList otherTestList = buildTestList();
		otherTestList.addUser(buildTestUser("lol"));
		testList = testList.getCommonUsers(otherTestList);
		assertEquals(6, testList.getUserIDs().size());
	}
}