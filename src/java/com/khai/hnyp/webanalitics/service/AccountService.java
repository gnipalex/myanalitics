package com.khai.hnyp.webanalitics.service;

import com.khai.hnyp.webanalitics.model.AccountModel;

public interface AccountService {
	long create(AccountModel account);
	void update(AccountModel account);
	boolean checkPasswordMatches(AccountModel account, String password);
	AccountModel getByLogin(String login);
}
