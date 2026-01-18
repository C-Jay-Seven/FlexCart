package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.cart.CartResponse;
import com.potragis.FlexCart.dto.cartItems.CartItemsRequest;
import com.potragis.FlexCart.service.cart.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{clientId}")
    public CartResponse getCart(@PathVariable Long clientId) {
        return cartService.getCart(clientId);
    }

    @PostMapping("/{clientId}/items")
    public CartResponse addProductToCart(@PathVariable Long clientId,
                                         @Valid @RequestBody CartItemsRequest cartItemsRequest) {
        return cartService.addToCart(clientId, cartItemsRequest);
    }

    @PutMapping("/{clientId}/items/{cartItemId}")
    public CartResponse updateCart(@PathVariable Long clientId,
                                   @PathVariable Long cartItemId,
                                   @Valid @RequestBody CartItemsRequest cartItemsRequest) {
        return cartService.updateCart(clientId, cartItemId, cartItemsRequest);
    }

    @DeleteMapping("/{clientId}/items/{cartItemId}")
    public CartResponse deleteCartItem(@PathVariable Long clientId,
                                       @PathVariable Long cartItemId) {
        return cartService.deleteCartItem(clientId, cartItemId);
    }

}
