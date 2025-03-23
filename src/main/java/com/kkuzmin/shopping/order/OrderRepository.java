package com.kkuzmin.shopping.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, UUID> {

    List<CustomerOrder> findOrdersByOrderTimeBetween(LocalDateTime from, LocalDateTime to);
}
