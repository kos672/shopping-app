package com.kkuzmin.shopping.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Product with the given name [%s] cannot be found.".formatted(productName));
    }
}
