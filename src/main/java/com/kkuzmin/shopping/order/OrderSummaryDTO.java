package com.kkuzmin.shopping.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderSummaryDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("products")
        List<String> products,
        @JsonProperty("total")
        BigDecimal total,
        @JsonProperty("placedAt")
        LocalDateTime placedAt
) {
}
