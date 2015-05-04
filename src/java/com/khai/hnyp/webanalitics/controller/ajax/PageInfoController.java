package com.khai.hnyp.webanalitics.controller.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khai.hnyp.webanalitics.controller.form.AnaliticsForm;
import com.khai.hnyp.webanalitics.service.RequestInfoService;

@Controller
public class PageInfoController {
	
	private static final Logger LOG = Logger.getLogger(PageInfoController.class);
	
	@Autowired
	private RequestInfoService requestInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/ajax/pageInfo", method = RequestMethod.GET)
	public void pageInfo(AnaliticsForm form, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		StringBuilder out = new StringBuilder();
		
		out.append("[");
		out.append("location=" + form.getLocation() + ";");
		out.append("referrer=" + form.getReferrer() + ";");
		out.append("time=" + form.getTime() + ";");
		out.append("responseTime=" + form.getResponseTime() + ";");
		out.append("domTime=" + form.getDomTime() + ";");
		out.append("resourcesTime=" + form.getResourcesTime() + ";");
		out.append("cookieEnabled=" + form.getCookieEnabled() + ";");
		out.append("cookies sent back=" + requestInfoService.cookiePresentsInHeader(request) + ";");
		out.append("server sessionId=" + session.getId() + ";");
		out.append("client address=" + request.getRemoteAddr() + ";");
		out.append("user agent=" + requestInfoService.determineUserAgent(request));
		out.append("]");
		
		LOG.info(out.toString());
		
		response.setContentType("text/javascript");
		
		closeSessionIfCookieNotPresent(request, session);
	}
	
	private void closeSessionIfCookieNotPresent(HttpServletRequest request, HttpSession session) {
		if (!requestInfoService.cookiePresentsInHeader(request)) {
			session.invalidate();
			LOG.info("cookie does not present, session invalidated");
		}
	}
	
}
