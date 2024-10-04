package com.raj.order_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderLineItemsDTO> orderLineItemsList;
}
