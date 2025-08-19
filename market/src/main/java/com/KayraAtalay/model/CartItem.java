package com.KayraAtalay.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {

	@ManyToOne
    private Product product;

	@ManyToOne
	private Cart cart;
	
	@Column(name = "quantity")
    private Integer quantity;
}
