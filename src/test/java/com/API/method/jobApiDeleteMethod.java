package com.API.method;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import datautils.DataDrivenXcelUtils;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobApiDeleteMethod extends BaseTest
{
	//public static String Url="https://jobs123.herokuapp.com/Jobs";
	//public static String path="C:\\cucumber\\Job-ApiTestingRestAssured\\src\\test\\resources\\JobApiExcel.xlsx";
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider="JobApiDelete",priority=4)
	public void jobApiDeleteData(String JobId,String statuscode)
	 {
			
			System.out.println(":::::::JobApi::::Delete Method::::::");
			RestAssured.baseURI=Url;
			RequestSpecification httprequest=RestAssured.given();
			JSONObject requestparam=new JSONObject();
			//int JobIdi=Integer.parseInt(JobId);
			requestparam.put("Job Id",JobId);
		
			
			httprequest.header("Content-Type","application/json");
			httprequest.body(requestparam.toJSONString());
			Response response=httprequest.request(Method.DELETE);
			System.out.print("Response  Body::job Api :::"+ response.getBody().asPrettyString());
			
			// Statuscode Validation
		    int statuscodeExpected=Integer.parseInt(statuscode);
		    Assert.assertEquals(response.getStatusCode(), statuscodeExpected);
		    //Assert.assertNull(response.getBody());
		   
	 }
	@DataProvider(name="JobApiDelete")
	  public Object[][] JobApiDeletedata() throws IOException
	  {
		  Object[][] postdata=DataDrivenXcelUtils.GetDataXcel(path,"delete");
		  return postdata;
	  }	
}