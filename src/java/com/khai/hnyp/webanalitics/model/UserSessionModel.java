package com.khai.hnyp.webanalitics.model;

import java.util.Date;

public class UserSessionModel {
	private long id;
	private Date date;
	private String browser;
	private boolean cookieEnabled;
	private int screenHeight;
	private int screenWidth;
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
	public boolean isCookieEnabled() {
		return cookieEnabled;
	}
	public void setCookieEnabled(boolean cookieEnabled) {
		this.cookieEnabled = cookieEnabled;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
	
	
}
