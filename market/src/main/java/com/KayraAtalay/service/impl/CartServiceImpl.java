package com.KayraAtalay.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.CartRequest;
import com.KayraAtalay.dto.DtoCart;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.CartItem;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Product;
import com.KayraAtalay.repository.CartItemRepository;
import com.KayraAtalay.repository.CartRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.repository.ProductRepository;
import com.KayraAtalay.service.ICartService;
import com.KayraAtalay.utils.DtoConverter;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductRepository productRepository;

	private CartItem createCartItem(Cart cart, Product product, int quantity) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		return cartItem;
	}

	private Cart createCart(Customer customer) {
		Cart cart = new Cart();
		cart.setCustomer(customer);

		return cartRepository.save(cart);
	}

	private Cart customerCartCheckOrCreate(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		return cartRepository.findByCustomer(customer).orElseGet(() -> createCart(customer));

	}

	@Override
	public DtoCart addProductToCart(Long customerId, CartRequest cartRequest) {

		Long productId = cartRequest.getProductId();
		Integer quantity = cartRequest.getQuantity();

		if (quantity < 1) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Please enter a valid amount to add this product to cart"));
		}

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

		Cart cart = customerCartCheckOrCreate(customerId);

		Optional<CartItem> optCartItem = cartItemRepository.findByCartAndProduct(cart, product);

		int newTotalQuantity;
		
		if (optCartItem.isPresent()) {
			newTotalQuantity = optCartItem.get().getQuantity() + quantity;
		} else {
			newTotalQuantity = quantity;
		}

		if (newTotalQuantity > product.getStock()) {
			throw new BaseException(new ErrorMessage(MessageType.OUT_OF_STOCK, product.getStock().toString()));
		}

		if (optCartItem.isPresent()) {
			CartItem existingCartItem = optCartItem.get();

			existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
		} else {
			CartItem newCartItem = createCartItem(cart, product, quantity);

			cart.getItems().add(newCartItem);
		}

		Cart savedCart = cartRepository.save(cart);

		return DtoConverter.toDto(savedCart);

	}

	@Override
	public DtoCart removeProductFromCart(Long customerId, CartRequest cartRequest) {

		Long productId = cartRequest.getProductId();
		Integer quantity = cartRequest.getQuantity();

		if (quantity < 1) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Please enter a valid amount to remove this product from cart"));
		}

		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		Cart cart = Optional.ofNullable(customer.getCart()).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, customerId.toString())));

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

		Optional<CartItem> optCartItem = cartItemRepository.findByCartAndProduct(cart, product);

		if (optCartItem.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"This product is not on this cart : " + productId.toString()));
		} else {
			CartItem cartItemToRemove = optCartItem.get();
			if (quantity >= cartItemToRemove.getQuantity()) {
				cart.getItems().remove(cartItemToRemove);
			} else {
				cartItemToRemove.setQuantity(cartItemToRemove.getQuantity() - quantity);
			}
		}

		Cart savedCart = cartRepository.save(cart);

		return DtoConverter.toDto(savedCart);
	}

	@Override
	public DtoCart getCart(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		Cart cart = customer.getCart();
		if (cart == null) {
			throw new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, customerId.toString()));
		}
		return DtoConverter.toDto(cart);
	}

	@Override
	public void clearCart(Long customerId) {

		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		if (customer.getCart() != null) {
			customer.getCart().getItems().clear();
			cartRepository.save(customer.getCart());
		}

	}

}
