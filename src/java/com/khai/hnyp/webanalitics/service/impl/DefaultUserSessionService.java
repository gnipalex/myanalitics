package com.khai.hnyp.webanalitics.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;
import com.khai.hnyp.webanalitics.service.UserSessionService;
import com.khai.hnyp.webanalitics.service.dao.ActivityDao;
import com.khai.hnyp.webanalitics.service.dao.UserSessionDao;
import com.khai.hnyp.webanalitics.service.transaction.ITransactedOperation;
import com.khai.hnyp.webanalitics.service.transaction.TransactionManager;

public class DefaultUserSessionService implements UserSessionService {
	
	@Autowired
	private TransactionManager transactionManager;
	@Autowired
	private UserSessionDao userSessionDao;
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public long getUsersCountForPerion(final ApplicationModel application,
			final Date leftBorder, final Date rightBorder) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return userSessionDao.getUsersCountForPerion(con, application, leftBorder, rightBorder);
			}
		});
	}

	@Override
	public long getUsersCountToday(ApplicationModel application) {
		Date today = new Date();
		Date leftBorder = DateUtils.setHours(today, 0);
		leftBorder = DateUtils.setMinutes(leftBorder, 0);
		leftBorder = DateUtils.setSeconds(leftBorder, 0);
		leftBorder = DateUtils.setMilliseconds(leftBorder, 0);
		Date rightBorder = DateUtils.addDays(leftBorder, 1);
		return this.getUsersCountForPerion(application, leftBorder, rightBorder);
	}

	@Override
	public long getActiveUsersCount(final ApplicationModel application) {
		final Date activityLastTime = prepareLastDateOfActivity(application);
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return activityDao.getActivityCountForApplicationSinceDate(con, application, activityLastTime);
			}
		});
	}
	
	private Date prepareLastDateOfActivity(ApplicationModel application) {
		long activeMaxMinutes = application.getSessionActiveMaxTimeMin();
		if (activeMaxMinutes <= 0) {
			return null;
		}
		Date dateNow = new Date();
		return DateUtils.addMinutes(dateNow, - (int)activeMaxMinutes);
	}

	@Override
	public void removeOlderDate(final ApplicationModel application, final Date date) {
		transactionManager.doInTransaction(new ITransactedOperation<Void>() {
			@Override
			public Void execute(Connection con) throws SQLException {
				userSessionDao.removeOlderDate(con, application, date);
				return null;
			}
		});
	}

	@Override
	public long create(final ApplicationModel application, final UserSessionModel session) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return userSessionDao.create(con, application, session);
			}
		});
	}

}
