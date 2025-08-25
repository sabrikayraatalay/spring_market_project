package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestProductController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoProduct;
import com.KayraAtalay.dto.DtoProductIU;
import com.KayraAtalay.dto.DtoProductIU.OnCreate;
import com.KayraAtalay.dto.DtoProductIU.OnUpdate;
import com.KayraAtalay.service.IProductService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

@RestController
@RequestMapping("/rest/api/product")
public class RestProductControllerImpl extends RestBaseController implements IRestProductController {

	@Autowired
	private IProductService productService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoProduct> saveProduct(@Validated(OnCreate.class) @RequestBody DtoProductIU request) {
		return ok(productService.saveProduct(request));
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoProduct> getProductById(@PathVariable Long id) {
		return ok(productService.getProductById(id));
	}

	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoProduct>> findAllPageable(PageableRequest pageable) {
		Page<DtoProduct> page = productService.findAllPageableDto(toPageable(pageable));

		return ok(toPageableResponse(page, page.getContent()));
	}

	@PatchMapping("/update/{id}")
	@Override
	public RootEntity<DtoProduct> updateProduct(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody DtoProductIU updateRequest) {
		return ok(productService.updateProduct(id, updateRequest));
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public void deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
	}

	@GetMapping("/find-by-category/{categoryId}")
	@Override
	public RootEntity<RestPageableEntity<DtoProduct>> findProductByCategory(@PathVariable Long categoryId, PageableRequest pageable) {
		Page<DtoProduct> page = productService.findProductByCategory(categoryId, toPageable(pageable));

		return ok(toPageableResponse(page, page.getContent()));
	}

}
