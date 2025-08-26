package com.KayraAtalay.service;

import com.KayraAtalay.dto.CartRequest;
import com.KayraAtalay.dto.DtoCart;

public interface ICartService {

	public DtoCart addProductToCart(Long customerId, CartRequest cartRequest);

	public DtoCart removeProductFromCart(Long customerId, CartRequest cartRequest);

	public DtoCart getCart(Long customerId);

	public void clearCart(Long customerId);

}
