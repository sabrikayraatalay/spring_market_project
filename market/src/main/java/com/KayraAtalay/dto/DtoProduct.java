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
public class DtoProduct extends DtoBase {
	
	    private String name;
	    
	    private BigDecimal price;
	    
	    private Integer stock;
	    
	    private DtoCategory category;

}
