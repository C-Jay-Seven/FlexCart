package com.potragis.FlexCart.service.clientToSellerRequest;

import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerRequestDTO;
import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerResponseDTO;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.ClientToSellerRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IClientToSellerRequestService {

    Client findClientById(Long clientId);

    ClientToSellerResponseDTO createRequest(ClientToSellerRequestDTO request);

    void approveRequest(Long requestId);

    void rejectRequest(Long requestId);

    List<ClientToSellerResponseDTO> getAllRequests();

    List<ClientToSellerResponseDTO> getRequestsByClientId(Long clientId);

    ClientToSellerResponseDTO getRequestById(Long requestId);
}
