package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCategoryIU;
import com.KayraAtalay.model.Category;

public interface ICategoryService {
	
	public DtoCategory saveCategory(DtoCategoryIU request);
	
	public DtoCategory getCategoryById(Long id);
	
	public Page<Category> findAllPageable(Pageable pageable);
	
	public Page<DtoCategory> findAllPageableDto(Pageable pageable);
	
	public DtoCategory updateCategory(Long id, DtoCategoryIU updateRequest);

}
