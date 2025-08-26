package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCartController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.CartRequest;
import com.KayraAtalay.dto.DtoCart;
import com.KayraAtalay.service.ICartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/cart")
public class RestCartControllerImpl extends RestBaseController implements IRestCartController {

	@Autowired
	private ICartService cartService;

	@PostMapping("/add/{customerId}")
	@Override
	public RootEntity<DtoCart> addProductToCart(@PathVariable Long customerId,
			@Valid @RequestBody CartRequest cartRequest) {
		return ok(cartService.addProductToCart(customerId, cartRequest));
	}

	@DeleteMapping("/remove/{customerId}")
	@Override
	public RootEntity<DtoCart> removeProductFromCart(@PathVariable Long customerId,
			@Valid @RequestBody CartRequest cartRequest) {
		return ok(cartService.removeProductFromCart(customerId, cartRequest));
	}

	@GetMapping("/get/{customerId}")
	@Override
	public RootEntity<DtoCart> getCart(@PathVariable Long customerId) {
		return ok(cartService.getCart(customerId));
	}

	@DeleteMapping("/clear/{customerId}")
	@Override
	public void clearCart(@PathVariable Long customerId) {
		cartService.clearCart(customerId);
	}

}
