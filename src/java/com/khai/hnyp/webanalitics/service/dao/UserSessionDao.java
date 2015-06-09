package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;

public interface UserSessionDao {
	long getUsersCountForPerion(Connection con, ApplicationModel application, Date leftBorder, Date rightBorder) throws SQLException;
	long getSessionsCountWithNoCookie(Connection con, ApplicationModel application, Date leftBorder, Date rightBorder) throws SQLException;
	List<UserSessionModel> getSessionsForPeriod(Connection con, ApplicationModel application, Date leftBorder, Date rightBorder) throws SQLException;
	void removeOlderDate(Connection con, ApplicationModel application, Date date) throws SQLException;
	long create(Connection con, ApplicationModel application, UserSessionModel session) throws SQLException;
	UserSessionModel getForApplication(Connection con, ApplicationModel application, long id) throws SQLException;
}
