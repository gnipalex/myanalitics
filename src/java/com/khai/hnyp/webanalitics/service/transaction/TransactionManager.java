package com.khai.hnyp.webanalitics.service.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class TransactionManager {
	private static final Logger LOG = Logger.getLogger(TransactionManager.class);
	private DataSource dataSource;
	
	/**
	 * 
	 * @param op operation T to do in transaction
	 * @return result T of operation
	 * @throws ServiceLayerException if any inner {@link SQLException} occurred
	 */
	public <T> T doInTransaction(ITransactedOperation<T> op) {
		Connection con = null;
		T result = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			result = op.execute(con);
			con.commit();
			return result;
		} catch (SQLException e) {
			LOG.error(e);
			try {
				con.rollback(); 
			} catch (SQLException se) {
				LOG.error(e);
			}
			throw new TransactionException("transaction failed", e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error(e);
			}
		}
	}
	
	private class TransactionException extends RuntimeException {
		public TransactionException() {
			super();
		}

		public TransactionException(String message, Throwable cause) {
			super(message, cause);
		}	
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
