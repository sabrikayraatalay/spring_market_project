package com.KayraAtalay.model;

import java.math.BigDecimal;

import com.KayraAtalay.enums.ProductCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "stock")
	private Integer stock;
	
	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	

}
