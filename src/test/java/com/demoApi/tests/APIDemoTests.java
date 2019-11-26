package com.demoApi.tests;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.baseutils.AutoConstants;
import com.baseutils.JsonFileParserUtils;
import com.baseutils.JwtUtils;
import com.baseutils.RestAssuredUtils;
import com.demo.dataproviders.DemoApiDataProvider;
import com.mongodb.util.JSON;

import io.restassured.response.Response;

public class APIDemoTests {

	JsonFileParserUtils jsonFileParser = new JsonFileParserUtils();
	RestAssuredUtils restAssuredUtils = new RestAssuredUtils();
	JwtUtils jwt = new JwtUtils();
	
	
	@Test(dataProviderClass = DemoApiDataProvider.class, dataProvider = "onBoardingApi", alwaysRun = true,priority = 1)
	public void onBoardDemoApiTest(ITestContext context,String endPoint,String	name,String onBoardingEndPoint,String message) {
		
		// framing the json request for the Onboarding  API and then reading the required data from the excel file
		JSONObject onBoardApiJsonRequest =jsonFileParser.createOnBoardAPIRequest(endPoint, name);
		String framedJsonRequestForOnBoardApi=JSON.parse(onBoardApiJsonRequest.toString()).toString();
		
		//hitting to the server through framed json request and then retrieving the onboard api end point from the excel sheet  
		Response onBoardApiResponse=restAssuredUtils.jsonClientPost(onBoardingEndPoint, framedJsonRequestForOnBoardApi);
		
		//retrieving the success message from the server  and validating the against the actual response and expected data kept in the excel
		String successMsg = onBoardApiResponse.jsonPath().getString(AutoConstants.message);
		Assert.assertEquals(successMsg, message,"uanble to hit to the server, success message from server didn't got");
	
		//parsing the onboarding api response and kept in the map
		Map<Object,Object> dmap=onBoardApiResponse.jsonPath().getMap(AutoConstants.data);
		
		// jwt secret key and pay id from the response
		String jwtSecretkeyRes=dmap.get(AutoConstants.jwtSecret).toString();
		String payIdRes=dmap.get(AutoConstants.payerAppID).toString();
		
		System.out.println("the payer app id is::"+payIdRes);
		System.out.println("the jwt secret key is::"+jwtSecretkeyRes);
		
		//retrieving the call back url and name from the response and validating the against the excepted data
		String callBackRes=dmap.get(AutoConstants.callbackURL).toString();
		String nameRes=dmap.get(AutoConstants.name).toString();
		Assert.assertEquals(callBackRes, endPoint,"call back url is not matching with the excepted data");
		Assert.assertEquals(nameRes, name,"name is not matching with the excepted data");
		
		//we have to send pay id and jwt secret key to the pay bills api service and then storing these 2 values in the ITestContext it's testng 
		//annotation.we can pass data from 1 test method to another.
		context.setAttribute(AutoConstants.payerAppID, payIdRes);
		context.setAttribute(AutoConstants.jwtSecret, jwtSecretkeyRes);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(dataProviderClass = DemoApiDataProvider.class, dataProvider = "fetchBillAPITest", alwaysRun = true,priority = 2)
	public void fetchBillAPITest(ITestContext context,String endPointFetchBills,String message) {
		
		System.out.println("11111111111::"+context.getAttribute("id"));
		
		System.out.println("222222222222::"+context.getAttribute(AutoConstants.jwtSecret));
		
		// got payer app ID and the jwtSecret from the on boarding api response in this test method
		String jwtSecretkey=context.getAttribute(AutoConstants.jwtSecret).toString();
		String payerAppId = context.getAttribute(AutoConstants.payerAppID).toString();
		
		//creating the jwt token from the jwtSecretKey and written the utility class for creating the jwt token
		String jwtToket = jwt.createJwt(jwtSecretkey);
		
		System.out.println("the generated jwt token is::"+jwtToket);
		//hitting the fetching bill api with just calculated  jwt token  and then payer app id 
		Response fetchBillApiResponse=restAssuredUtils.jsonClientGet(endPointFetchBills,payerAppId,AutoConstants.bearer+jwtToket);
		
		System.out.println("the response 2nd is :::"+fetchBillApiResponse.asString());
		
		//retrieving the success message from the server  and validating the against the actual response and expected data.
		String successMsg = fetchBillApiResponse.jsonPath().getString(AutoConstants.message);
		Assert.assertEquals(successMsg, message,"uanble to hit to the server, success message from server didn't got");
	
		//parsing the fecth bill api response int hs list map
		List<Map<?,?>> datas = (List<Map<?, ?>>) fetchBillApiResponse.jsonPath().getMap(AutoConstants.data).get(AutoConstants.bills);

		// retrieving the amount,ifsc and account number from the datas map and passing to the next Make Payment API Test
		context.setAttribute("amount", datas.get(0).get(AutoConstants.amount));
		context.setAttribute("ifsc", datas.get(0).get(AutoConstants.ifsc));
		context.setAttribute("accountNumber", datas.get(0).get(AutoConstants.accountNumber));
		context.setAttribute(AutoConstants.jwtToket,jwtToket);
		context.setAttribute(AutoConstants.payerAppID,payerAppId);
		

		
	
	}
	
	@Test(dataProviderClass = DemoApiDataProvider.class, dataProvider = "makePaymentAPITest", alwaysRun = true,priority = 3)
	public void makePaymentAPITest(ITestContext context,String endPointMakePaymentApi,String message) {
	
		//framing the json request file for the make payment api
		String makePaymentApiJsonRequest =jsonFileParser.createMakePaymentAPIRequest(context);
		
		// getting the jwt token from the fetch bills api request
		String jwtToket = context.getAttribute(AutoConstants.jwtToket).toString();
		
		//hitting to the makepayment api and getting the response
		Response makePaymentRes=restAssuredUtils.jsonClientPost(endPointMakePaymentApi,makePaymentApiJsonRequest,context.getAttribute(AutoConstants.payerAppID).toString(),AutoConstants.bearer+jwtToket);
		
		//retrieving the success message from the server  and validating the against the actual response and expected data.
		String successMsg = makePaymentRes.jsonPath().getString(AutoConstants.message);
		Assert.assertEquals(successMsg, message,"uanble to hit to the server, success message from server didn't got");
		
		//When one or more of amount, IFSC & accountNumber don't match to a bill
		 context.setAttribute("ifsc", "jhagsjghads");
		  
		  String negatIvePaymentApiJsonRequest=jsonFileParser.createMakePaymentAPIRequest(context);
		  
		  Response negativeMakePaymentRes=restAssuredUtils.jsonClientPost(endPointMakePaymentApi,
		  negatIvePaymentApiJsonRequest,context.getAttribute(AutoConstants.payerAppID).
		  toString(),AutoConstants.bearer+jwtToket);
		  
		  System.out.println("the response got from negative  is ::"+negativeMakePaymentRes.
		  asString());
		 
		//retrieving the success message from the server  and validating the against the actual response and expected data.
		  String negativeSuccessMsg = makePaymentRes.jsonPath().getString(AutoConstants.message);
		  Assert.assertEquals(negativeSuccessMsg, message,"uanble to hit to the server, success message from server didn't got");
		
	}
}
