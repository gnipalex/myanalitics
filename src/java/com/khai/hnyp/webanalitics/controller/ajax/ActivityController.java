package com.khai.hnyp.webanalitics.controller.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khai.hnyp.webanalitics.controller.form.ActivityForm;

@Controller
public class ActivityController {
	private static final Logger LOG = Logger.getLogger(ActivityController.class); 
	
	public static final String SCRIPT_WITH_CALLBACK = "script/scriptWithCallback";

	
	@RequestMapping(value = "/collect/activity")
	public String collectActivity(ActivityForm form, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("=====Activity");
		LOG.info("json:" + form.getJson());
		LOG.info("sid:" + form.getSid());
		response.setContentType("text/javascript");
		return SCRIPT_WITH_CALLBACK;
	}
	
	
}
