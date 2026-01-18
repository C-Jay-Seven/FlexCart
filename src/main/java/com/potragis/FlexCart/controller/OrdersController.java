package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.order.OrderRequest;
import com.potragis.FlexCart.dto.order.OrderResponse;
import com.potragis.FlexCart.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final IOrderService orderService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByClient(@PathVariable Long clientId) {
        List<OrderResponse> orders = orderService.getOrdersByClient(clientId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/client/{clientId}")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable Long clientId,
            @RequestBody OrderRequest orderRequest) {

        OrderResponse createdOrder = orderService.createOrder(clientId, orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order canceled successfully.");
    }


    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersBySeller(@PathVariable Long sellerId) {
        List<OrderResponse> orders = orderService.getOrdersBySeller(sellerId);
        return ResponseEntity.ok(orders);
    }
}
