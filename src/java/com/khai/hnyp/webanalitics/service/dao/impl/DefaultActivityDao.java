package com.khai.hnyp.webanalitics.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ActivityType;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;
import com.khai.hnyp.webanalitics.service.dao.ActivityDao;

public class DefaultActivityDao implements ActivityDao {

	public static final String SQL_SELECT_ALL_FOR_PERIOD = 
			"SELECT * FROM `activities` a"
			+ " JOIN `usersessions` us ON a.`session_id` = us.`id`"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE a.`date` >=? AND a.`date` <=? AND app.`id` =?";
	public static final String SQL_CREATE = "INSERT INTO"
			+ " `activities`(`type`, `location`, `referrer`, `responseTime`, "
			+ "`domTime`, `date`, `href`, `title`, `elementId`, `kind`, `session_id`)"
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM `activities` a WHERE a.`id` =?";
	public static final String SQL_SELECT_VISIT_COUNT_FOR_PAGE = "SELECT count(a.`id`) FROM `activities` a"
			+ " JOIN `usersessions` us ON a.`session_id` = us.`id`"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE a.`type` = " + ActivityType.PAGE 
			+ " AND a.`location` LIKE ? AND a.`date` >=? AND a.`date` <=? AND app.`id` =?";
	public static final String SQL_VISIT_COUNT_FOR_PAGES = "SELECT a.`location`, count(a.`id`) as `count` FROM `activities` a" 
			+ " JOIN `usersessions` us ON a.`session_id` = us.`id`"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE a.`type` = " + ActivityType.PAGE + " AND a.`date` >=? AND a.`date` <=? AND app.`id` =?"
			+ " GROUP BY a.`location` "
			+ " SORT BY `count` DESC";
	public static final String SQL_SELECT_COUNT_FOR_PERIOD = "SELECT count(*) FROM `activities` a"
			+ " JOIN `usersessions` us ON a.`session_id` = us.`id`"
			+ " JOIN `applications` app ON us.`application_id` = app.`id`"
			+ " WHERE a.`type` =? AND a.`date` >=? AND a.`date` <=? AND app.`id` =?";
	
	@Override
	public List<ActivityModel> getAllForPeriod(Connection con,
			ApplicationModel app, Date leftDate, Date rightDate)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_ALL_FOR_PERIOD)) {
			int index = 1;
			prst.setTimestamp(index++, new Timestamp(leftDate.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightDate.getTime()));
			prst.setLong(index++, app.getId());
			ResultSet rs = prst.executeQuery();
			List<ActivityModel> activities = new ArrayList<ActivityModel>();
			while (rs.next()) {
				activities.add(extractModel(rs));
			}
			return activities;
		}
	}

	@Override
	public long create(Connection con, UserSessionModel session,
			ActivityModel model) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int index = 1;
			prst.setString(index++, model.getType().toString());
			prst.setString(index++, model.getLocation());
			//TODO null handling
			setNullableObject(index++, prst, model.getReferrer());
			setNullableObject(index++, prst, model.getResponseTime());
			setNullableObject(index++, prst, model.getDomTime());
			prst.setTimestamp(index++, new Timestamp(model.getDate().getTime()));
			setNullableObject(index++, prst, model.getHref());
			setNullableObject(index++, prst, model.getTitle());
			setNullableObject(index++, prst, model.getElementId());
			setNullableObject(index++, prst, model.getKind());
			prst.setLong(index++, model.getSessionId());
			prst.executeUpdate();
			ResultSet rs = prst.getGeneratedKeys();
			long generatedId = 0;
			if (rs.next()) {
				generatedId = rs.getLong(1);
			}
			return generatedId;
		}
	}
	
	private boolean isNull(Object o) {
		return o == null;
	}
	
	private void setNullableObject(int index, PreparedStatement prst, Object value) throws SQLException {
		if (isNull(value)) {
			prst.setNull(index++, Types.NULL);
		} else {
			prst.setObject(index++, value);
		}
	}

	@Override
	public ActivityModel get(Connection con, long id) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_ID)) {
			prst.setLong(1, id);
			ResultSet rs = prst.executeQuery();
			ActivityModel model = null;
			if (rs.next()) {
				model = extractModel(rs);
			}
			return model;
		}
	}

	@Override
	public long checkPageVisit(Connection con, ApplicationModel application,
			String page, Date leftBorder, Date rightBorder) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_VISIT_COUNT_FOR_PAGE)) {
			int index = 1;
			prst.setString(index++, page);
			prst.setTimestamp(index++, new Timestamp(leftBorder.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightBorder.getTime()));
			prst.setLong(index++, application.getId());
			ResultSet rs = prst.executeQuery();
			long count = 0;
			if (rs.next()) {
				count = rs.getLong(1);
			}
			return count;
		}
	}

	@Override
	public Map<String, Long> getPagesVisits(Connection con,
			ApplicationModel application, Date leftBorder, Date rightBorder)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_VISIT_COUNT_FOR_PAGES)) {
			int index = 1;
			prst.setTimestamp(index++, new Timestamp(leftBorder.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightBorder.getTime()));
			prst.setLong(index++, application.getId());
			ResultSet rs = prst.executeQuery();
			Map<String, Long> pagesVisits = new HashMap<String, Long>(); 
			while(rs.next()) {
				String page = rs.getString(1);
				Long visits = rs.getLong(2);
				pagesVisits.put(page, visits);
			}
			return pagesVisits;
		}
	}

	@Override
	public long getActivityCountForPeriod(Connection con,
			ApplicationModel application, ActivityType type, Date leftBorder,
			Date rightBorder) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_VISIT_COUNT_FOR_PAGES)) {
			int index = 1;
			prst.setString(index++, type.toString());
			prst.setTimestamp(index++, new Timestamp(leftBorder.getTime()));
			prst.setTimestamp(index++, new Timestamp(rightBorder.getTime()));
			prst.setLong(index++, application.getId());
			ResultSet rs = prst.executeQuery();
			long count = 0;
			if (rs.next()) {
				count = rs.getLong(1);
			}
			return count;
		}
	}
	
	private ActivityModel extractModel(ResultSet rs) throws SQLException {
		ActivityModel model = new ActivityModel();
		model.setDate(rs.getTimestamp("date"));
		model.setDomTime(rs.getLong("domTime"));
		model.setHref(rs.getString("href"));
		model.setId(rs.getLong("id"));
		model.setElementId(rs.getString("elementId"));
		model.setKind(rs.getString("kind"));
		model.setLocation(rs.getString("location"));
		model.setReferrer(rs.getString("referrer"));
		model.setResponseTime(rs.getLong("responseTime"));
		model.setSessionId(rs.getLong("sessionId"));
		model.setTitle(rs.getString("title"));
		model.setType(ActivityType.valueOf(rs.getString("type")));
		return model;
	}

}
