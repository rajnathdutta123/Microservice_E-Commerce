package com.raj.inventory_service.service;

import com.raj.inventory_service.dto.InventoryRequest;
import com.raj.inventory_service.dto.InventoryResponse;
import com.raj.inventory_service.model.Inventory;
import com.raj.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode,Integer quantity)
    {
        return inventoryRepository.findBySkuCode(skuCode).getQuantity() >= quantity;
    }

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {

        return convertIntoInventoryResponse(inventoryRepository.save(Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build()));
    }
    public InventoryResponse convertIntoInventoryResponse(Inventory inventory)
    {
        if(inventory==null)
        {
            log.error("Inventory Object is Null");
            return null;
        }
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }

}
