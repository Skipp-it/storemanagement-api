package com.storemanagement.model;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, String description, BigDecimal price) {
}
