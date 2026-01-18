package com.potragis.FlexCart.dto.orderItems;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderItemsResponse {

    private Long orderItemId;
    private Long productId;
    private String productName;
    private List<String> productImage; // <-- new
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public OrderItemsResponse(Long orderItemId,
                              Long productId,
                              String productName,
                              List<String> productImage,
                              int quantity,
                              BigDecimal unitPrice,
                              BigDecimal subtotal) {
        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }
}
