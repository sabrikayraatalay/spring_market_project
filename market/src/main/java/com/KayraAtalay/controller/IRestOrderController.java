package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoOrder;
import com.KayraAtalay.enums.OrderStatus;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestOrderController {
	
	public RootEntity<DtoOrder> createOrderFromCart(Long customerId, Long addressId);

    public RootEntity<DtoOrder> getOrderById(Long orderId);

    public RootEntity<RestPageableEntity<DtoOrder>> findAllPageable(PageableRequest pageable);

    public RootEntity<RestPageableEntity<DtoOrder>> getOrdersByCustomerPageable(Long customerId, PageableRequest pageable);

    public RootEntity<DtoOrder> cancelOrder(Long orderId);

	public RootEntity<DtoOrder> updateOrderStatus(Long orderId, OrderStatus newStatus);

}
