package com.raj.order_service.service;

import com.raj.order_service.client.InventoryClient;
import com.raj.order_service.dto.OrderLineItemsDTO;
import com.raj.order_service.dto.OrderRequest;
import com.raj.order_service.dto.OrderResponse;
import com.raj.order_service.exception.OrderException;
import com.raj.order_service.model.Order;
import com.raj.order_service.model.OrderLineItems;
import com.raj.order_service.repository.OrderLineItemsRepository;
import com.raj.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderLineItemsRepository orderLineItemsRepository;
        public OrderResponse placeOrder(OrderRequest orderRequest) {

            if(isInStock(orderRequest)) {
                // Create the order object
                Order order = Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .build();

                // Save the order first to ensure it has an ID
                Order savedOrder = orderRepository.save(order);

                // Convert and save order line items
                List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList().stream()
                        .map(or -> createOrderLineItems(or, savedOrder))
                        .collect(Collectors.toList());

                // Set the saved line items back to the order
                savedOrder.setOrderLineItemsList(orderLineItemsList);

                // Optionally save the order again if necessary
                return convertIntoOrderResponse(orderRepository.save(savedOrder));
            }
            else {
                return null;
            }
        }

        public OrderLineItems createOrderLineItems (OrderLineItemsDTO orderLineItemsDTO, Order order) {
            if (orderLineItemsDTO == null) {
                log.error("Order Line items DTO is NULL");
                return null;
            }
            OrderLineItems orderLineItems= OrderLineItems.builder()
                    .price(orderLineItemsDTO.getPrice())
                    .quantity(orderLineItemsDTO.getQuantity())
                    .skuCode(orderLineItemsDTO.getSkuCode())
                    .order(order)
                    .build();
            return orderLineItemsRepository.save(orderLineItems);
        }
        public boolean isInStock(OrderRequest orderRequest)
        {
           for(OrderLineItemsDTO itemsDTO:orderRequest.getOrderLineItemsList())
           {
               if(!inventoryClient.isInStock(itemsDTO.getSkuCode(),itemsDTO.getQuantity()))
               {
                   throw new OrderException("Product with SKU code "+itemsDTO.getSkuCode()+" is not in stock for quantity "+itemsDTO.getQuantity());
               }
           }
             return true;
        }

        public OrderResponse convertIntoOrderResponse(Order order) {
            if (order == null) {
                log.error("Order object is NULL");
                return null;
            }

            return OrderResponse.builder()
                    .orderNumber(order.getOrderNumber())
                    .id(order.getId())
                    .orderLineItemsList(order.getOrderLineItemsList().stream().map(this::convertIntoOrderLineItemsDTO).collect(Collectors.toList()))
                    .build();
        }
        public OrderLineItemsDTO convertIntoOrderLineItemsDTO(OrderLineItems OrderLineItems)
        {
            if (OrderLineItems == null) {
                log.error("OrderLineItems object is NULL");
                return null;
            }
           return OrderLineItemsDTO.builder()
                    .id(OrderLineItems.getId())
                    .price(OrderLineItems.getPrice())
                    .skuCode(OrderLineItems.getSkuCode())
                    .quantity(OrderLineItems.getQuantity())
                    .build();
        }

    public OrderLineItems dummyPlaceOrder() {
        OrderLineItems orderLineItems=OrderLineItems.builder()
                .id(87654345672L)
                .price(new BigDecimal(122345))
                .skuCode("jhgfujfyifi")
                .quantity(10)
                .build();
        return orderLineItemsRepository.save(orderLineItems);


    }
}
