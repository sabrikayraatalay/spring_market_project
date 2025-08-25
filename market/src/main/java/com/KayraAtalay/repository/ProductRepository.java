package com.KayraAtalay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Category;
import com.KayraAtalay.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory(Category category);

	Optional<Product> findByName(String name);

	boolean existsByName(String name);
	
	Page<Product> findByCategory(Category category, Pageable pageable);


}
