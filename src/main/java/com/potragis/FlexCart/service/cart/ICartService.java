package com.potragis.FlexCart.service.cart;

import com.potragis.FlexCart.dto.cart.CartResponse;
import com.potragis.FlexCart.dto.cartItems.CartItemsRequest;

public interface ICartService {
    CartResponse getCart(Long clientId);
    CartResponse addToCart(Long clientId, CartItemsRequest cartItemsRequest);
    CartResponse updateCart(Long clientId, Long cartItemId, CartItemsRequest cartItemsRequest);
    CartResponse deleteCartItem(Long clientId, Long cartItemId);
}
