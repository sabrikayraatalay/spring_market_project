package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoProduct;
import com.KayraAtalay.dto.DtoProductIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestProductController {
	

	
	public RootEntity<DtoProduct> saveProduct(DtoProductIU request);
	
	public RootEntity<DtoProduct>  getProductById(Long id);
		
	public RootEntity<RestPageableEntity<DtoProduct>> findAllPageable(PageableRequest pageable);
	
	public RootEntity<DtoProduct> updateProduct(Long id, DtoProductIU updateRequest);
		
	public void deleteProduct(Long id);
	
	public RootEntity<RestPageableEntity<DtoProduct>> findProductByCategory(Long categoryId, PageableRequest pageable);

}
