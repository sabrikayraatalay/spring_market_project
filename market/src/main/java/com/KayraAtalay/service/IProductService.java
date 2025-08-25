package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoProduct;
import com.KayraAtalay.dto.DtoProductIU;
import com.KayraAtalay.model.Product;

public interface IProductService {
	
	public DtoProduct saveProduct(DtoProductIU request);
	
	public DtoProduct getProductById(Long id);
	
	public Page<Product> findAllPageable(Pageable pageable);
	
	public Page<DtoProduct> findAllPageableDto(Pageable pageable);
	
	public DtoProduct updateProduct(Long id, DtoProductIU updateRequest);
		
	public void deleteProduct(Long id);
	
	public Page<DtoProduct> findProductByCategory(Long categoryId, Pageable pageable);

}
