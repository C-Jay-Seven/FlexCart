package com.potragis.FlexCart.mapper.orderItemsMapper;

import com.potragis.FlexCart.dto.orderItems.OrderItemsRequest;
import com.potragis.FlexCart.dto.orderItems.OrderItemsResponse;
import com.potragis.FlexCart.model.entity.Images;
import com.potragis.FlexCart.model.entity.OrderItems;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemsDTOMapper {

    public OrderItems toEntity(OrderItemsRequest orderItemsRequest) {
        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(orderItemsRequest.getQuantity());
        orderItems.setUnitPrice(orderItemsRequest.getUnitPrice());
        orderItems.setSubtotal(orderItemsRequest.getSubtotal());
        return orderItems;
    }

    public OrderItemsResponse toDTO(OrderItems orderItems) {
        // Convert product images to list of URLs
        List<String> imageUrls = orderItems.getProduct().getImages()
                .stream()
                .map(Images::getUrl) // replace getUrl() if your field is different
                .toList();

        return new OrderItemsResponse(
                orderItems.getId(),
                orderItems.getProduct().getId(),
                orderItems.getProduct().getProductName(),
                imageUrls, // use list of URLs instead of toString()
                orderItems.getQuantity(),
                orderItems.getUnitPrice(),
                orderItems.getSubtotal()
        );
    }

    public List<OrderItemsResponse> toDTO(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(this::toDTO)
                .toList();
    }

}
