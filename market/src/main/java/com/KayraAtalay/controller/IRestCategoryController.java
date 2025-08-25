package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCategoryIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestCategoryController {

	public RootEntity<DtoCategory> saveCategory(DtoCategoryIU request);

	public RootEntity<DtoCategory> getCategoryById(Long id);

	public RootEntity<RestPageableEntity<DtoCategory>> findAllPageable(PageableRequest pageableRequest);

	public RootEntity<DtoCategory> updateCategory(Long id, DtoCategoryIU updateRequest);

}
