package com.potragis.FlexCart.mapper.order;

import com.potragis.FlexCart.dto.order.OrderRequest;
import com.potragis.FlexCart.dto.order.OrderResponse;
import com.potragis.FlexCart.dto.orderItems.OrderItemsRequest;
import com.potragis.FlexCart.dto.orderItems.OrderItemsResponse;
import com.potragis.FlexCart.model.entity.*;
import com.potragis.FlexCart.model.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderDTOMapper {

    public Orders toEntity(OrderRequest orderRequest, Client client, Map<Long, Product> productMap) {

        Orders order = new Orders();
        order.setClient(client);
        order.setStatus(false);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setPaymentMethod(orderRequest.getPaymentMethod());

        List<OrderItems> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemsRequest orderItem : orderRequest.getOrderItems()) {

            Product product = productMap.get(orderItem.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found: " + orderItem.getProductId());
            }

            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(orderItem.getQuantity());
            item.setUnitPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

            item.setSubtotal(subtotal);
            items.add(item);
            total = total.add(subtotal);
        }

        order.setOrderItems(items);
        order.setTotalAmount(total);

        return order;
    }

    // Convert Orders entity → OrderResponse DTO
    public OrderResponse toDTO(Orders order) {

        List<OrderItemsResponse> items = order.getOrderItems().stream()
                .map(item -> {
                    // Map product images to relative URLs
                    List<String> imageUrls = item.getProduct().getImages()
                            .stream()
                            .map(image -> "/images/" + image.getFilename()) // Option B
                            .collect(Collectors.toList());

                    return new OrderItemsResponse(
                            item.getId(),
                            item.getProduct().getId(),
                            item.getProduct().getProductName(),
                            imageUrls,
                            item.getQuantity(),
                            item.getUnitPrice(),
                            item.getSubtotal()
                    );
                })
                .toList();

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setClientId(order.getClient().getId());
        response.setTotalPrice(order.getTotalAmount());
        response.setOrderDate(order.getCreatedAt() != null ? order.getCreatedAt().toString() : null);
        response.setStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : "PENDING");
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setOrderItems(items);

        return response;

    }

}
