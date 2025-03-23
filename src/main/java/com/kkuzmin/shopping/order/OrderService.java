package com.kkuzmin.shopping.order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderSummaryDTO placeOrder(String email, List<String> products);

    List<OrderSummaryDTO> getOrdersInRange(LocalDateTime from, LocalDateTime to);

}
