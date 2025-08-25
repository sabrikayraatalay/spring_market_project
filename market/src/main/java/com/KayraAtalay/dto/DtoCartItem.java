package com.KayraAtalay.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoCartItem extends DtoBase {
	
	    private Long productId;
	    
	    private String productName;
	    
	    private BigDecimal price;
	    
	    private Integer quantity;
	    
	    private BigDecimal subtotal;

}
