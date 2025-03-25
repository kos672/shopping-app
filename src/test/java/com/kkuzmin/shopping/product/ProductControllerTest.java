package com.kkuzmin.shopping.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    private final String urlPlaceholder = "http://localhost:%s/products";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanProducts() {
        this.productRepository.deleteAll();
    }

    @Test
    void testCreateProduct_productSuccessfullyCreated() {
        // given
        String prodName = "cheese";
        BigDecimal prodPrice = new BigDecimal(3);
        String prodPriceCurrency = "PLN";
        ProductDTO request = new ProductDTO(prodName, prodPrice, prodPriceCurrency);

        // when
        ResponseEntity<UUID> createdProductId = this.restTemplate.postForEntity(urlPlaceholder.formatted(port), request, UUID.class);
        Product createdProduct = productRepository.getReferenceById(Objects.requireNonNull(createdProductId.getBody()));


        // then
        assertThat(createdProductId).isNotNull();
        assertThat(createdProductId.getBody()).isNotNull();
        assertThat(createdProductId.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @Sql("/product/addThreeProductsTestData.sql")
    void testGetProducts_productsAreRetrieved() {
        // given

        // when
        ResponseEntity<List> allProducts = this.restTemplate.getForEntity(urlPlaceholder.formatted(port), List.class);

        // then
        assertThat(allProducts).isNotNull();
        assertThat(allProducts.getBody()).hasSize(3);
        assertThat(allProducts.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetProducts_noProductAvailable() {
        // given

        // when
        ResponseEntity<List> allProducts = this.restTemplate.getForEntity(urlPlaceholder.formatted(port), List.class);

        // then
        assertThat(allProducts).isNotNull();
        assertThat(allProducts.getBody()).isNull();
        assertThat(allProducts.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql("/product/addTomatoProduct.sql")
    void testUpdateProduct_productSuccessfullyUpdated() {
        // given
        String prodName = "tomato";
        BigDecimal prodPrice = new BigDecimal(19);
        String prodPriceCurrency = "PLN";
        ProductDTO request = new ProductDTO(prodName, prodPrice, prodPriceCurrency);


        // when
        ResponseEntity<ProductFullDTO> changedProduct = this.restTemplate.exchange(urlPlaceholder.formatted(port), HttpMethod.PUT, new HttpEntity<>(request), ProductFullDTO.class);

        // then
        assertThat(changedProduct).isNotNull();
        assertThat(changedProduct.getBody()).isNotNull();
        assertThat(changedProduct.getBody().product().price()).isEqualTo(prodPrice);
        assertThat(changedProduct.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUpdateProduct_notExistingProductUpdateNotSuccessful() {
        // given
        String prodName = "pepper";
        BigDecimal prodPrice = new BigDecimal(3);
        String prodPriceCurrency = "PLN";
        ProductDTO request = new ProductDTO(prodName, prodPrice, prodPriceCurrency);


        // when
        ResponseEntity<ProductFullDTO> changedProduct = this.restTemplate.exchange(urlPlaceholder.formatted(port), HttpMethod.PUT, new HttpEntity<>(request), ProductFullDTO.class);

        // then
        assertThat(changedProduct).isNotNull();
        assertThat(changedProduct.getBody()).isNull();
        assertThat(changedProduct.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}