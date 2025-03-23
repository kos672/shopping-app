package com.kkuzmin.shopping.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody ProductDTO productDTO) {
        UUID productId = productService.createProduct(productDTO);
        return ResponseEntity.ok(productId);
    }

    @GetMapping
    public ResponseEntity<List<ProductFullDTO>> getAllProducts() {
        List<ProductFullDTO> allProducts = productService.getAllProducts();
        return allProducts.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(allProducts);
    }

    @PutMapping
    public ResponseEntity<ProductFullDTO> updateProduct(@RequestBody ProductDTO product) {
        Optional<ProductFullDTO> productOpt = productService.updateProduct(product);
        return ResponseEntity.of(productOpt);
    }
}
