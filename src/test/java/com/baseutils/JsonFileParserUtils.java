package com.baseutils;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
@SuppressWarnings("unchecked")
public class JsonFileParserUtils {
	
	
	public JSONObject createOnBoardAPIRequest(String callBackUrl, String name) {
			  
		  
		    
		    final JSONObject obj_rc = new JSONObject();
		 
		    
		    
		    obj_rc.put("callbackURL", callBackUrl);
		    obj_rc.put("name", name);
		    
		    return obj_rc;
		    
	}
	
	
	
	public String createMakePaymentAPIRequest(ITestContext context) {
		  
		 final JSONObject obj_rc = new JSONObject();
		 obj_rc.put("ifsc", context.getAttribute("ifsc"));
		 obj_rc.put("accountNumber", context.getAttribute("accountNumber"));
		 obj_rc.put("amount", context.getAttribute("amount"));
		 return obj_rc.toJSONString();
	    
	}

	
	public JSONObject createCallBackPayLoadAPIRequest(ITestContext context) {
		  
		final JSONObject obj_rc = new JSONObject();
		final JSONObject bill_obj = new JSONObject();
		
		
		
		obj_rc.put("success",true);
		
		obj_rc.put("transactionId",context.getAttribute("transactionId"));
		
		bill_obj.put("id", context.getAttribute("bills_id"));
		bill_obj.put("amount", context.getAttribute("amount"));
		bill_obj.put("ifsc", context.getAttribute("ifsc"));
		bill_obj.put("billerName", context.getAttribute("billerName"));
		bill_obj.put("accountNumber", context.getAttribute("accountNumber"));

	
	    
	    return obj_rc;
	    
	}
		

}
