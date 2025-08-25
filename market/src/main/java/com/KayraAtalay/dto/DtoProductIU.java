package com.KayraAtalay.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoProductIU {

    public interface OnCreate {} // for saving
    public interface OnUpdate {} // for updating

    @NotBlank(message = "Product name cannot be blank", groups = OnCreate.class)
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Product price cannot be null", groups = OnCreate.class)
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0", groups = {OnCreate.class, OnUpdate.class})
    private BigDecimal price;

    @NotNull(message = "Product stock cannot be null", groups = OnCreate.class)
    private Integer stock;

    @NotNull(message = "Category ID cannot be null", groups = OnCreate.class)
    private Long categoryId;

}