package com.khai.hnyp.webanalitics.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ActivityType;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;

public interface ActivityService {
	List<ActivityModel> getAllForPeriod(ApplicationModel app, Date leftDate, Date rightDate);
	long create(UserSessionModel session, ActivityModel model);
	ActivityModel get(long id);
	long checkPageVisit(ApplicationModel application, String page, Date leftBorder, Date rightBorder);
	Map<String, Long> getPagesVisits(ApplicationModel application, Date leftBorder, Date rightBorder);
	long getActivityCountForPeriod(ApplicationModel application, ActivityType type, Date leftBorder, Date rightBorder);
}
