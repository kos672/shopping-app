package com.kkuzmin.shopping.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderSummaryDTO> placeOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        OrderSummaryDTO orderSummaryDTO = orderService.placeOrder(createOrderRequest.email(), createOrderRequest.products());
        return ResponseEntity.ok(orderSummaryDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryDTO>> getOrderInRange(@RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                       @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<OrderSummaryDTO> ordersInRange = orderService.getOrdersInRange(from, to);
        return !ordersInRange.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(ordersInRange);
    }
}
