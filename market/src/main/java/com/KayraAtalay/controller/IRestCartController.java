package com.KayraAtalay.controller;
import com.KayraAtalay.dto.CartRequest;
import com.KayraAtalay.dto.DtoCart;

public interface IRestCartController {

	public RootEntity<DtoCart> addProductToCart(Long customerId, CartRequest cartRequest);

	public RootEntity<DtoCart> removeProductFromCart(Long customerId,CartRequest cartRequest);

	public RootEntity<DtoCart> getCart(Long customerId);

	public void clearCart(Long customerId);

}