package com.khai.hnyp.webanalitics.controller.form;

public class AnaliticsForm {
	
	private String location;
	private String referrer;
	private String responseTime;
	private String domTime;
	private String cookieEnabled;
	private String sid;
	private String json;
	
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
	public String getCookieEnabled() {
		return cookieEnabled;
	}
	public void setCookieEnabled(String cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
}
