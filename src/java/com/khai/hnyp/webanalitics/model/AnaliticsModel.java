package com.khai.hnyp.webanalitics.model;

import java.util.Date;

public class AnaliticsModel {
	private long id;
	private String location;
	private String referrer;
	private Date clientDate;
	private long responseTime;
	private long domTime;
	private long resourcesTime;
	private boolean cookieEnabled;
	private String sessionId;
	private Date date;
	private String userAgent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public Date getClientDate() {
		return clientDate;
	}
	public void setClientDate(Date clientDate) {
		this.clientDate = clientDate;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public long getDomTime() {
		return domTime;
	}
	public void setDomTime(long domTime) {
		this.domTime = domTime;
	}
	public long getResourcesTime() {
		return resourcesTime;
	}
	public void setResourcesTime(long resourcesTime) {
		this.resourcesTime = resourcesTime;
	}
	public boolean isCookieEnabled() {
		return cookieEnabled;
	}
	public void setCookieEnabled(boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
