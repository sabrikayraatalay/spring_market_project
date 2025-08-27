package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoOrder;
import com.KayraAtalay.enums.OrderStatus;
import com.KayraAtalay.model.Order;

public interface IOrderService {
	
	 	public DtoOrder createOrderFromCart(Long customerId, Long addressId);

	    public DtoOrder getOrderById(Long orderId);

	    public Page<Order> findAllPageable(Pageable pageable);
	    
	    public Page<DtoOrder> findAllPageableDto(Pageable pageable);

	    public Page<DtoOrder> getOrdersByCustomer(Long customerId, Pageable pageable);

	    public DtoOrder cancelOrder(Long orderId);

		public DtoOrder updateOrderStatus(Long orderId, OrderStatus newStatus);

}
