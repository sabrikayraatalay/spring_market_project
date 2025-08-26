package com.KayraAtalay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.Customer;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findByCustomer(Customer customer);

}
