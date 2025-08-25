package com.KayraAtalay.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCart extends DtoBase {
	
	private Long cartId;
	
    private Long customerId;
    
    private List<DtoCartItem> items;
    
    private BigDecimal totalPrice;

}
