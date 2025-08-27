package com.KayraAtalay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoOrder;
import com.KayraAtalay.enums.OrderStatus;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Cart;
import com.KayraAtalay.model.CartItem;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Order;
import com.KayraAtalay.model.OrderItem;
import com.KayraAtalay.model.Product;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.repository.CartRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.repository.OrderItemRepository;
import com.KayraAtalay.repository.OrderRepository;
import com.KayraAtalay.repository.ProductRepository;
import com.KayraAtalay.service.IOrderService;
import com.KayraAtalay.utils.DtoConverter;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	@Transactional
	public DtoOrder createOrderFromCart(Long customerId, Long addressId) {

		List<Product> productsForSaveAll = new ArrayList<>();

		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		Cart cart = cartRepository.findByCustomer(customer).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CART_NOT_FOUND, customerId.toString())));

		Address address = addressRepository.findById(addressId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, addressId.toString())));

		if (address.getCustomers().stream().noneMatch(c -> c.getId().equals(customerId))) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"This address does not belong to this customer: " + customer.getFirstName()));
		}

		if (cart.getItems().isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Cart is empty, cannot create an order."));
		}

		Order order = new Order();
		order.setCustomer(customer);
		order.setDeliveryAddress(address);
		order.setStatus(OrderStatus.PENDING);

		BigDecimal totalAmountOfOrder = BigDecimal.ZERO;
		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItem cartItem : cart.getItems()) {
			Product product = cartItem.getProduct();
			Integer requestedQuantity = cartItem.getQuantity();

			if (product.getStock() < requestedQuantity) {
				throw new BaseException(new ErrorMessage(MessageType.OUT_OF_STOCK,
						"Not enough stock for product: " + product.getName()));
			}

			product.setStock(product.getStock() - cartItem.getQuantity());
			productsForSaveAll.add(product);

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setPriceAtPurchase(product.getPrice());
			orderItem.setProduct(product);
			orderItem.setQuantity(cartItem.getQuantity());

			totalAmountOfOrder = totalAmountOfOrder
					.add(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

			orderItems.add(orderItem);

		}

		productRepository.saveAll(productsForSaveAll);

		order.setItems(orderItems);
		order.setTotalAmount(totalAmountOfOrder);

		orderRepository.save(order);
		orderItemRepository.saveAll(orderItems);

		// clear customer's cart
		cart.getItems().clear();
		cartRepository.save(cart);

		return DtoConverter.toDto(order);
	}

	@Override
	public DtoOrder getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())));

		return DtoConverter.toDto(order);
	}

	@Override
	public Page<DtoOrder> getOrdersByCustomer(Long customerId, Pageable pageable) {

		Customer customer = customerRepository.findById(customerId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString())));

		Page<Order> orders = orderRepository.findAllByCustomer(customer, pageable);

		return orders.map(DtoConverter::toDto);
	}

	@Override
	@Transactional
	public DtoOrder cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())));

		// checking order status
		if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED
				|| order.getStatus() == OrderStatus.CANCELLED) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"This order cannot be cancelled as it is already " + order.getStatus()));
		}

		List<Product> productsToUpdate = new ArrayList<>();

		// updating stocks when order is cancelled
		for (OrderItem item : order.getItems()) {
			Product product = item.getProduct();
			product.setStock(product.getStock() + item.getQuantity());
			productsToUpdate.add(product);

		}

		productRepository.saveAll(productsToUpdate);

		order.setStatus(OrderStatus.CANCELLED);
		Order savedOrder = orderRepository.save(order);
		return DtoConverter.toDto(savedOrder);
	}

	@Override
	@Transactional
	public DtoOrder updateOrderStatus(Long orderId, OrderStatus newStatus) {
		Order order = orderRepository.findById(orderId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ORDER_NOT_FOUND, orderId.toString())));
		OrderStatus currentStatus = order.getStatus();

		if (currentStatus == OrderStatus.DELIVERED || currentStatus == OrderStatus.CANCELLED) {
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Order status cannot be changed for orders that are already " + currentStatus));
		}

		if (currentStatus == newStatus) {
			return DtoConverter.toDto(order);
		}

		switch (currentStatus) {
		case PENDING:
			// PENDING orders can only transition to SHIPPED or CANCELLED.
			if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
				throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
						"Invalid transition from PENDING to " + newStatus));
			}
			break;
		case SHIPPED:
			// SHIPPED orders can only transition to DELIVERED.
			if (newStatus != OrderStatus.DELIVERED) {
				throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
						"Invalid transition from SHIPPED to " + newStatus));
			}
			break;
		default:
			// This case catches unexpected or undefined transitions.
			throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
					"Invalid transition from " + currentStatus + " to " + newStatus));
		}

		order.setStatus(newStatus);
		Order savedOrder = orderRepository.save(order);

		return DtoConverter.toDto(savedOrder);
	}

	@Override
	public Page<Order> findAllPageable(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public Page<DtoOrder> findAllPageableDto(Pageable pageable) {

		Page<Order> orderPage = findAllPageable(pageable);
		if (orderPage.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}

		return orderPage.map(DtoConverter::toDto);
	}

}
