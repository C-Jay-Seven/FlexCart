package com.potragis.FlexCart.dto.cartItems;

import com.potragis.FlexCart.model.entity.CartItems;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class CartItemsResponse {

    private final Long id;
    private final Long productId;
    private final String productName;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal subtotal;

    public CartItemsResponse(CartItems cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId();
        this.productName = cartItem.getProduct().getProductName();
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
        this.subtotal = cartItem.getSubtotal();
    }

}
