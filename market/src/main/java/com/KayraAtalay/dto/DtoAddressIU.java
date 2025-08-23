package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAddressIU {

    @NotBlank(message = "City cannot be empty or just spaces")
    private String city;

    @NotBlank(message = "District cannot be empty or just spaces")
    private String district;

    @NotBlank(message = "Neighborhood cannot be empty or just spaces")
    private String neighborhood;

    @NotBlank(message = "Street cannot be empty or just spaces")
    private String street;
    

}