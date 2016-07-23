package edu.bsu.cs222.instagram;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramUserListBuilder {

	public InstagramUserList createCommonFollowsList(String userOneID, String userTwoID)
			throws IOException, JSONException {
		InstagramUserList userOneFollowedUsers = buildFollowedUserList(userOneID);
		InstagramUserList userTwoFollowedUsers = buildFollowedUserList(userTwoID);
		InstagramUserList commonUsers = userOneFollowedUsers.getCommonUsers(userTwoFollowedUsers);
		return buildUserListFromIDs(commonUsers);
	}
	
	private InstagramUserList buildUserListFromIDs(InstagramUserList userIDs) throws JSONException, IOException{
		ArrayList<InstagramUser> partialUsers = userIDs.getUsers();
		InstagramUserList users = new InstagramUserList();
		for (int index = 0; index < partialUsers.size(); index ++){
			InstagramUser user = partialUsers.get(index);
			users.addUser(getUser(user.getUserID()));
		}
		return users;
	}

	private InstagramUserList buildFollowedUserList(String userID) throws JSONException, IOException {
		InputStream inputStream = getUserFollowsInput(userID);
		JSONObject userData = getJSON(inputStream);
		return getAllPagesOfUsers(userData);
	}
	
	private InputStream getUserFollowsInput(String userID) throws IOException{
		return new InstagramConnection().buildUserFollowsInputStream(userID);
	}

	private JSONObject getJSON(InputStream input) throws IOException, JSONException {
		return new InstagramJSONBuilder().buildJSON(input);
	}

	private InstagramUserList getAllPagesOfUsers(JSONObject currentPage) throws JSONException, IOException {
		InstagramUserList users = getUserIDs(currentPage);
		if (nextPageExists(currentPage)){
			JSONObject nextPage = getNextPage(currentPage);
			InstagramUserList restOfUsers = getAllPagesOfUsers(nextPage);
			users.addAllUsers(restOfUsers);
		}
		return users;
	}
	
	public InstagramUserList getUserIDs(JSONObject json) throws JSONException{
		InstagramUserList userIDs = new InstagramUserList();
		JSONArray jsonUsers = json.getJSONArray("data");
		for (int index = 0; index < jsonUsers.length(); index++){
			JSONObject jsonUser = jsonUsers.getJSONObject(index);
			InstagramUser userWithID = InstagramUser.withUserID(jsonUser.getString("id")).andFullname("lol");
			userIDs.addUser(userWithID);
		}
		return userIDs;
	}
	
	public Boolean nextPageExists(JSONObject data) throws JSONException {
		return data.getJSONObject("pagination").has("next_url");
	}
	
	private JSONObject getNextPage(JSONObject currentPage) throws JSONException, IOException{
		URL nextPageURL = getNextURL(currentPage);
		InputStream input = getURLInput(nextPageURL);
		return getJSON(input);
	}
	
	private InputStream getURLInput(URL url) throws IOException{
		return new InstagramConnection().getInputStream(url);
	}

	public URL getNextURL(JSONObject data) throws JSONException, MalformedURLException {
		String nextURLString = data.getJSONObject("pagination").getString("next_url");
		URL nextURL = new URL(nextURLString);
		return nextURL;
	}

	public InstagramUserList createSearchResultsList(String searchRequest) throws IOException, JSONException {
		InputStream searchInput = getSearchResultsInput(searchRequest);
		JSONObject searchResults = getJSON(searchInput);
		return getUsers(searchResults);
	}
	
	private InputStream getSearchResultsInput(String searchRequest) throws IOException{
		return new InstagramConnection().buildSearchResultsInputStream(searchRequest);
	}

	public InstagramUserList getUsers(JSONObject json) throws JSONException, IOException {
		JSONArray users = json.getJSONArray("data");
		InstagramUserList userList = new InstagramUserList();
		for (int index = 0; index < users.length(); index++) {
			String userID = users.getJSONObject(index).getString("id");
			InstagramUser newUser = getUser(userID);
			userList.addUser(newUser);
		}
		return userList;
	}

	private InstagramUser getUser(String id) throws JSONException, IOException {
		InputStream userInformationInput = new InstagramConnection().buildUserInformationInputStream(id);
		if (userInformationInput!=null){
			JSONObject userInformationJSON = getJSON(userInformationInput);
			return buildUser(userInformationJSON);
		}
		return null;
	}

	private InstagramUser buildUser(JSONObject jsonUserInformation) throws JSONException{
		JSONObject jsonUser;
			jsonUser = jsonUserInformation.getJSONObject("data");
			return InstagramUser.//
					withUserID(jsonUser.getString("id"))//
					.andUsername(jsonUser.getString("username"))//
					.andFollowsCount(jsonUser.getJSONObject("counts").getInt("follows"))//
					.andProfilePictureURL(jsonUser.getString("profile_picture"))//
					.andFullname(jsonUser.getString("full_name"));
	}
}