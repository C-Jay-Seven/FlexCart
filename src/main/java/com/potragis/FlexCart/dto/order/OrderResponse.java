package com.potragis.FlexCart.dto.order;

import com.potragis.FlexCart.dto.orderItems.OrderItemsResponse;
import com.potragis.FlexCart.model.enums.PaymentMethod;
import com.potragis.FlexCart.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long clientId;
    private BigDecimal totalPrice;
    private String orderDate;
    private String status;
    private PaymentMethod paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
    private PaymentStatus paymentStatus;

    private List<OrderItemsResponse> orderItems;

    public OrderResponse(Long id,
                         Long id1,
                         BigDecimal totalAmount,
                         String string,
                         String name,
                         List<OrderItemsResponse> items) {
        this.orderId = id;
        this.clientId = id1;
        this.totalPrice = totalAmount;
        this.orderDate = string;
        this.status = name;
        this.orderItems = items;
    }
}
