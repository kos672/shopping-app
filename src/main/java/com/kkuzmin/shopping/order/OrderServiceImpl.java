package com.kkuzmin.shopping.order;

import com.kkuzmin.shopping.product.ProductFullDTO;
import com.kkuzmin.shopping.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(ProductService productService, OrderRepository orderRepository) {
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderSummaryDTO placeOrder(String email, List<String> products) {
        List<ProductFullDTO> productsByNames = productService.findProductsByNames(products);
        List<String> foundProdNames = productsByNames.stream().map(prod -> prod.product().name()).toList();
        if (productsByNames.size() != products.size()) {
            products.forEach(prodName -> {
                if (!foundProdNames.contains(prodName)) {
                    LOG.warn("The order will be placed without the Product [%s], as it could not be found.".formatted(prodName));
                }
            });
        }

        CustomerOrder order = new CustomerOrder();
        order.setBuyersEmail(email);
        productsByNames.forEach(productFullDTO -> {
            OrderProduct orderProduct = OrderProductMapper.INSTANCE.mapToOrderProduct(productFullDTO, order);
            order.addProduct(orderProduct);
        });

        orderRepository.save(order);
        return new OrderSummaryDTO(order.getId(), foundProdNames, order.getTotal(), order.getOrderTime());
    }

    @Override
    public List<OrderSummaryDTO> getOrdersInRange(LocalDateTime from, LocalDateTime to) {
        List<CustomerOrder> ordersInRange = orderRepository.findOrdersByOrderTimeBetween(from, to);
        return ordersInRange.stream().map(order -> new OrderSummaryDTO(order.getId(), order.getProductNames(), order.getTotal(), order.getOrderTime())).toList();
    }
}
