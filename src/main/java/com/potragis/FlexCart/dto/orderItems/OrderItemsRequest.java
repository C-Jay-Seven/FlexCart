package com.potragis.FlexCart.dto.orderItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequest {

    private Long productId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

}
