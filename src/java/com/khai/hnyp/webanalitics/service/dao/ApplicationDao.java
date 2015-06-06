package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.SiteCategoryModel;

public interface ApplicationDao {
	long create(Connection con, ApplicationModel model) throws SQLException;
	List<ApplicationModel> getAllForCategory(Connection con, SiteCategoryModel category) throws SQLException;
	List<ApplicationModel> getAllForAccount(Connection con, AccountModel account) throws SQLException;
	ApplicationModel get(Connection con, long id) throws SQLException;
	void remove(Connection con, long id) throws SQLException;
	void update(Connection con, ApplicationModel application) throws SQLException;
	ApplicationModel getForDomain(Connection con, String domain) throws SQLException;
}
