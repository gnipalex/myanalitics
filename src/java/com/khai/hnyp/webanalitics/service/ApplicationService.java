package com.khai.hnyp.webanalitics.service;

import java.util.List;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.model.ApplicationModel;
import com.khai.hnyp.webanalitics.model.SiteCategoryModel;

public interface ApplicationService {
	long create(ApplicationModel application);
	void updateConfig(ApplicationModel application);
	void delete(ApplicationModel application);
	List<ApplicationModel> getAllForCategory(SiteCategoryModel category);
	List<ApplicationModel> getAllForAccount(AccountModel account);
	ApplicationModel getForDomainAndAccount(AccountModel account, String domain);
}
