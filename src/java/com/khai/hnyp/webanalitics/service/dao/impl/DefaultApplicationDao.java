package com.khai.hnyp.webanalitics.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.SiteCategoryModel;
import com.khai.hnyp.webanalitics.service.dao.ApplicationDao;
import com.khai.hnyp.webanalitics.service.dao.util.StatementUtils;

public class DefaultApplicationDao implements ApplicationDao {

	public static final String SQL_CREATE = "INSERT INTO `applications`(`name`, `domain`,"
			+ " `sessionActiveMaxTimeMin`, `sessionBreakPage`, `conversionClass`, `collectActivityOnPage`, `activitySendIntervalSec`,"
			+ " `cookieMaxTimeMin`, `account_id`, `category_id`) VALUES(?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_SELECT_ALL_FOR_CATEGORY = "SELECT * FROM `applications` app"
			+ " JOIN `sitecategories` cat ON app.`category_id` = cat.`id`"
			+ " WHERE cat.`id` =?";
	public static final String SQL_SELECT_FOR_ACCOUNT = "SELECT * FROM `applications` app"
			+ " JOIN `accounts` acc ON app.`account_id` = acc.`id`"
			+ " WHERE acc.`id` =?";
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM `applications` app WHERE app.`id` =?";
	public static final String SQL_SELECT_BY_DOMAIN = "SELECT * FROM `applications` app WHERE app.`domain` =?";
	public static final String SQL_REMOVE_BY_ID = "DELETE FROM `applications` app WHERE app.`id` =?";
	public static final String SQL_UPDATE_BY_ID = "UPDATE `applications` SET"
			+ " `name` =?, `domain` =?, `sessionActiveMaxTimeMin` =?, `sessionBreakPage` =?, `conversionClass` =?, `collectActivityOnPage` =?,"
			+ " `activitySendIntervalSec` =?, `cookieMaxTimeMin` =?, `account_id` =?,"
			+ " `category_id` =? WHERE `id` =?";
	
	@Override
	public long create(Connection con, ApplicationModel model)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int index = 1;
			prst.setString(index++, model.getName());
			prst.setString(index++, model.getDomain());
			prst.setLong(index++, model.getSessionActiveMaxTimeMin());
			StatementUtils.setNullableObject(index++, prst, model.getSessionBreakPage());
			StatementUtils.setNullableObject(index++, prst, model.getConversionClass());
			prst.setBoolean(index++, model.isCollectActivityOnPage());
			prst.setLong(index++, model.getActivitySendIntervalSec());
			prst.setLong(index++, model.getCookieMaxTimeMin());
			prst.setLong(index++, model.getAccountId());
			prst.setLong(index++, model.getSiteCategoryId());
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
	public List<ApplicationModel> getAllForCategory(Connection con,
			SiteCategoryModel category) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_ALL_FOR_CATEGORY)) {
			int index = 1;
			prst.setLong(index++, category.getId());
			ResultSet rs = prst.executeQuery();
			List<ApplicationModel> applications = new ArrayList<ApplicationModel>();
			while (rs.next()) {
				ApplicationModel application = extractModel(rs);
				applications.add(application);
			}
			return applications;
		}
	}

	@Override
	public List<ApplicationModel> getAllForAccount(Connection con,
			AccountModel account) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_FOR_ACCOUNT)) {
			prst.setLong(1, account.getId());
			ResultSet rs = prst.executeQuery();
			List<ApplicationModel> applications = new ArrayList<ApplicationModel>();
			while (rs.next()) {
				ApplicationModel application = extractModel(rs);
				applications.add(application);
			}
			return applications;
		}
	}

	@Override
	public ApplicationModel get(Connection con, long id) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_ID)) {
			prst.setLong(1, id);
			ResultSet rs = prst.executeQuery();
			ApplicationModel application = null;
			if (rs.next()) {
				application = extractModel(rs);
			}
			return application;
		}
	}

	@Override
	public void remove(Connection con, long id) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_REMOVE_BY_ID)) {
			prst.setLong(1, id);
			prst.executeUpdate();
		}
	}

	@Override
	public void update(Connection con, ApplicationModel application)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_UPDATE_BY_ID)) {
			int index = 1;
			prst.setString(index++, application.getName());
			prst.setString(index++, application.getDomain());
			prst.setLong(index++, application.getSessionActiveMaxTimeMin());
			StatementUtils.setNullableObject(index++, prst, application.getSessionBreakPage());
			StatementUtils.setNullableObject(index++, prst, application.getConversionClass());
			prst.setBoolean(index++, application.isCollectActivityOnPage());
			prst.setLong(index++, application.getActivitySendIntervalSec());
			prst.setLong(index++, application.getCookieMaxTimeMin());
			prst.setLong(index++, application.getAccountId());
			prst.setLong(index++, application.getSiteCategoryId());
			prst.setLong(index++, application.getId());
			prst.executeUpdate();
		}
	}
	
	private ApplicationModel extractModel(ResultSet rs) throws SQLException {
		ApplicationModel model = new ApplicationModel();
		model.setAccountId(rs.getLong("account_id"));
		model.setActivitySendIntervalSec(rs.getInt("activitySendIntervalSec"));
		model.setCollectActivityOnPage(rs.getBoolean("collectActivityOnPage"));
		model.setCookieMaxTimeMin(rs.getInt("cookieMaxTimeMin"));
		model.setDomain(rs.getString("domain"));
		model.setId(rs.getLong("id"));
		model.setName(rs.getString("name"));
		model.setSessionActiveMaxTimeMin(rs.getInt("sessionActiveMaxTimeMin"));
		model.setSessionBreakPage(rs.getString("sessionBreakPage"));
		model.setConversionClass(rs.getString("conversionClass"));
		model.setSiteCategoryId(rs.getLong("category_id"));
		return model;
	}

	@Override
	public ApplicationModel getForDomain(Connection con, String domain)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_DOMAIN)) {
			prst.setString(1, domain);
			ResultSet rs = prst.executeQuery();
			ApplicationModel application = null;
			if (rs.next()) {
				application = extractModel(rs);
			}
			return application;
		}
	}

}
