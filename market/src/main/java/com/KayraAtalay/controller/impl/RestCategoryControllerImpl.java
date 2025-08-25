package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCategoryController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCategoryIU;
import com.KayraAtalay.service.ICategoryService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/category")
public class RestCategoryControllerImpl extends RestBaseController implements IRestCategoryController {

	@Autowired
	private ICategoryService categoryService;

	@PostMapping("/add")
	@Override
	public RootEntity<DtoCategory> saveCategory(@Valid @RequestBody DtoCategoryIU request) {
		return ok(categoryService.saveCategory(request));
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCategory> getCategoryById(@PathVariable Long id) {
		return ok(categoryService.getCategoryById(id));
	}

	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoCategory>> findAllPageable(PageableRequest pageableRequest) {

		Page<DtoCategory> page = categoryService.findAllPageableDto(toPageable(pageableRequest));

		return ok(toPageableResponse(page, page.getContent()));
	}

	@PutMapping("/update/{id}")
	@Override
	public RootEntity<DtoCategory> updateCategory(@PathVariable Long id, @Valid @RequestBody DtoCategoryIU updateRequest) {
		return ok(categoryService.updateCategory(id, updateRequest));
	}

}
