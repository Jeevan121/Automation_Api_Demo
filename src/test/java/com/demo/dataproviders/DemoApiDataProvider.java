package com.demo.dataproviders;

import java.io.File;

import org.testng.annotations.DataProvider;

import com.baseutils.ExcelUtilities;



public class DemoApiDataProvider {
	
	 @DataProvider(name = "onBoardingApi")
	  public static Object[][] onBoardingApi() {
	    final Object[][] objReturn = ExcelUtilities.getTableArray(
	        System.getProperty("user.dir") + File.separator+"TestData"+File.separator+"O1_Auto_TestData_Nightly_WorkSpace.xls", "demo_api",
	        "onBoardingApi");
	    return objReturn;
	  }
	 
	 @DataProvider(name = "fetchBillAPITest")
	  public static Object[][] fetchBillAPITest() {
	    final Object[][] objReturn = ExcelUtilities.getTableArray(
	        System.getProperty("user.dir") + File.separator+"TestData"+File.separator+"O1_Auto_TestData_Nightly_WorkSpace.xls", "demo_api",
	        "fetchBillAPITest");
	    return objReturn;
	  }
	 
	 @DataProvider(name = "makePaymentAPITest")
	  public static Object[][] makePaymentAPITest() {
	    final Object[][] objReturn = ExcelUtilities.getTableArray(
	        System.getProperty("user.dir") + File.separator+"TestData"+File.separator+"O1_Auto_TestData_Nightly_WorkSpace.xls", "demo_api",
	        "makePaymentAPITest");
	    return objReturn;
	  }
	 
	 
	 
}
