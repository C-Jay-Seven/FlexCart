package com.potragis.FlexCart.dto.order;

import com.potragis.FlexCart.dto.orderItems.OrderItemsRequest;
import com.potragis.FlexCart.dto.orderItems.OrderItemsResponse;
import com.potragis.FlexCart.model.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    private Long clientId;

    private int quantity;

    private PaymentMethod paymentMethod = PaymentMethod.CASH_ON_DELIVERY;

    private List<OrderItemsRequest> orderItems;

    private String address;

}
