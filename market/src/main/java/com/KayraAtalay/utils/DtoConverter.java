package com.KayraAtalay.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCart;
import com.KayraAtalay.dto.DtoCartItem;
import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoProduct;
import com.KayraAtalay.dto.DtoUser;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.CartItem;
import com.KayraAtalay.model.Category;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Product;
import com.KayraAtalay.model.User;

public class DtoConverter {

	public static DtoCustomer toDto(Customer customer) {
		DtoCustomer dtoCustomer = new DtoCustomer();

		BeanUtils.copyProperties(customer, dtoCustomer);

		return dtoCustomer;

	}

	public static DtoUser toDto(User user) {
		DtoUser dtoUser = new DtoUser();

		BeanUtils.copyProperties(user, dtoUser);

		return dtoUser;

	}

	public static DtoAddress toDto(Address address) {
		DtoAddress dtoAddress = new DtoAddress();
		BeanUtils.copyProperties(address, dtoAddress);
		return dtoAddress;
	}

	public static DtoCategory toDto(Category category) {
		DtoCategory dtoCategory = new DtoCategory();

		BeanUtils.copyProperties(category, dtoCategory);

		return dtoCategory;
	}
	
	public static DtoProduct toDto(Product product) {
		DtoProduct dtoProduct = new DtoProduct();
		
		BeanUtils.copyProperties(product, dtoProduct);
		
		dtoProduct.setCategory(toDto(product.getCategory()));
		
		return dtoProduct;
	}
	
	
	
	public static DtoCartItem toDto(CartItem cartItem) {
		DtoCartItem dtoCartItem = new DtoCartItem();
		Product cartItemProduct = cartItem.getProduct();
		BigDecimal quantityBigDecimal = new BigDecimal(cartItem.getQuantity());
		
		dtoCartItem.setId(cartItem.getId());
		dtoCartItem.setCreateTime(cartItem.getCreateTime());
		dtoCartItem.setPrice(cartItemProduct.getPrice());
		dtoCartItem.setProductId(cartItemProduct.getId());
		dtoCartItem.setProductName(cartItemProduct.getName());
		dtoCartItem.setQuantity(cartItem.getQuantity());
		dtoCartItem.setSubtotal(cartItemProduct.getPrice().multiply(quantityBigDecimal));
		
		return dtoCartItem;
		
	}
	
	
	
	public static DtoCart toDto(Cart cart) {
		DtoCart dtoCart = new DtoCart();
		List<DtoCartItem> items = cart.getItems().stream().map(DtoConverter::toDto).collect(Collectors.toList());
		
		dtoCart.setId(cart.getId());
		dtoCart.setCustomerId(cart.getCustomer().getId());
		dtoCart.setCartId(cart.getId());
		dtoCart.setTotalPrice(cart.getTotalPrice());
		dtoCart.setItems(items);
		dtoCart.setCreateTime(cart.getCreateTime());
		
		return dtoCart;
		
	}

}
