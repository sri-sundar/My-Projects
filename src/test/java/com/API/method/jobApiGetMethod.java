package com.API.method;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.nio.file.Paths;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class jobApiGetMethod extends BaseTest
{

	@Test(priority=1)
	public void jobApiGetData()
	 {
		
			System.out.println(":::::::JobApi::::Get Method::::::");
			RestAssured.baseURI=Url;
			RequestSpecification httprequest=RestAssured.given();
					
			Response response= httprequest.request(Method.GET);
			System.out.println("Get Response :::JobApi Get::"+response.asString());
			
			String NoNaNResponse=response.getBody().asString().replaceAll("NaN","\"5hours ago\"");
			//String NoNaNResponse=response.getBody().asString().replaceAll("NaN","null");
			
			MatcherAssert.assertThat(NoNaNResponse, JsonSchemaValidator.matchesJsonSchemaInClasspath("JobsApiJson//jobsApiSchema.json"));

		}

	 }
		
	
			
			
			
			
			
			
			
			
			
