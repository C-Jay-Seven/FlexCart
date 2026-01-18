package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerRequestDTO;
import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerResponseDTO;
import com.potragis.FlexCart.repository.IClientToSellerRequestRepository;
import com.potragis.FlexCart.service.clientToSellerRequest.IClientToSellerRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller-requests")
@RequiredArgsConstructor
public class ClientToSellerRequestController {

    private final IClientToSellerRequestRepository sellerRequestRepo;
    private final IClientToSellerRequestService clientToSellerRequestService;

    @PostMapping
    @Transactional
    public ClientToSellerResponseDTO createRequest(@RequestBody ClientToSellerRequestDTO dto) {
        return clientToSellerRequestService.createRequest(dto);
    }

    @PutMapping("/{requestId}/approve")
    @Transactional
    public void approveRequest(@PathVariable Long requestId) {
        clientToSellerRequestService.approveRequest(requestId);
    }

    @PutMapping("/{requestId}/reject")
    @Transactional
    public void rejectRequest(@PathVariable Long requestId) {
        clientToSellerRequestService.rejectRequest(requestId);
    }

    @GetMapping
    public List<ClientToSellerResponseDTO> getAllRequests() {
        return clientToSellerRequestService.getAllRequests();
    }

    @GetMapping("/client/{clientId}")
    public List<ClientToSellerResponseDTO> getRequestsByClient(@PathVariable Long clientId) {
        return clientToSellerRequestService.getRequestsByClientId(clientId);
    }

}
