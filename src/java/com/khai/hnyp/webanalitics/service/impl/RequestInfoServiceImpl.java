package com.khai.hnyp.webanalitics.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.khai.hnyp.webanalitics.service.RequestInfoService;
import com.mattunderscore.http.headers.UnParsableHeaderException;
import com.mattunderscore.http.headers.useragent.UserAgent;
import com.mattunderscore.http.headers.useragent.details.UserAgentDetail;
import com.mattunderscore.http.headers.useragent.details.application.Browser;
import com.mattunderscore.http.headers.useragent.parser.UserAgentParser;

public class RequestInfoServiceImpl implements RequestInfoService {

	private static final Logger LOG = Logger.getLogger(RequestInfoServiceImpl.class);
	
	@Override
	public boolean cookiePresentsInHeader(HttpServletRequest request) {
		return request.getHeader("Cookie") != null;
	}

	@Override
	public String determineUserAgent(HttpServletRequest request) {
		StringBuilder output = new StringBuilder();
		try {
			UserAgentParser userAgentParser = new UserAgentParser();
			UserAgent userAgent = userAgentParser.parseField(request.getHeader("User-Agent"));
			for (UserAgentDetail detail : userAgent.details()) {
				if (detail instanceof Browser) {
					output.append(detail.name() + " " + detail.version() + ";");
					break;
				}
			}
		} catch (UnParsableHeaderException e) {
			LOG.error("User-Agent wrong format", e);
		}
		return output.toString();
	}

}
