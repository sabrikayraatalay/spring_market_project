package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest extends DtoBase {
	
	@NotNull(message = "productId can not be empty")
	private Long productId;
	
	@NotNull(message = "quantity can not be empty")
	private Integer quantity;

}
