package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestOrderController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoOrder;
import com.KayraAtalay.enums.OrderStatus;
import com.KayraAtalay.service.IOrderService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

@RestController
@RequestMapping("/rest/api/order")
public class RestOrderControllerImpl extends RestBaseController implements IRestOrderController {

	@Autowired
	private IOrderService orderService;

	@PostMapping("/{customerId}/{addressId}")
	@Override
	public RootEntity<DtoOrder> createOrderFromCart(@PathVariable Long customerId, @PathVariable Long addressId) {
		return ok(orderService.createOrderFromCart(customerId, addressId));
	}

	@GetMapping("/{orderId}")
	@Override
	public RootEntity<DtoOrder> getOrderById(@PathVariable Long orderId) {
		return ok(orderService.getOrderById(orderId));
	}

	@GetMapping("/all/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoOrder>> findAllPageable(PageableRequest pageable) {
		Page<DtoOrder> page = orderService.findAllPageableDto(toPageable(pageable));

		return ok(toPageableResponse(page, page.getContent()));
	}

	@GetMapping("/customer-orders/{customerId}")
	@Override
	public RootEntity<RestPageableEntity<DtoOrder>> getOrdersByCustomerPageable(@PathVariable Long customerId, PageableRequest pageable) {
		Page<DtoOrder> page = orderService.getOrdersByCustomer(customerId, toPageable(pageable));
		
		return ok(toPageableResponse(page, page.getContent()));
	}

	@PutMapping("/cancel/{orderId}")
	@Override
	public RootEntity<DtoOrder> cancelOrder(@PathVariable Long orderId) {
		return ok(orderService.cancelOrder(orderId));
	}

	@PutMapping("/update-status/{orderId}")
	@Override
	public RootEntity<DtoOrder> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus newStatus) {
		return ok(orderService.updateOrderStatus(orderId, newStatus));
	}

}
