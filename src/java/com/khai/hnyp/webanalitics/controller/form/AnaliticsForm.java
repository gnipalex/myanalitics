package com.khai.hnyp.webanalitics.controller.form;

public class AnaliticsForm {
	
	private String location;
	private String referrer;
	private String time;
	private String responseTime;
	private String domTime;
	private String resourcesTime;
	private String cookieEnabled;
	
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getDomTime() {
		return domTime;
	}
	public void setDomTime(String domTime) {
		this.domTime = domTime;
	}
	public String getResourcesTime() {
		return resourcesTime;
	}
	public void setResourcesTime(String resourcesTime) {
		this.resourcesTime = resourcesTime;
	}
	public String getCookieEnabled() {
		return cookieEnabled;
	}
	public void setCookieEnabled(String cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
}
