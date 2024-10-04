package com.raj.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
}
