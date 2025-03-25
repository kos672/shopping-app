package com.kkuzmin.shopping.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductExistsValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductConstraint {
    String message() default "Product with the given name already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
