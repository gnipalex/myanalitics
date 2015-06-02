package com.khai.hnyp.webanalitics.service.dao;

import java.sql.SQLException;
import java.util.List;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.mysql.jdbc.Connection;

public interface AnaliticsDao {
	List<ActivityModel> findAll(Connection con) throws SQLException;
	List<ActivityModel> findAllForSite(Connection con, long id) throws SQLException;
	long create(Connection con, ActivityModel model) throws SQLException;
	ActivityModel get(Connection con, long id) throws SQLException;
	void remove(Connection con, long id) throws SQLException;
}
