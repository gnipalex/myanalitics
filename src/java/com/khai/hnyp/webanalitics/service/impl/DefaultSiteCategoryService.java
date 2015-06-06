package com.khai.hnyp.webanalitics.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.khai.hnyp.webanalitics.model.SiteCategoryModel;
import com.khai.hnyp.webanalitics.service.SiteCategoryService;
import com.khai.hnyp.webanalitics.service.dao.SiteCategoryDao;
import com.khai.hnyp.webanalitics.service.transaction.ITransactedOperation;
import com.khai.hnyp.webanalitics.service.transaction.TransactionManager;

public class DefaultSiteCategoryService implements SiteCategoryService {

	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private SiteCategoryDao sitCategoryDao;
	
	@Override
	public List<SiteCategoryModel> getAll() {
		return transactionManager.doInTransaction(new ITransactedOperation<List<SiteCategoryModel>>() {
			@Override
			public List<SiteCategoryModel> execute(Connection con)
					throws SQLException {
				return sitCategoryDao.getAll(con);
			}
		});
	}

	@Override
	public long create(final SiteCategoryModel category) {
		return transactionManager.doInTransaction(new ITransactedOperation<Long>() {
			@Override
			public Long execute(Connection con) throws SQLException {
				return sitCategoryDao.create(con, category);
			}
		});
	}

	@Override
	public SiteCategoryModel getByName(final String name) {
		return transactionManager.doInTransaction(new ITransactedOperation<SiteCategoryModel>() {
			@Override
			public SiteCategoryModel execute(Connection con)
					throws SQLException {
				return sitCategoryDao.getByName(con, name);
			}
		});
	}

}
