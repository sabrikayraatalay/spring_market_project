package com.KayraAtalay.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCategoryIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Category;
import com.KayraAtalay.repository.CategoryRepository;
import com.KayraAtalay.service.ICategoryService;
import com.KayraAtalay.utils.DtoConverter;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public DtoCategory saveCategory(DtoCategoryIU request) {
		Optional<Category> optCategory = categoryRepository.findByName(request.getName());

		if (optCategory.isPresent()) {
			throw new BaseException(new ErrorMessage(MessageType.CATEGORY_ALREADY_EXISTS, request.getName()));
		}

		Category category = new Category();
		BeanUtils.copyProperties(request, category);

		Category savedCategory = categoryRepository.save(category);

		return DtoConverter.toDto(savedCategory);
	}

	@Override
	public DtoCategory getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, id.toString())));

		return DtoConverter.toDto(category);
	}

	@Override
	public Page<Category> findAllPageable(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Page<DtoCategory> findAllPageableDto(Pageable pageable) {
		Page<Category> page = findAllPageable(pageable);

		if (page.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}

		return page.map(DtoConverter::toDto);
	}

	@Override
	public DtoCategory updateCategory(Long id, DtoCategoryIU updateRequest) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CATEGORY_NOT_FOUND, id.toString())));

		Optional<Category> optCategory = categoryRepository.findByName(updateRequest.getName());
		if (optCategory.isPresent() && !optCategory.get().getId().equals(category.getId())) {
			throw new BaseException(new ErrorMessage(MessageType.CATEGORY_ALREADY_EXISTS, updateRequest.getName()));
		}

		BeanUtils.copyProperties(updateRequest, category);

		Category savedCategory = categoryRepository.save(category);

		return DtoConverter.toDto(savedCategory);
	}

}
