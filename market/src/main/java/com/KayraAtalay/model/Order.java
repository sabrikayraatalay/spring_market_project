package com.KayraAtalay.model;

import java.math.BigDecimal;
import java.util.List;

import com.KayraAtalay.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

	@OneToOne
	private Customer customer;

	@Column(name = "total_amount")
	private BigDecimal totalAmount;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> items;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address deliveryAddress;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

}