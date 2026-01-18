package com.potragis.FlexCart.dto.cart;

import com.potragis.FlexCart.dto.cartItems.CartItemsResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    @NotNull(message = "Client id must not be null")
    private Long clientId;

    private List<CartItemsResponse> cartItems;
}
