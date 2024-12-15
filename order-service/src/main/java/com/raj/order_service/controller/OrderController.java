package com.raj.order_service.controller;

import com.raj.order_service.dto.OrderRequest;
import com.raj.order_service.dto.OrderResponse;
import com.raj.order_service.model.OrderLineItems;
import com.raj.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest)
    {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }
    @PostMapping("/dummy")
    public ResponseEntity<OrderLineItems> placeOrder()
    {
        return ResponseEntity.ok(orderService.dummyPlaceOrder());
    }

}
