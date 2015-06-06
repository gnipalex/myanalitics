package com.khai.hnyp.webanalitics.service.transaction;


public interface TransactionManager {
	public <T> T doInTransaction(ITransactedOperation<T> op);
}
