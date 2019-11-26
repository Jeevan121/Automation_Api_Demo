package com.baseutils;

import java.io.File;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUtils {
	
public RestAssuredUtils() {
		
		// TODO Auto-generated constructor stub
	}

	// for post Response with String request json
	  public Response jsonClientPost(String url, String json) {
		  
	    Response response = null;
	    
	    final RequestSpecification httpRequest = RestAssured.given();
	    
	    httpRequest.header("Content-Type", "application/json");
	    httpRequest.body(json);
	    //httpRequest.header("Authorization", authVal);
	    
	    try {
	      response = httpRequest.post(url);
	      System.out.println("==="+response.statusCode());

	      /* System.out.println("==========" + response.asString()); */
	      if (response.statusCode() != 200 && response.statusCode()!=201 ) {
	       System.out.println("Failed : RestAssured error code : " + response.statusCode());
	      }
	      
	      System.out.println("Response JSON Output is===>> " + response.asString());
	    } catch (final Exception e) {
	      // TODO Auto-generated catch block
	      throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
	    }
	    return response;
	  }

	  

	  // for get Response with String request json
	  public Response jsonClientGet(String url, String authVal) {
	    Response response = null;
	    final RequestSpecification httpRequest = RestAssured.given();
	    httpRequest.header("Content-Type", "application/json");
	    httpRequest.header("Authorization", authVal);
	    System.out.println("Request Url : " + url);
	    try {
	      response = httpRequest.get(url);
	      if (response.statusCode() != 200) {
	        System.out.println("Failed : RestAssured error code : " + response.statusCode());
	      }
	      System.out.println("Response JSON Output is===>> " + response.asString());
	    } catch (final Exception e) {
	      // TODO Auto-generated catch block
	      throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
	    }
	    return response;
	  }
	  
	// for get Response with String request json
		  public Response jsonClientGet(String url,String id, String authVal) {
		    Response response = null;
		    final RequestSpecification httpRequest = RestAssured.given();
		    httpRequest.header("Content-Type", "application/json");
		    httpRequest.header("PAYER-ID",id);
		    httpRequest.header("Authorization", authVal);
		    System.out.println("Request Url : " + url);
		    try {
		      response = httpRequest.get(url);
		      if (response.statusCode() != 200) {
		        System.out.println("Failed : RestAssured error code : " + response.statusCode());
		      }
		      System.out.println("Response JSON Output is===>> " + response.asString());
		    } catch (final Exception e) {
		      // TODO Auto-generated catch block
		      throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
		    }
		    return response;
		  }
	  
	  // for post Response with String request json
	  public Response jsonClientPost(String url, String json,String payId, String authVal) {
	    Response response = null;
	    final RequestSpecification httpRequest = RestAssured.given();
	    httpRequest.header("Content-Type", "application/json");
	    httpRequest.header("PAYER-ID",payId);
	    httpRequest.header("Authorization", authVal);
	    httpRequest.body(json);
	    try {
	      response = httpRequest.post(url);
	      /* System.out.println("==========" + response.asString()); */
	      if (response.statusCode() != 200) {
	        System.out.println("Failed : RestAssured error code : " + response.statusCode());
	      }
	      System.out.println("Response JSON Output is===>> " + response.asString());
	    } catch (final Exception e) {
	      // TODO Auto-generated catch block
	      throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
	    }
	    return response;
	  }

	 

}
