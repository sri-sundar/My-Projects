package com.API.method;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import datautils.DataDrivenXcelUtils;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class jobApiPostMethod extends BaseTest
{
	
	
	
@SuppressWarnings("unchecked")
@Test(dataProvider="JobApiPost",priority=2)
  public void jobApiPostData(String JobTitle,String JobCompanyName,String JobLocation,String JobType,String JobPostedtime,String JobDescription,String JobId,String statuscode)
  {
	System.out.println(":::::::JobApi::::Post Method::::::"); 
	RestAssured.baseURI=Url;
	RequestSpecification httprequest=RestAssured.given();
	
	  //JSon Body details
	 
	  JSONObject requestparam=new JSONObject();

	// HashMap<String, String> data=new HashMap<String, String>();
	  requestparam.put("Job Id", JobId);
	 requestparam.put("Job Title", JobTitle);
	 requestparam.put("Job Company Name", JobCompanyName);
	 requestparam.put("Job Location", JobLocation);
	 requestparam.put("Job Type", JobType);
	 requestparam.put("Job Posted time",JobPostedtime);
	 requestparam.put("Job Description",JobDescription);
	 
	//sending header and Body
	 httprequest.header("Content-Type","application/json");
	 httprequest.body(requestparam.toJSONString());
	Response response=httprequest.request(Method.POST);
	String responseBody=response.asString();
	
    System.out.println("Response Body is:" +responseBody);
    int statusCode=response.getStatusCode();
    System.out.println("Status code is: "+statusCode);
    Reporter.log("Post Response Body::JobApi::"+statusCode);
    //After Post GEt REsponse
    Response C=httprequest.request(Method.GET);

    
    // Statuscode Validation
    
    int statuscodeExpected=Integer.parseInt(statuscode);
    Assert.assertEquals(statusCode,statuscodeExpected);
    
    //ReponseBody Validation
//   Assert.assertEquals(C.asString().contains(JobTitle),true);
//   Assert.assertEquals(C.asString().contains(JobCompanyName),true);
//   Assert.assertEquals(C.asString().contains(JobDescription),true);
//   Assert.assertEquals(C.asString().contains(JobLocation),true);
//   Assert.assertEquals(C.asString().contains(JobType),true);
//   Assert.assertEquals(C.asString().contains(JobPostedtime),true);
//   Assert.assertEquals(C.asString().contains(JobId),true);
   
   //Assertion Validation
   
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
			String actualJobposted = path .get("data.\"Job Posted time\"."+jobIdkey+"");
			String actualJobdes = path .get("data.\"Job Description\"."+jobIdkey+"");
			
			//System.out.println(" job title" +actualJobTit);
			
			Assert.assertEquals(actualJobTit,JobTitle);
			Assert.assertEquals(actualJobloc,JobLocation);
			Assert.assertEquals(actualJobcomnam,JobCompanyName);
			Assert.assertEquals(actualJobTyp,JobType);
			Assert.assertEquals(actualJobposted,JobPostedtime);
			Assert.assertEquals(actualJobdes,JobDescription);
				
}		
    if(response.getStatusCode()==400) {
		
    	Response getresponse400 = httprequest.request(Method.GET);
    	Assert.assertNull(getresponse400.body());
    }

  } 
	

 
  @DataProvider(name="JobApiPost")
  public Object[][] JobApiPostdata() throws IOException
  {
	  Object[][] postdata=DataDrivenXcelUtils.GetDataXcel(path, "post");
	  return postdata;
  }

}  
    












//JsonPath jspost=new JsonPath(Sget);
//String JobTitleRes=jspost.get("data.\"Job Title\"");
//System.out.println("Job Title"+JobTitleRes);
//String JobCompanyNameRes=jspost.getString("Job Company Name");
//String JobLocationRes=jspost.getString("Job Location");
//String JobTypeRes=jspost.getString("Job Type");
//String JobPostedtimeRes=jspost.getString("Job Posted time");
//String JobDescriptionRes=jspost.getString("Job Description");
//String JobIdRes=jspost.getString("Job Id");
//System.out.println(" job title" +path.get("data.\"Job Title\".32"));

