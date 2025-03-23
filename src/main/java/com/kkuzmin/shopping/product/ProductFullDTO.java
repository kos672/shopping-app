package com.kkuzmin.shopping.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ProductFullDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("product")
        ProductDTO product
)  {
}
