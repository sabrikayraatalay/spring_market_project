package com.KayraAtalay.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoCart;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.CartItem;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Product;
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

		return cart;
	}

	private Cart customerCartCheckOrCreate(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		Optional<Cart> optCart = cartRepository.findByCustomer(customer);
		Cart cart = new Cart();

		if (optCart.isEmpty()) {
			cart = createCart(customer);
			return cart;
		}

		return optCart.get();

	}

	private Optional<CartItem> findCartItemInCart(Cart cart, Product product) {
		if (cart.getItems() == null) {
			return Optional.empty();
		}

		for (CartItem item : cart.getItems()) {
			if (item.getProduct().getId().equals(product.getId())) {
				return Optional.of(item);
			}
		}
		return Optional.empty();
	}

	@Override
	public DtoCart addProductToCart(Long customerId, Long productId, int quantity) {
		if (quantity < 1) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Please enter a valid amount to add this product to cart"));
		}

		Product product = productRepository.findById(productId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

		Cart cart = customerCartCheckOrCreate(customerId);

		Optional<CartItem> optCartItem = findCartItemInCart(cart, product);

		if (optCartItem.isPresent()) {
			CartItem existingCartItem = optCartItem.get();
			if (quantity + existingCartItem.getQuantity() > product.getStock()) {
				throw new BaseException(new ErrorMessage(MessageType.OUT_OF_STOCK, product.getStock().toString()));
			}
			existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
		} else {
			CartItem newCartItem = createCartItem(cart, product, quantity);
			cart.getItems().add(newCartItem);
		}

		Cart savedCart = cartRepository.save(cart);

		return DtoConverter.toDto(savedCart);

	}

	@Override
	public DtoCart removeProductFromCart(Long customerId, Long productId, int quantity) {
		if (quantity < 1) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Please enter a valid amount to remove this product from cart"));
		}

		Cart cart = customerCartCheckOrCreate(customerId);
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.PRODUCT_NOT_FOUND, productId.toString())));

		Optional<CartItem> optCartItem = findCartItemInCart(cart, product);

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
