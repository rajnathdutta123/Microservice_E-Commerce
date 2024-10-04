package com.raj.inventory_service.controller;

import com.raj.inventory_service.dto.InventoryRequest;
import com.raj.inventory_service.dto.InventoryResponse;
import com.raj.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}/{quantity}")
    public ResponseEntity<Boolean> isInStock(@PathVariable("skuCode") String skuCode, @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode, quantity));
    }

    @PostMapping("/")
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryRequest));
    }


}
