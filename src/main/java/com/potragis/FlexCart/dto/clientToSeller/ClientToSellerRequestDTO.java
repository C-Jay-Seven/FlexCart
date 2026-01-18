package com.potragis.FlexCart.dto.clientToSeller;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientToSellerRequestDTO {

    @NotNull
    private Long clientId;

    @NotNull
    private String shopName;
}
