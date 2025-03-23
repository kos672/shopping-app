package com.kkuzmin.shopping.order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "ORDER_PRODUCT")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURRENCY")
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerOrder order;

    public OrderProduct() {}

    public OrderProduct(String name, BigDecimal price, String currency, CustomerOrder order) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }
}
