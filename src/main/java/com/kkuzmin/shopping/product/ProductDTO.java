package com.kkuzmin.shopping.product;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductDTO(
        @JsonProperty("name")
        String name,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("currency")
        String currency
) {}
