package com.khai.hnyp.webanalitics.service.dao.util;

public class ParserUtils {
	
	public static Integer parseIntegerOrNull(String value) {
		try { 
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	public static Long parseLongOrNull(String value) {
		try { 
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
