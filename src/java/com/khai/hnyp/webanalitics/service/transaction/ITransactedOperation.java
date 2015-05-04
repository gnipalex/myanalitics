package com.khai.hnyp.webanalitics.service.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface ITransactedOperation<E> {
	E execute(Connection con) throws SQLException;
}
