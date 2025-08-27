package com.KayraAtalay.dto;

import java.math.BigDecimal;
import java.util.List;

import com.KayraAtalay.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrder extends DtoBase {

	private DtoCustomer customer;

	private List<DtoOrderItem> items;

	private DtoAddress deliveryAddress;
	
	private BigDecimal totalAmount;

	private OrderStatus status;


}
