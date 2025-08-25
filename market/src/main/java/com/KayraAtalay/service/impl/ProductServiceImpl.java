package com.KayraAtalay.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoProduct;
import com.KayraAtalay.dto.DtoProductIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Category;
import com.KayraAtalay.model.Product;
import com.KayraAtalay.repository.CategoryRepository;
import com.KayraAtalay.repository.ProductRepository;
import com.KayraAtalay.service.IProductService;
import com.KayraAtalay.utils.DtoConverter;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	private Product createProduct(DtoProductIU request) {
		Product product = new Product();
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new BaseException(
				new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, request.getCategoryId().toString())));

		BeanUtils.copyProperties(request, product);
		product.setCategory(category);

		return product;
	}

	private Category categoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, id.toString())));

		return category;
	}

	@Override
	public DtoProduct saveProduct(DtoProductIU request) {

		if (productRepository.existsByName(request.getName())) {
			throw new BaseException(new ErrorMessage(MessageType.PRODUCT_ALREADY_EXISTS, request.getName()));
		}

		Product product = createProduct(request);

		Product savedProduct = productRepository.save(product);

		return DtoConverter.toDto(savedProduct);
	}

	@Override
	public DtoProduct getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, id.toString())));

		return DtoConverter.toDto(product);
	}

	@Override
	public Page<Product> findAllPageable(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Page<DtoProduct> findAllPageableDto(Pageable pageable) {
		Page<Product> page = findAllPageable(pageable);
		if (page.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}

		return page.map(DtoConverter::toDto);
	}

	@Override
	public DtoProduct updateProduct(Long id, DtoProductIU updateRequest) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, id.toString())));

		if (updateRequest.getName() != null) {
			Optional<Product> optProduct = productRepository.findByName(updateRequest.getName());
			if (optProduct.isPresent() && !optProduct.get().getId().equals(id)) {
				throw new BaseException(new ErrorMessage(MessageType.PRODUCT_ALREADY_EXISTS, updateRequest.getName()));
			}
			product.setName(updateRequest.getName());
		}

		if (updateRequest.getPrice() != null) {
			product.setPrice(updateRequest.getPrice());
		}

		if (updateRequest.getStock() != null) {
			product.setStock(updateRequest.getStock());
		}

		if (updateRequest.getCategoryId() != null) {
			product.setCategory(categoryById(updateRequest.getCategoryId()));
		}

		Product savedProduct = productRepository.save(product);
		return DtoConverter.toDto(savedProduct);
	}

	@Override
	public void deleteProduct(Long id) {

		if (!productRepository.existsById(id)) {
			throw new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, id.toString()));
		}
		productRepository.deleteById(id);
	}

	@Override
	public Page<DtoProduct> findProductByCategory(Long categoryId, Pageable pageable) {
		Category category = categoryById(categoryId);
		
		Page<Product> productPage = productRepository.findByCategory(category, pageable);
		
		if (productPage.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
        }

		return productPage.map(DtoConverter::toDto);
	}

}
