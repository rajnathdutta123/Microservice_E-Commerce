package com.raj.order_service.repository;

import com.raj.order_service.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems,Long> {
}
