package edu.bsu.cs222.instagram.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import edu.bsu.cs222.instagram.InstagramJSONBuilder;
import edu.bsu.cs222.instagram.InstagramUser;
import edu.bsu.cs222.instagram.InstagramUserList;
import edu.bsu.cs222.instagram.InstagramUserListBuilder;

public class InstagramUserListBuilderTest {

	private InputStream testInputStream;

	private InputStream buildTestFollowsInputStream() throws FileNotFoundException{
		File testData = new File("testData/sampleuserFollowsRequestData.txt");
		testInputStream = (InputStream) new FileInputStream(testData);
		return testInputStream;
	}
	
	private JSONObject buildTestJSON() throws FileNotFoundException, JSONException, IOException{
		return new InstagramJSONBuilder().buildJSON(buildTestFollowsInputStream());
	}
	
	@Test
	public void testSinglePageOfUsers() throws FileNotFoundException, JSONException, IOException{
		JSONObject testJSON = buildTestJSON();
		InstagramUserList testUsers = new InstagramUserListBuilder().getUserIDs(testJSON);
		String userID = testUsers.getUser(49).getUserID();
		System.out.println(userID);
		assertEquals(userID,"905578535");
	}

	@Test
	public void testNextURLExists() throws JSONException, IOException {
		JSONObject testJSON = buildTestJSON();
		InstagramUserListBuilder testBuilder = new InstagramUserListBuilder();
		Assert.assertTrue(testBuilder.nextPageExists(testJSON));
	}

	@Test
	public void testGetNextURL() throws JSONException, IOException {
		JSONObject testJSON = buildTestJSON();
		InstagramUserListBuilder testBuilder = new InstagramUserListBuilder();
		String nextURLActual = testBuilder.getNextURL(testJSON).toString();
		String expectedURL = "https://api.instagram.com/v1/users/47562703/follows?cursor=AQD7H2_W3vBLZ0DHVwYDLKPGilkqywxp9Zu2uD_kI9O1-IE7f-JU86Z_m2cjAmzZj9hH-eOQ5NJ5GTttqQFSWDY2CxTJjbsLJF_h101N-QBbt26Kd9DRv5v39schekOs3qdbRqv8HNEObRvQUtVWUC67-7b-jIq_kRwMHdoW9JFQSHRqchgtD_IFKED0vw6cEMg7RBxEkOnbOEi_yyK-D9Pw&client_id=11851da6ca1c4531a36f3af7bfbdebfb";
		Assert.assertEquals(expectedURL, nextURLActual);
	}

	@Test
	public void testGetSinglePageOfCompleteUsers() throws FileNotFoundException, JSONException, IOException{
		JSONObject testJSON = buildTestJSON();
		InstagramUserList testList = new InstagramUserListBuilder().getUsers(testJSON);
		InstagramUser testUser = testList.getUser(4);
		int followsCount = testUser.getUserFollowsCount();
		assertNotNull(followsCount);
	}
}