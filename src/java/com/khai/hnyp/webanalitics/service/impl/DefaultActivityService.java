package com.khai.hnyp.webanalitics.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.khai.hnyp.webanalitics.model.ActivityModel;
import com.khai.hnyp.webanalitics.model.ActivityType;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.UserSessionModel;
import com.khai.hnyp.webanalitics.service.ActivityService;
import com.khai.hnyp.webanalitics.service.dao.ActivityDao;
import com.khai.hnyp.webanalitics.service.transaction.ITransactedOperation;
import com.khai.hnyp.webanalitics.service.transaction.TransactionManager;

public class DefaultActivityService implements ActivityService {

	@Autowired
	private TransactionManager transactionManager;
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public List<ActivityModel> getAllForPeriod(final ApplicationModel app,
			final Date leftDate, final Date rightDate) {
		return transactionManager.doInTransaction(new ITransactedOperation<List<ActivityModel>>() {
			@Override
			public List<ActivityModel> execute(Connection con)
					throws SQLException {
				return activityDao.getAllForPeriod(con, app, leftDate, rightDate);
			}
		});
	}

	@Override
	public long createForSession(final UserSessionModel session, final ActivityModel model) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return activityDao.create(con, session, model);
			}
		});
	}

	@Override
	public ActivityModel get(final long id) {
		return transactionManager.doInTransaction(new ITransactedOperation<ActivityModel>() {
			@Override
			public ActivityModel execute(Connection con) throws SQLException {
				return activityDao.get(con, id);
			}
		});
	}

	@Override
	public long checkPageVisit(final ApplicationModel application, final String page,
			final Date leftBorder, final Date rightBorder) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return activityDao.checkPageVisit(con, application, page, leftBorder, rightBorder);
			}
		});
	}

	@Override
	public Map<String, Long> getPagesVisits(final ApplicationModel application,
			final Date leftBorder, final Date rightBorder) {
		return transactionManager.doInTransaction(new ITransactedOperation<Map<String, Long>>() {
			@Override
			public Map<String, Long> execute(Connection con)
					throws SQLException {
				return activityDao.getPagesVisits(con, application, leftBorder, rightBorder);
			}
		});
	}

	@Override
	public long getActivityCountForPeriod(final ApplicationModel application,
			final ActivityType type, final Date leftBorder, final Date rightBorder) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return activityDao.getActivityCountForPeriod(con, application, type, leftBorder, rightBorder);
			}
		});
	}

}
