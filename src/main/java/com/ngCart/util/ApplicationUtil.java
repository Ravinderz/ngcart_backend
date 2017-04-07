package com.ngCart.util;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

public class ApplicationUtil {

	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(new Date().getTime());
	}
	
	public static JSONObject composeSuccessJsonOuput(String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "OK");
		jsonObject.put("message", message);
		return jsonObject;
	}
	
	public static JSONObject composeFailureJsonOuput(String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", "Failed");
		jsonObject.put("message", message);
		return jsonObject;
	}
}
