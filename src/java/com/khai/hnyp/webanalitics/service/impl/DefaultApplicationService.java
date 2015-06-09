package com.khai.hnyp.webanalitics.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.SiteCategoryModel;
import com.khai.hnyp.webanalitics.service.ApplicationService;
import com.khai.hnyp.webanalitics.service.dao.AccountDao;
import com.khai.hnyp.webanalitics.service.dao.ApplicationDao;
import com.khai.hnyp.webanalitics.service.transaction.ITransactedOperation;
import com.khai.hnyp.webanalitics.service.transaction.TransactionManager;

public class DefaultApplicationService implements ApplicationService {

	@Autowired
	private TransactionManager transactionManager;
	@Autowired
	private ApplicationDao applicationDao;
	@Autowired
	private AccountDao accounDao;
	
	@Override
	public long create(final ApplicationModel application) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return applicationDao.create(con, application);
			}
		});
	}

	@Override
	public void updateConfig(final ApplicationModel application) {
		transactionManager.doInTransaction(new ITransactedOperation<Void>() {
			@Override
			public Void execute(Connection con) throws SQLException {
				applicationDao.update(con, application);
				return null;
			}
		});
	}

	@Override
	public void delete(final ApplicationModel application) {
		transactionManager.doInTransaction(new ITransactedOperation<Void>() {
			@Override
			public Void execute(Connection con) throws SQLException {
				applicationDao.remove(con, application.getId());
				return null;
			}
		});
	}

	@Override
	public List<ApplicationModel> getAllForCategory(final SiteCategoryModel category) {
		return transactionManager.doInTransaction(new ITransactedOperation<List<ApplicationModel>>() {
			@Override
			public List<ApplicationModel> execute(Connection con)
					throws SQLException {
				return applicationDao.getAllForCategory(con, category);
			}
		});
	}

	@Override
	public List<ApplicationModel> getAllForAccount(final AccountModel account) {
		return transactionManager.doInTransaction(new ITransactedOperation<List<ApplicationModel>>() {
			@Override
			public List<ApplicationModel> execute(Connection con)
					throws SQLException {
				return applicationDao.getAllForAccount(con, account);
			}
		});
	}
	
	@Override
	public ApplicationModel getForDomainAndAccount(final AccountModel account, final String domain) {
		return transactionManager.doInTransaction(new ITransactedOperation<ApplicationModel>() {
			@Override
			public ApplicationModel execute(Connection con) throws SQLException {
				ApplicationModel application = applicationDao.getForDomain(con, domain);
				if (accounDao.ownsApplication(con, account, application)) {
					return application;
				}
				return null;
			}
		});
	}
	
	@Override
	public ApplicationModel getForDomain(final String domain) {
		return transactionManager.doInTransaction(new ITransactedOperation<ApplicationModel>() {
			@Override
			public ApplicationModel execute(Connection con) throws SQLException {
				return applicationDao.getForDomain(con, domain);
			}
		});
	}

}
