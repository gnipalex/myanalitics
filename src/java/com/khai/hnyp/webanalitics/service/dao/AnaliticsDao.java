package com.khai.hnyp.webanalitics.service.dao;

import java.util.List;

import com.khai.hnyp.webanalitics.model.AnaliticsModel;
import com.mysql.jdbc.Connection;

public interface AnaliticsDao {
	List<AnaliticsModel> findAll(Connection con);
	List<AnaliticsModel> findAllForSite(Connection con, long id);
	long create(Connection con, AnaliticsModel model);
	AnaliticsModel get(Connection con, long id);
	void remove(Connection con, long id);
}
