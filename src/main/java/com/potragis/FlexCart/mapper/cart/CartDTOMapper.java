package com.potragis.FlexCart.mapper.cart;

import com.potragis.FlexCart.dto.cart.CartRequest;
import com.potragis.FlexCart.dto.cart.CartResponse;
import com.potragis.FlexCart.dto.cartItems.CartItemsResponse;
import com.potragis.FlexCart.model.entity.Cart;
import com.potragis.FlexCart.model.entity.CartItems;
import com.potragis.FlexCart.model.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDTOMapper {

    public Cart toEntity(CartRequest cartRequest, List<Product> productList) {

        Cart cart = new Cart();

        List<CartItems> cartItems = cartRequest.getCartItems().stream()
                .map(item -> {
                    CartItems cartItem = new CartItems();
                    Product product = productList.stream()
                            .filter(p -> p.getId().equals(item.getProductId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));

                    cartItem.setProduct(product);
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setUnitPrice(product.getPrice());
                    cartItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    return cartItem;
                    
                }).collect(Collectors.toList());

        cart.setCartItems(cartItems);
        return cart;
    }

    public CartResponse toDTO(Cart cart) {

        List<CartItemsResponse> cartItems = cart.getCartItems().stream()
                .map(CartItemsResponse::new)
                .toList();

        BigDecimal total = cartItems.stream()
                .map(CartItemsResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
                cart.getId(),
                cart.getClient().getId(),
                cartItems,
                total
        );
    }
}