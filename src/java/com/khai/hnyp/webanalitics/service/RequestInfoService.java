package com.khai.hnyp.webanalitics.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestInfoService {
	boolean cookiePresentsInHeader(HttpServletRequest request);
	String determineUserAgent(HttpServletRequest request);
}
