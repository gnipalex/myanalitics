package com.khai.hnyp.webanalitics.model;

import java.util.Date;

public class UserSessionModel {
	private long id;
	private Date date;
	private String browser;
	private Boolean cookieEnabled;
	private Integer screenHeight;
	private Integer screenWidth;
	private long applicationId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public Boolean getCookieEnabled() {
		return cookieEnabled;
	}
	public void setCookieEnabled(Boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
	public Integer getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}
	public Integer getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
	}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
}