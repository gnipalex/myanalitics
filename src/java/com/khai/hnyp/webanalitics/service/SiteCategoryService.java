package com.khai.hnyp.webanalitics.service;

import java.util.List;

import com.khai.hnyp.webanalitics.model.SiteCategoryModel;

public interface SiteCategoryService {
	List<SiteCategoryModel> getAll();
	long create(SiteCategoryModel category);
	SiteCategoryModel getByName(String name);
}
