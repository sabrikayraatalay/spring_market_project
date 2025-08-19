package com.KayraAtalay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomer extends DtoBase {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phoneNumber;

}
