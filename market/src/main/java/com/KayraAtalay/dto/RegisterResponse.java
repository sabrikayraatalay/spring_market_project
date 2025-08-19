package com.KayraAtalay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
	
	private DtoUser user;
	
	private DtoCustomer customer;

}