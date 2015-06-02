package com.khai.hnyp.webanalitics.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.service.dao.AccountDao;

public class DefaultAccountDao implements AccountDao {

	public static final String SQL_CREATE = "INSERT INTO `accounts`(`login`, `password`, `email`) VALUES (?,?,?)";
	public static final String SQL_UPDATE_BY_ID = "UPDATE `accounts` a SET a.`login` =? , a.`password` =?, a.`email` =? WHERE a.`id` =?";
	public static final String SQL_SELECT_BY_ID = "SELECT * FROM `accounts` a WHERE a.`login` = ?";
	
	@Override
	public long create(Connection con, AccountModel account)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_CREATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int index = 1;
			prst.setString(index++, account.getLogin());
			prst.setString(index++, account.getPassword());
			prst.setString(index++, account.getEmail());
			prst.executeUpdate();
			ResultSet rs = prst.getGeneratedKeys();
			long generatedId = 0;
			if (rs.next()) {
				generatedId = rs.getLong(1);
			}
			return generatedId;
		}
	}

	@Override
	public void update(Connection con, AccountModel account)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_UPDATE_BY_ID)) {
			int index = 1;
			prst.setString(index++, account.getLogin());
			prst.setString(index++, account.getPassword());
			prst.setString(index++, account.getEmail());
			prst.setLong(index++, account.getId());
			prst.executeUpdate();
		}
	}

	@Override
	public AccountModel getByLogin(Connection con, String login)
			throws SQLException {
		try (PreparedStatement prst = con.prepareStatement(SQL_SELECT_BY_ID)){
			int index = 1;
			prst.setString(index++, login);
			ResultSet rs = prst.executeQuery();
			AccountModel account = null;
			if (rs.next()) {
				account = extractModel(rs);
			}
			return account;
		}
	}
	
	private AccountModel extractModel(ResultSet rs) throws SQLException {
		AccountModel model = new AccountModel();
		model.setEmail(rs.getString("email"));
		model.setId(rs.getLong("id"));
		model.setLogin(rs.getString("login"));
		model.setPassword(rs.getString("password"));
		return model;
	}

}
