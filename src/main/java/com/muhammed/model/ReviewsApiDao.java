package com.muhammed.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReviewsApiDao {
	
	public String getJsonFromUrl(URL url) throws IOException {
		final String APIKEY = "KTF7B9oZjl1fYK_AAxGLGPHKSzS03RHw6nRp2Nt_VML3yh8uLvG_p6J21NQ25Y65MMD_9qXoiW2LSy3Yxmu_UPidE3j5NqPdiF7Y1M7PKQtOGpMY9OUGAM58z_YQXnYx";
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "Bearer " + APIKEY);
		
		if(connection.getResponseCode() != 200) {
			System.out.println(url);
			throw new RuntimeException("Failed : HTTP error code: " + connection.getResponseCode());
		}
		
		BufferedReader responseContentReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output = "";
		String jsonString = "";
		
		while((output = responseContentReader.readLine()) != null) {
			jsonString += output;
		}
		connection.disconnect();
		
		return jsonString;
	}

}
