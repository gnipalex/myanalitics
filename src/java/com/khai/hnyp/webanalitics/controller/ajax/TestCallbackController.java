package com.khai.hnyp.webanalitics.controller.ajax;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestCallbackController {
	
	public static final String SCRIPT_WITH_CALLBACK = "script/scriptWithCallback"; 
	public static final String CALLBACK_ATTRIBUTE = "callback";
	public static final String RESULT_MAP_ATTRIBUTE = "resultMap";
	
	@RequestMapping("/ajax/getServerTime")
	public String getServerTime(String callback, Model model) {
		System.out.println("getServerTime, callback=" + callback);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("time", new Date());
		
		model.addAttribute(CALLBACK_ATTRIBUTE, callback);
		model.addAttribute(RESULT_MAP_ATTRIBUTE, new JSONObject(result));
		
		return SCRIPT_WITH_CALLBACK;
	}
}
