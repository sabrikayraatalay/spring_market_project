package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoCart;

public interface ICartService {

	public DtoCart addProductToCart(Long customerId, Long productId, int quantity);

	public DtoCart removeProductFromCart(Long customerId, Long productId, int quantity);

	public DtoCart getCart(Long customerId);

	public void clearCart(Long customerId);

}
