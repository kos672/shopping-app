package com.kkuzmin.shopping.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductExistsValidator implements ConstraintValidator<UniqueProductConstraint, ProductDTO> {

    private final ProductRepository productRepository;

    @Autowired
    public ProductExistsValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(ProductDTO product, ConstraintValidatorContext context) {
        return productRepository.findByName(product.name()) == null;
    }
}
