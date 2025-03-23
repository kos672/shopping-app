package com.kkuzmin.shopping.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    UUID createProduct(ProductDTO productDTO);

    List<ProductFullDTO> getAllProducts();

    Optional<ProductFullDTO> updateProduct(ProductDTO product);

    List<ProductFullDTO> findProductsByNames(List<String> productNames);

}
