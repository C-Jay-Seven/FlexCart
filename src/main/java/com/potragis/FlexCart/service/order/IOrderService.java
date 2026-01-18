package com.potragis.FlexCart.service.order;

import com.potragis.FlexCart.dto.order.OrderRequest;
import com.potragis.FlexCart.dto.order.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<OrderResponse> getOrdersByClient(Long clientId);
    OrderResponse getOrderById(Long orderId);
    OrderResponse createOrder(Long clientId, OrderRequest orderRequest);
    void cancelOrder(Long orderId);
    List<OrderResponse> getOrdersBySeller(Long sellerId);
}
