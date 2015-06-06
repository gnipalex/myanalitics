package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.khai.hnyp.webanalitics.model.SiteCategoryModel;

public interface SiteCategoryDao {
	List<SiteCategoryModel> getAll(Connection con) throws SQLException;
	long create(Connection con, SiteCategoryModel category) throws SQLException;
	SiteCategoryModel getByName(Connection con, String name) throws SQLException;
	SiteCategoryModel get(Connection con, long id) throws SQLException;
}
