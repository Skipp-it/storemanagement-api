package com.storemanagement.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductDto(
        @NotNull @Size(min = 3) String name,
        @NotNull String description,
        @NotNull BigDecimal price) {
}
