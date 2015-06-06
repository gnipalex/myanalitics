package com.khai.hnyp.webanalitics.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.khai.hnyp.webanalitics.model.SiteCategoryModel;
import com.khai.hnyp.webanalitics.service.dao.SiteCategoryDao;

public class DefaultSiteCategoryDao implements SiteCategoryDao {

	public static final String SQL_SELECT_ALL = "SELECT * FROM `sitecategories` sc";
	
	public static final String SQL_SELECT_BY_NAME = "SELECT * FROM `sitecategories` sc"
			+ " WHERE sc.`name` =?";
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM `sitecategories` sc"
			+ " WHERE sc.`id` =?";
	public static final String SQL_CREATE = "INSERT INTO `sitecategories`(`name`) VALUES(?)";
	
	
	@Override
	public List<SiteCategoryModel> getAll(Connection con) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_ALL)) {
			ResultSet rs = prst.executeQuery();
			List<SiteCategoryModel> categories = new ArrayList<SiteCategoryModel>();
			while (rs.next()) {
				categories.add(extractModel(rs));
			}
			return categories;
		}
	}
	
	private SiteCategoryModel extractModel(ResultSet rs) throws SQLException {
		SiteCategoryModel model = new SiteCategoryModel();
		model.setId(rs.getLong("id"));
		model.setName(rs.getString("name"));
		return model;
	}

	@Override
	public long create(Connection con, SiteCategoryModel category) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			prst.setString(1, category.getName());
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
	public SiteCategoryModel getByName(Connection con, String name) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_NAME)) {
			prst.setString(1, name);
			ResultSet rs = prst.executeQuery();
			SiteCategoryModel category = null;
			if (rs.next()) {
				category = extractModel(rs);
			}
			return category;
		}
	}

	@Override
	public SiteCategoryModel get(Connection con, long id) throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_NAME)) {
			prst.setLong(1, id);
			ResultSet rs = prst.executeQuery();
			SiteCategoryModel category = null;
			if (rs.next()) {
				category = extractModel(rs);
			}
			return category;
		}
	}

}
