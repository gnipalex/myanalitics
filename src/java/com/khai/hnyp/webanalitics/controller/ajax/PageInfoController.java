package com.khai.hnyp.webanalitics.controller.ajax;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.khai.hnyp.webanalitics.controller.form.AnaliticsForm;
import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ActivityType;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;
import com.khai.hnyp.webanalitics.service.ActivityService;
import com.khai.hnyp.webanalitics.service.ApplicationService;
import com.khai.hnyp.webanalitics.service.RequestInfoService;
import com.khai.hnyp.webanalitics.service.UserSessionService;
import com.khai.hnyp.webanalitics.service.dao.util.ParserUtils;
import com.khai.hnyp.webanalitics.service.transaction.DefaultTransactionManager.ServiceLayerException;

@Controller
public class PageInfoController {

	private static final Logger LOG = Logger
			.getLogger(PageInfoController.class);

	public static final String SCRIPT_WITH_CALLBACK = "script/scriptWithCallback";
	public static final String RESULT_MAP_ATTRIBUTE = "resultMap";
	
	@Autowired
	private RequestInfoService requestInfoService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private UserSessionService userSessionService;
	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "/collect/pageInfo", method = RequestMethod.GET)
	public String pageInfo(AnaliticsForm form, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		logAll(form, request);
		
		if (isFormValid(form)) {
			
			String domain = requestInfoService.retrieveDomain(form.getLocation()); 
			ApplicationModel relatedApplication = applicationService.getForDomain(domain);
			
			if (relatedApplication == null) {
				LOG.info("application not found for '" + domain +"', no action");
				return SCRIPT_WITH_CALLBACK;
			}
			
			Long lastSessionId = ParserUtils.parseLongOrNull(form.getSid());
			UserSessionModel relatedSession = null;
			if (lastSessionId != null) {
				relatedSession = userSessionService.getUserSessionForApplication(relatedApplication, lastSessionId);
			}
			
			ActivityModel currentActivity = populateActivityModel(form);
			try {
				if (relatedSession == null) {
					LOG.info("related session not found, creating new");
					relatedSession = populateSessionModel(form, request);
					long id = userSessionService.createAndAddActivity(relatedApplication,
							relatedSession, currentActivity);
					relatedSession.setId(id);
				} else {
					activityService.createForSession(relatedSession,
							currentActivity);
				}
			} catch (ServiceLayerException e) {
				LOG.error("activity save failed");
			}
			
			Map<String, Object> config = populateConfigForAnaliticsScript(relatedApplication);
			config.put("sid", relatedSession.getId());
			request.setAttribute(RESULT_MAP_ATTRIBUTE, new JSONObject(config));
		}
		
		response.setContentType("text/javascript");
		return SCRIPT_WITH_CALLBACK;
	}
	
	private ActivityModel populateActivityModel(AnaliticsForm form) {
		ActivityModel model = new ActivityModel();
		model.setDate(new Date());
		model.setDomTime(ParserUtils.parseLongOrNull(form.getDomTime()));
		model.setLocation(form.getLocation());
		model.setReferrer(form.getReferrer());
		model.setResponseTime(ParserUtils.parseLongOrNull(form.getResponseTime()));
		model.setType(ActivityType.PAGE);
		return model;
	}
	
	private UserSessionModel populateSessionModel(AnaliticsForm form, HttpServletRequest request) {
		UserSessionModel model = new UserSessionModel();
		String agent = requestInfoService.determineUserAgent(request);
		model.setBrowser(agent);
		model.setCookieEnabled(Boolean.valueOf(form.getCookieEnabled()));
		model.setDate(new Date());
		model.setScreenHeight(ParserUtils.parseIntegerOrNull(form.getScreenHeight()));
		model.setScreenWidth(ParserUtils.parseIntegerOrNull(form.getScreenWidth()));
		return model;
	}
	
	private boolean isFormValid(AnaliticsForm form) {
		if (StringUtils.isEmpty(form.getLocation())) {
			return false;
		}
		return true;
	}
	
	private Map<String, Object> populateConfigForAnaliticsScript(ApplicationModel application) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("activityListeningEnabled", application.isCollectActivityOnPage());
		result.put("activitySendInterval", application.getActivitySendIntervalSec());
		result.put("sessionBreakPage", application.getSessionBreakPage());
		result.put("conversionClass", application.getConversionClass());
		result.put("cookieMaxTimeMin", application.getCookieMaxTimeMin());
		return result;
	}
	
	/*
	private Map<String, Object> populateTemplateConfig(AnaliticsForm form) {
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
		return result;
	}
	*/
	
	private void logAll(AnaliticsForm form, HttpServletRequest request) {
		StringBuilder out = new StringBuilder();

		out.append("[\n");
		out.append("location=" + form.getLocation() + ";\n");
		out.append("referrer=" + form.getReferrer() + ";\n");
		/*
		out.append("responseTime=" + form.getResponseTime() + ";\n");
		out.append("domTime=" + form.getDomTime() + ";\n");
		out.append("cookieEnabled=" + form.getCookieEnabled() + ";\n");
		out.append("cookies sent back="
				+ requestInfoService.cookiePresentsInHeader(request) + ";\n");
		*/
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
