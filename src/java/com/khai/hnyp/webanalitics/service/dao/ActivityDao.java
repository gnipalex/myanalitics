package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ActivityType;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;

public interface ActivityDao {
	List<ActivityModel> getAllForPeriod(Connection con, ApplicationModel app,
			Date leftDate, Date rightDate) throws SQLException;

	long create(Connection con, UserSessionModel session, ActivityModel model)
			throws SQLException;

	ActivityModel get(Connection con, long id) throws SQLException;

	long checkPageVisit(Connection con, ApplicationModel application,
			String page, Date leftBorder, Date rightBorder) throws SQLException;

	Map<String, Long> getPagesVisits(Connection con,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException;

	long getActivityCountForPeriod(Connection con,
			ApplicationModel application, ActivityType type, Date leftBorder,
			Date rightBorder) throws SQLException;
}
