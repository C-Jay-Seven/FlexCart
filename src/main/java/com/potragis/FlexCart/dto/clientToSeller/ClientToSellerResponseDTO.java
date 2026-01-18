package com.potragis.FlexCart.dto.clientToSeller;

import com.potragis.FlexCart.model.enums.ClientToSellerRequestStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientToSellerResponseDTO {
    private Long id;
    private Long clientId;
    private String shopName;
    private ClientToSellerRequestStatus status;
}
