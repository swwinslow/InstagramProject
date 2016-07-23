package edu.bsu.cs222.instagram.tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import edu.bsu.cs222.instagram.InstagramJSONBuilder;

public class InstagramJSONBuilderTest {

	@Test 
	public void testBuildDataString() throws IOException{
		File testData = new File("testData/sampleSearchResultsData.txt");
		InputStream testDataInputStream = (InputStream) new FileInputStream(testData);
		String testDataString = new InstagramJSONBuilder().buildDataString(testDataInputStream);
		String expectedString = "some sort of fake data";
		assertEquals(testDataString, expectedString);
	}	
	
	@Test
	public void testBuildJSON() throws IOException, JSONException{
		File testData = new File("testData/sampleUserFollowsRequestData.txt");
		InputStream testDataInputStream = (InputStream) new FileInputStream(testData);
		JSONObject testJSON = new InstagramJSONBuilder().buildJSON(testDataInputStream);
		String testID = testJSON.getJSONArray("data").getJSONObject(0).getString("id");
		assertEquals(testID, "274431224");
	}
}