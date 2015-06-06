package com.khai.hnyp.webanalitics.service.dao.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class StatementUtils {
	
	public static void setNullableObject(int index, PreparedStatement prst, Object value) throws SQLException {
		if (isNull(value)) {
			prst.setNull(index, Types.NULL);
		} else {
			prst.setObject(index, value);
		}
	}
	
	private static boolean isNull(Object o) {
		return o == null;
	}
}
