package com.muhammed.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.muhammed.model.ReviewsApiDao;

@RestController
@RequestMapping("/")
@CrossOrigin
public class APIController {
	
	final String API_ENDPOINT = "https://api.yelp.com/v3/businesses/";
	final String BUSINESS_ID = "vG7ID9YnW3NcEEcwUfipiQ";	// hard coded just because it's asked to do for a favorite restaurant
	
	ReviewsApiDao apiDao = new ReviewsApiDao();
	
	@SuppressWarnings("unchecked")
	@GetMapping
	public String returnReviewData() throws IOException, ParseException {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("Hello from Java Yelp review api at " + timestamp);
		
		URL url = new URL(API_ENDPOINT + BUSINESS_ID + "/reviews");
		String jsonString = apiDao.getJsonFromUrl(url);
		
		// parse the json string into json object and json array to manipulate data
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(jsonString);
		JSONArray reviewsArray = (JSONArray) obj.get("reviews");
		
		JSONArray newReviewsArray = new JSONArray();	// store new data into json array
		
		for(int i = 0; i < reviewsArray.size(); i++) {
			JSONObject aReview = (JSONObject) reviewsArray.get(i);
			
			JSONObject reviewData = new JSONObject();	// create a json object to store each review
			reviewData.put("reviewer's_name",((JSONObject)aReview.get("user")).get("name"));
			reviewData.put("review_content", aReview.get("text"));
			reviewData.put("image_url", ((JSONObject)aReview.get("user")).get("image_url"));
			reviewData.put("rating", aReview.get("rating"));
			
			newReviewsArray.add(reviewData);
			
		}
		
		return newReviewsArray.toJSONString();
	}

}
