package com.kkuzmin.shopping.order;

import com.kkuzmin.shopping.product.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    private static final String EMAIL = "kos@mail.com";
    private final String urlPlaceholder = "http://localhost:%s/orders";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void cleanProducts() {
        this.productRepository.deleteAll();
        this.orderRepository.deleteAll();
    }

    @Test
    @Sql("/product/addThreeProductsTestData.sql")
    void testPlaceOrder_orderCanBeSuccessfullyCreated() {
        // given
        List<String> products = List.of("spaghetti", "rice", "garlic");
        BigDecimal total = new BigDecimal("8.5").setScale(2);
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(EMAIL, products);

        // when
        ResponseEntity<OrderSummaryDTO> createdOrder = this.restTemplate.postForEntity(urlPlaceholder.formatted(port), createOrderRequest, OrderSummaryDTO.class);

        // then
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getBody()).isNotNull();
        assertThat(createdOrder.getBody().total()).isEqualTo(total);
        assertThat(createdOrder.getBody().products()).hasSize(products.size());
        assertThat(createdOrder.getBody().products()).containsAll(products);

        assertThat(createdOrder.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Sql("/product/addThreeProductsTestData.sql")
    void testPlaceOrder_orderCanBeSuccessfullyCreatedWithoutNotExistingProducts() {
        // given
        List<String> products = List.of("spaghetti", "rice", "garlic", "tomato", "egg");
        BigDecimal total = new BigDecimal("8.5").setScale(2);
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(EMAIL, products);

        // when
        ResponseEntity<OrderSummaryDTO> createdOrder = this.restTemplate.postForEntity(urlPlaceholder.formatted(port), createOrderRequest, OrderSummaryDTO.class);

        // then
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getBody()).isNotNull();
        assertThat(createdOrder.getBody().total()).isEqualTo(total);
        assertThat(createdOrder.getBody().products()).hasSize(3);
        assertThat(createdOrder.getBody().products()).contains(products.get(0));
        assertThat(createdOrder.getBody().products()).contains(products.get(1));
        assertThat(createdOrder.getBody().products()).contains(products.get(2));
        assertThat(createdOrder.getBody().products()).doesNotContain(products.get(3));
        assertThat(createdOrder.getBody().products()).doesNotContain(products.get(4));

        assertThat(createdOrder.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Sql("/order/addThreeOrders.sql")
    void testGetOrdersInRange_orderCanBeSuccessfullyRetrieved() {
        // given
        LocalDateTime from = LocalDateTime.of(2025, 3, 25, 22, 40);
        LocalDateTime to = LocalDateTime.of(2025, 3, 25, 23, 0);

        // when
        ResponseEntity<List> orders = this.restTemplate.getForEntity(urlPlaceholder.formatted(port) + "?from=" + from + "&to=" + to , List.class);

        // then
        assertThat(orders.getBody()).hasSize(3);

        assertThat(orders.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql("/order/addThreeOrders.sql")
    void testGetOrdersInRange_orderCannotBeRetrievedNoOrderInGivenTimeRange() {
        // given
        LocalDateTime from = LocalDateTime.of(2025, 3, 25, 12, 40);
        LocalDateTime to = LocalDateTime.of(2025, 3, 25, 22, 0);

        // when
        ResponseEntity<List> orders = this.restTemplate.getForEntity(urlPlaceholder.formatted(port) + "?from=" + from + "&to=" + to , List.class);

        // then
        assertThat(orders.getBody()).isNull();

        assertThat(orders.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}