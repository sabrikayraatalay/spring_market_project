package com.KayraAtalay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.CartItem;
import com.KayraAtalay.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
