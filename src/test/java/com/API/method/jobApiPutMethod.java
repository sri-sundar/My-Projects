package com.API.method;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import datautils.DataDrivenXcelUtils;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobApiPutMethod extends BaseTest
{

	
  @Test(dataProvider="JobApiPut",priority=3)
 
  public void jobApiPutData(String JobTitle,String JobCompanyName,String JobLocation,String JobType,String JobId,String statuscode)
  {
	  System.out.println(":::::::JobApi::::Put Method::::::");
	 RestAssured.baseURI=Url;
	 RequestSpecification httprequest=RestAssured.given();
	 //creating Json Object to send body details
	 JSONObject requestparam=new JSONObject();

	// HashMap<String, String> data=new HashMap<String, String>();
	
	 requestparam.put("Job Title", JobTitle);
	 requestparam.put("Job Company Name", JobCompanyName);
	 requestparam.put("Job Location", JobLocation);
	 requestparam.put("Job Type", JobType);
	 requestparam.put("Job Id", JobId);
	 
	 //sending header and Body
	httprequest.header("Content-Type","application/json");
	httprequest.body(requestparam.toJSONString());
	Response response=httprequest.request(Method.PUT);
	String responseBody=response.getBody().asPrettyString();
	
    System.out.println("Response Body is:" +responseBody);
    int statusCode=response.getStatusCode();
    System.out.println("Status code is: "+statusCode);
    Response responseG=httprequest.request(Method.GET);
    //Reporter.log("Put Response Body"+statusCode);
    
if(response.getStatusCode()==200) {
		
		Response getresponse = httprequest.request(Method.GET);
		JsonPath path = JsonPath.from(getresponse.getBody().asString().replaceAll("NaN", "null"));
	
		Map<String,String> jobids =	path.get("data.\"Job Id\"");
		//System.out.println(" jobids " + jobids);
		
		
		String jobIdkey=null;
		for (String key : jobids.keySet())
		{
			// getting key for the actual jobId value
			String ids = jobids.get(key);
			
			if(ids!=null) {
			if(jobids.get(key).equals(JobId)){
				
				 jobIdkey=key;
				 break;
			
		}
		}
		
	}
			String actualJobTit = path .get("data.\"Job Title\"."+jobIdkey+"");
			String actualJobloc = path .get("data.\"Job Location\"."+jobIdkey+"");
			String actualJobcomnam = path .get("data.\"Job Company Name\"."+jobIdkey+"");
			String actualJobTyp =path .get("data.\"Job Type\"."+jobIdkey+"");
			
			Assert.assertEquals(actualJobTit,JobTitle);
			Assert.assertEquals(actualJobloc,JobLocation);
			Assert.assertEquals(actualJobcomnam,JobCompanyName);
			Assert.assertEquals(actualJobTyp,JobType);
			
}		
    
    
//   
//    int statuscodeExpected=Integer.parseInt(statuscode);
//    Assert.assertEquals(statusCode, statuscodeExpected);
//    Reporter.log("StatusCode Assertion passed");
//    Assert.assertEquals(responseG.getBody().asString().contains(JobCompanyName),true);
//    Reporter.log("JobCompanyName Assertion passed");
//    Assert.assertEquals(responseG.getBody().asString().contains(JobLocation),true);
//    Reporter.log("Job Location Assertion passed");
//    Assert.assertEquals(responseG.getBody().asString().contains(JobType),true);
//    Assert.assertEquals(responseG.getBody().asString().contains(JobId),true);

  }
 @DataProvider(name="JobApiPut")
  public Object[][] JobApiPutdata() throws IOException
  {
	  Object[][] postdata=DataDrivenXcelUtils.GetDataXcel(path,"put");
	  return postdata;
  }

}



//// fetching ResponseBody 
//JsonPath jspost=response.jsonPath();
//String JobTitleRes=jspost.getString("Job Title");
//String JobCompanyNameRes=jspost.getString("Job Company Name");
//String JobLocationRes=jspost.getString("Job Location");
//String JobTypeRes=jspost.getString("Job Type");
//String JobIdRes=jspost.getString("Job Id");
//
////ResponseBody Validation
//Assert.assertEquals(JobTitleRes,JobTitle);
//Assert.assertEquals(JobCompanyNameRes,JobCompanyName);
//Assert.assertEquals(JobLocationRes,JobLocation);
//Assert.assertEquals(JobTypeRes,JobType);
//Assert.assertEquals(JobTitleRes,JobTitle);
//Assert.assertEquals(JobIdRes,JobId);
//
//
//// Statuscode Validation
//int statuscodeExpected=Integer.parseInt(statuscode);
//Assert.assertEquals(statusCode, statuscodeExpected);
//
