package com.khai.hnyp.webanalitics.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;
import com.khai.hnyp.webanalitics.service.dao.UserSessionDao;
import com.khai.hnyp.webanalitics.service.dao.util.StatementUtils;

public class DefaultUserSessionDao implements UserSessionDao {

	public static final String SQL_COUNT_FOR_PERIOD = "SELECT count(*) FROM `usersessions` us"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE app.`id` =? AND us.`date` >=? AND us.`date` <=?";
	public static final String SQL_COUNT_NO_COOKIE_FOR_PERIOD = "SELECT count(*) FROM `usersessions` us"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE app.`id` =? AND us.`date` >=? AND us.`date` <=? AND us.`cookieEnabled` = false";
	public static final String SQL_SESSIONS_FOR_PERIOD = "SELECT * FROM `usersessions` us"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE app.`id` =? AND us.`date` >=? AND us.`date` <=?";
	public static final String SQL_REMOVE_OLDER_DATE = "DELETE us FROM `usersessions` us"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE app.`id` =? AND us.`date` <?";
	public static final String SQL_CREATE = "INSERT INTO `usersessions`(`date`,`browser`,`cookieEnabled`,"
			+ "`screenHeight`,`screenWidth`,`application_id`) VALUES(?,?,?,?,?,?)";
	public static final String SQL_SELECT_ACTIVE_SESSIONS_COUNT = "SELECT count(a.`id`) FROM `activities` a"
			+ " WHERE a.`session_id` IN "
			+ "(SELECT us.`id` FROM `usersessions` us WHERE us.`application_id` =?)"
			+ " AND a.`date` >=?";
	public static final String SQL_SELECT_SESSION_FOR_APPLICATION_BY_ID = "SELECT * FROM `usersessions` us"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE app.`id` =? AND us.`id` =?";

	@Override
	public long getUsersCountForPerion(Connection con,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException {
		return getUsersCountForPerion(con, SQL_COUNT_FOR_PERIOD, application,
				leftBorder, rightBorder);
	}

	private long getUsersCountForPerion(Connection con, String query,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(query)) {
			int index = 1;
			prst.setLong(index++, application.getId());
			prst.setTimestamp(index++, new Timestamp(leftBorder.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightBorder.getTime()));
			ResultSet rs = prst.executeQuery();
			long count = 0;
			if (rs.next()) {
				count = rs.getLong(1);
			}
			return count;
		}
	}

	@Override
	public long getSessionsCountWithNoCookie(Connection con,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException {
		return getUsersCountForPerion(con, SQL_COUNT_NO_COOKIE_FOR_PERIOD,
				application, leftBorder, rightBorder);
	}

	@Override
	public void removeOlderDate(Connection con, ApplicationModel application,
			Date date) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_REMOVE_OLDER_DATE)) {
			int index = 1;
			prst.setLong(index++, application.getId());
			prst.setTimestamp(index++, new Timestamp(date.getTime()));
			prst.executeUpdate();
		}
	}

	@Override
	public long create(Connection con, ApplicationModel application,
			UserSessionModel session) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int index = 1;
			prst.setTimestamp(index++, new Timestamp(session.getDate().getTime()));
			StatementUtils.setNullableObject(index++, prst, session.getBrowser());
			StatementUtils.setNullableObject(index++, prst, session.getCookieEnabled());
			StatementUtils.setNullableObject(index++, prst, session.getScreenHeight());
			StatementUtils.setNullableObject(index++, prst, session.getScreenWidth());
			prst.setLong(index++, application.getId());
			prst.executeUpdate();
			ResultSet rs = prst.getGeneratedKeys();
			long id = 0;
			if (rs.next()) {
				id = rs.getLong(1);
			}
			return id;
		}
	}

	@Override
	public List<UserSessionModel> getSessionsForPeriod(Connection con,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SESSIONS_FOR_PERIOD)) {
			int index = 1;
			prst.setLong(index++, application.getId());
			prst.setTimestamp(index++, new Timestamp(leftBorder.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightBorder.getTime()));
			ResultSet rs = prst.executeQuery();
			List<UserSessionModel> sessions = new ArrayList<UserSessionModel>();
			while (rs.next()) {
				sessions.add(extractModel(rs));
			}
			return sessions;
		}
	}
	
	private UserSessionModel extractModel(ResultSet rs) throws SQLException {
		UserSessionModel model = new UserSessionModel();
		model.setApplicationId(rs.getLong("application_id"));
		model.setBrowser(rs.getString("browser"));
		model.setCookieEnabled(rs.getBoolean("cookieEnabled"));
		model.setDate(rs.getTimestamp("date"));
		model.setId(rs.getLong("id"));
		model.setScreenHeight(rs.getInt("screenHeight"));
		model.setScreenWidth(rs.getInt("screenHeight"));
		return model;
	}
	
	@Override
	public UserSessionModel getForApplication(Connection con,
			ApplicationModel application, long id) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_SESSION_FOR_APPLICATION_BY_ID)) {
			int index = 1;
			prst.setLong(index++, application.getId());
			prst.setLong(index++, id);
			ResultSet rs = prst.executeQuery();
			UserSessionModel model = null;
			if (rs.next()) {
				model = extractModel(rs);
			}
			return model;
		}
	}

}
