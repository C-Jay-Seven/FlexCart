package com.potragis.FlexCart.mapper.clientToSeller;

import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerRequestDTO;
import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerResponseDTO;
import com.potragis.FlexCart.model.entity.ClientToSellerRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientToSellerDTOMapper {
    public ClientToSellerRequest toEntity(ClientToSellerRequestDTO dto) {
        ClientToSellerRequest entity = new ClientToSellerRequest();
        entity.setShopName(dto.getShopName());
        return entity;
    }

    public ClientToSellerResponseDTO toResponse(ClientToSellerRequest entity) {
        ClientToSellerResponseDTO dto = new ClientToSellerResponseDTO();
        dto.setId(entity.getId());
        dto.setShopName(entity.getShopName());
        dto.setStatus(entity.getStatus());
        dto.setClientId(entity.getClient().getId());
        return dto;
    }
}
