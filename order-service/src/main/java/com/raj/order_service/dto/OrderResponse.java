package com.raj.order_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private List<OrderLineItemsDTO> orderLineItemsList;

}
