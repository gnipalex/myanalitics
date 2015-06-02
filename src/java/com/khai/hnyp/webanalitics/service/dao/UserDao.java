package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.khai.hnyp.webanalitics.model.AccountModel;

public interface UserDao {
	long add(AccountModel user, Connection con) throws SQLException;
	void remove(long id, Connection con) throws SQLException;
	void update(AccountModel user, Connection con) throws SQLException;
	AccountModel get(long id, Connection con) throws SQLException;
	AccountModel getByLogin(String login, Connection con) throws SQLException;
	List<AccountModel> getAll(Connection con) throws SQLException;
}
