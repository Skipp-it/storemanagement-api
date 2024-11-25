package com.storemanagement.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void testProductGettersAndSetters() {

        Product product = new Product();
        product.setName("tiles");
        product.setDescription("floor tiles");
        product.setPrice(new BigDecimal("99.03"));
        product.setDeleted(false);

        assertThat(product.getName()).isEqualTo("tiles");
        assertThat(product.getDescription()).isEqualTo("floor tiles");
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("99.03"));
        assertThat(product.isDeleted()).isFalse();
    }

    @Test
    void testSetDeletedTrue() {
        Product product = new Product();
        product.setDeleted(true);
        assertThat(product.isDeleted()).isTrue();
    }

}