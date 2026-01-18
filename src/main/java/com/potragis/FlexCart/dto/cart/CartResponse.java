package com.potragis.FlexCart.dto.cart;

import com.potragis.FlexCart.dto.cartItems.CartItemsResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CartResponse {

    private final Long cartId;
    private final Long clientId;
    private final List<CartItemsResponse> cartItems;
    private final BigDecimal totalPrice;

    public CartResponse(Long cartId,
                        Long clientId,
                        List<CartItemsResponse> cartItems,
                        BigDecimal totalPrice) {
        this.cartId = cartId;
        this.clientId = clientId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }
}
