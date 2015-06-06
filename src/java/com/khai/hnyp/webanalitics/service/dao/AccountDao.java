package com.khai.hnyp.webanalitics.service.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;

public interface AccountDao {
	long create(Connection con, AccountModel account) throws SQLException;
	void update(Connection con, AccountModel account) throws SQLException;
	AccountModel getByLogin(Connection con, String login) throws SQLException;
	boolean ownsApplication(Connection con, AccountModel account, ApplicationModel application) throws SQLException;
}
