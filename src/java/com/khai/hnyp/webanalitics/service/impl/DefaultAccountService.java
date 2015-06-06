package com.khai.hnyp.webanalitics.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.service.AccountService;
import com.khai.hnyp.webanalitics.service.dao.AccountDao;
import com.khai.hnyp.webanalitics.service.transaction.ITransactedOperation;
import com.khai.hnyp.webanalitics.service.transaction.TransactionManager;

public class DefaultAccountService implements AccountService {

	@Autowired
	private TransactionManager transactionManager;
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public long create(final AccountModel account) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return accountDao.create(con, account);
			}
		});
	}

	@Override
	public void update(final AccountModel account) {
		transactionManager.doInTransaction(new ITransactedOperation<Void>() {
			@Override
			public Void execute(Connection con) throws SQLException {
				accountDao.update(con, account);
				return null;
			}
		});
	}

	@Override
	public boolean checkPasswordMatches(AccountModel account, String password) {
		return account.getPassword().equals(password);
	}

	@Override
	public AccountModel getByLogin(final String login) {
		return transactionManager.doInTransaction(new ITransactedOperation<AccountModel>() {
			@Override
			public AccountModel execute(Connection con) throws SQLException {
				return accountDao.getByLogin(con, login);
			}
		});
	}

}
