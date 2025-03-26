package com.kkuzmin.shopping.order;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Tag(name = "Place an order", description = "Place an order with the supplied products. If some of supplied products does not exist, " +
            "the order will be created without those products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An order was successfully placed")
    })
    @PostMapping
    public ResponseEntity<OrderSummaryDTO> placeOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderSummaryDTO orderSummaryDTO = orderService.placeOrder(createOrderRequest.email(), createOrderRequest.products());
        return ResponseEntity.ok(orderSummaryDTO);
    }

    @Tag(name = "Get order", description = "Get orders for the given time range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "At least one order could be found"),
            @ApiResponse(responseCode = "404", description = "No orders could be found for the specified time range")
    })
    @GetMapping
    public ResponseEntity<List<OrderSummaryDTO>> getOrdersInRange(@RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                  @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<OrderSummaryDTO> ordersInRange = orderService.getOrdersInRange(from, to);
        return ordersInRange.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(ordersInRange);
    }
}
