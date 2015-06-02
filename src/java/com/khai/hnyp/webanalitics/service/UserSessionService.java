package com.khai.hnyp.webanalitics.service;

import java.util.Date;

import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;

public interface UserSessionService {
	long getUsersCountForPerion(ApplicationModel application, Date leftBorder, Date rightBorder);
	long getUsersCountToday(ApplicationModel application);
	long getActiveUsersCount(ApplicationModel application);
	void removeOlderDate(Date date);
	long create(ApplicationModel application, UserSessionModel session);
	
}
