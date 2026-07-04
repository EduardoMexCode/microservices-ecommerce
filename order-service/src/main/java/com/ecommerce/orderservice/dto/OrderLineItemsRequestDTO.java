package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsRequestDTO {
    @NotBlank(message = "El SKU es requerido")
    private String sku;
    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.0", inclusive = false) // El precio debe ser mayor que cero
    private BigDecimal price;
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1)
    private Integer quantity;
}
