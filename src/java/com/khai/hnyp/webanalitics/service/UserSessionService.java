package com.khai.hnyp.webanalitics.service;

import java.util.Date;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;

public interface UserSessionService {
	long getUsersCountForPerion(ApplicationModel application, Date leftBorder, Date rightBorder);
	long getUsersCountToday(ApplicationModel application);
	long getActiveUsersCount(ApplicationModel application);
	void removeOlderDate(ApplicationModel application, Date date);
	long create(ApplicationModel application, UserSessionModel session);
	long createAndAddActivity(ApplicationModel application, UserSessionModel userSession, ActivityModel activity);
	UserSessionModel getUserSessionForApplication(ApplicationModel application, long id);
}
