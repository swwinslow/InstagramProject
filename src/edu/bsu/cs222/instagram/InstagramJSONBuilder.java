package edu.bsu.cs222.instagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramJSONBuilder {

	public JSONObject buildJSON(InputStream input) throws JSONException, IOException {
		return new JSONObject(buildDataString(input));
	}

	public String buildDataString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
		bufferedReader.close();
		return stringBuilder.toString();
	}
}