package com.kkuzmin.shopping.product;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Tag(name = "Create Product", description = "Create a product with the supplied name, price, currency. " +
            "If the product with the given name exists, no new product will be created.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products could be successfully created"),
            @ApiResponse(responseCode = "400", description = "Product could not be created as there is one with the given name"),
    })
    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody @UniqueProductConstraint ProductDTO productDTO) {
        UUID productId = productService.createProduct(productDTO);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @Tag(name = "Get all products", description = "Get all available products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found"),
    })
    @GetMapping
    public ResponseEntity<List<ProductFullDTO>> getAllProducts() {
        List<ProductFullDTO> allProducts = productService.getAllProducts();
        return allProducts.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(allProducts);
    }

    @Tag(name = "Update a product", description = "Update an existing product with the supplied data, " +
            "the existing product will be found by supplied 'name'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "No product for the given name found"),
    })
    @PutMapping
    public ResponseEntity<ProductFullDTO> updateProduct(@RequestBody ProductDTO product) {
        Optional<ProductFullDTO> productOpt = productService.updateProduct(product);
        return ResponseEntity.of(productOpt);
    }
}
