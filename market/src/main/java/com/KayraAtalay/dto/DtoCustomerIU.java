package com.KayraAtalay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCustomerIU {
	

	@NotBlank(message = "First name cannot be empty.")
	@JsonProperty("firstName")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be empty.")
	@JsonProperty("lastName")
	private String lastName;

	@NotBlank(message = "Email cannot be empty.")
	@JsonProperty("email")
    @Email
	private String email;

	
	@NotBlank(message = "Phone number cannot be empty.")
	@JsonProperty("phoneNumber")
	 @Pattern(regexp = "^(05)([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$", 
     message = "Phone number is not in a valid format (e.g., 05xx xxx xx xx).")
	private String phoneNumber;
	
}
