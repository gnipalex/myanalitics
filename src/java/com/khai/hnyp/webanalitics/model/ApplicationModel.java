package com.khai.hnyp.webanalitics.model;

public class ApplicationModel {
	private long id;
	private String name;	
	private String domain;
	private long sessionActiveMaxTimeMin;
	private boolean collectActivityOnPage;
	private long activitySendIntervalSec;
	private long cookieMaxTimeMin;
	private long siteCategoryId;
	private long accountId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public long getSessionActiveMaxTimeMin() {
		return sessionActiveMaxTimeMin;
	}
	public void setSessionActiveMaxTimeMin(long sessionActiveMaxTimeMin) {
		this.sessionActiveMaxTimeMin = sessionActiveMaxTimeMin;
	}
	public boolean isCollectActivityOnPage() {
		return collectActivityOnPage;
	}
	public void setCollectActivityOnPage(boolean collectActivityOnPage) {
		this.collectActivityOnPage = collectActivityOnPage;
	}
	public long getActivitySendIntervalSec() {
		return activitySendIntervalSec;
	}
	public void setActivitySendIntervalSec(long activitySendIntervalSec) {
		this.activitySendIntervalSec = activitySendIntervalSec;
	}
	public long getCookieMaxTimeMin() {
		return cookieMaxTimeMin;
	}
	public void setCookieMaxTimeMin(long cookieMaxTimeMin) {
		this.cookieMaxTimeMin = cookieMaxTimeMin;
	}
	public long getSiteCategoryId() {
		return siteCategoryId;
	}
	public void setSiteCategoryId(long siteCategoryId) {
		this.siteCategoryId = siteCategoryId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
}