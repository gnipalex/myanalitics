package com.khai.hnyp.webanalitics.controller.ajax;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.khai.hnyp.webanalitics.controller.form.AnaliticsForm;
import com.khai.hnyp.webanalitics.service.RequestInfoService;

@Controller
public class PageInfoController {

	private static final Logger LOG = Logger
			.getLogger(PageInfoController.class);

	public static final String SCRIPT_WITH_CALLBACK = "script/scriptWithCallback";
	public static final String RESULT_MAP_ATTRIBUTE = "resultMap";
	
	@Autowired
	private RequestInfoService requestInfoService;

	@RequestMapping(value = "/collect/pageInfo", method = RequestMethod.GET)
	public String pageInfo(AnaliticsForm form, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		logAll(form, request);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "ok");
		result.put("activityListeningEnabled", true);
		result.put("activitySendInterval", 20);
		result.put("loginPage", "/login");
		result.put("conversionClass", "addToCartButton");
		if (form.getSid() == null || form.getSid().length() == 0) {
			LOG.info("SID LOST");
			form.setSid(String.valueOf(new Random().nextLong()));
		}
		result.put("sid", form.getSid());
		request.setAttribute(RESULT_MAP_ATTRIBUTE, new JSONObject(result));
		
		response.setContentType("text/javascript");
		return SCRIPT_WITH_CALLBACK;
	}
	
	private void logAll(AnaliticsForm form, HttpServletRequest request) {
		StringBuilder out = new StringBuilder();

		out.append("[\n");
		out.append("location=" + form.getLocation() + ";\n");
		out.append("referrer=" + form.getReferrer() + ";\n");
		out.append("responseTime=" + form.getResponseTime() + ";\n");
		out.append("domTime=" + form.getDomTime() + ";\n");
		out.append("cookieEnabled=" + form.getCookieEnabled() + ";\n");
		out.append("cookies sent back="
				+ requestInfoService.cookiePresentsInHeader(request) + ";\n");
		out.append("client address=" + request.getRemoteAddr() + ";\n");
		out.append("user agent="
				+ requestInfoService.determineUserAgent(request));
		out.append("SID="
				+ form.getSid() + ";\n");
		out.append("]");
		/*
		out.append("\n");
		out.append("Headers:\n");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			out.append(headerName);
			out.append(":");
			out.append(request.getHeader(headerName));
			if (headerNames.hasMoreElements()) {
				out.append("\n");
			}
		}
		*/

		LOG.info(out.toString());
	}

}
