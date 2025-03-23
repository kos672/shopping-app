package com.kkuzmin.shopping.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "CUSTOMER_ORDER")
@Table(name = "CUSTOMER_ORDER")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "BUYERS_EMAIL")
    private String buyersEmail;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderProduct> products;

    @Column(name = "ORDER_TIME", columnDefinition = "TIMESTAMP")
    private LocalDateTime orderTime;

    @Transient
    private BigDecimal total;

    public CustomerOrder() {
        this.products = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public List<OrderProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProduct(OrderProduct product) {
        product.setOrder(this);
        this.products.add(product);
    }

    public List<String> getProductNames() {
        return products.stream().map(OrderProduct::getName).toList();
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @PostLoad
    @PostPersist
    private void calculateTotal() {
        this.total = products.stream().map(OrderProduct::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
