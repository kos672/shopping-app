package com.kkuzmin.shopping.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateOrderRequest(
        @JsonProperty("email")
        String email,
        @JsonProperty("products")
        List<String> products
) {
}
