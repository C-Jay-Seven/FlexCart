package com.potragis.FlexCart.service.clientToSellerRequest;

import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerRequestDTO;
import com.potragis.FlexCart.dto.clientToSeller.ClientToSellerResponseDTO;
import com.potragis.FlexCart.mapper.clientToSeller.ClientToSellerDTOMapper;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.ClientToSellerRequest;
import com.potragis.FlexCart.model.entity.Role;
import com.potragis.FlexCart.model.enums.ClientToSellerRequestStatus;
import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IClientToSellerRequestRepository;
import com.potragis.FlexCart.repository.IRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientToSellerRequestService implements IClientToSellerRequestService {

    private final IClientToSellerRequestRepository sellerRequestRepo;
    private final IClientRepository clientRepository;
    private final ClientToSellerDTOMapper mapper;
    private final IRoleRepository roleRepository;

    // Fetch client by ID
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    // Get all requests for a specific client
    public List<ClientToSellerResponseDTO> getRequestsByClientId(Long clientId) {
        Client client = findClientById(clientId);
        List<ClientToSellerRequest> requests = sellerRequestRepo.findByClient(client);
        return requests.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClientToSellerResponseDTO createRequest(ClientToSellerRequestDTO dto) {
        // Fetch managed Client entity
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Map DTO to entity
        ClientToSellerRequest request = mapper.toEntity(dto);
        request.setClient(client);
        request.setStatus(ClientToSellerRequestStatus.PENDING);

        // Save and flush immediately
        ClientToSellerRequest saved = sellerRequestRepo.saveAndFlush(request);

        return mapper.toResponse(saved);
    }

    @Transactional
    @Override
    public void approveRequest(Long requestId) {
        // Fetch the request
        ClientToSellerRequest request = sellerRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Approve the request
        request.setStatus(ClientToSellerRequestStatus.APPROVED);

        Client client = request.getClient();

        // Ensure roles list is initialized
        if (client.getRoles() == null) {
            client.setRoles(new ArrayList<>());
        }

        // Check if the client already has a SELLER role
        boolean hasSellerRole = client.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleName.SELLER));

        if (!hasSellerRole) {
            // Look in the DB first
            Role sellerRole = roleRepository.findByRoleNameAndClient(RoleName.SELLER, client)
                    .orElseGet(() -> {
                        // Create new SELLER role if not found
                        Role role = new Role();
                        role.setRoleName(RoleName.SELLER);
                        role.setDescription("Granted automatically upon seller request approval");
                        role.setActive(true);
                        role.setClient(client);
                        return roleRepository.save(role);
                    });

            client.getRoles().add(sellerRole);
        }

        // Persist updates
        sellerRequestRepo.saveAndFlush(request);
        clientRepository.saveAndFlush(client);
    }


    @Transactional
    @Override
    public void rejectRequest(Long requestId) {
        ClientToSellerRequest request = sellerRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(ClientToSellerRequestStatus.REJECTED);
        sellerRequestRepo.saveAndFlush(request);
    }

    @Override
    public List<ClientToSellerResponseDTO> getAllRequests() {
        return sellerRequestRepo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientToSellerResponseDTO getRequestById(Long requestId) {
        ClientToSellerRequest request = sellerRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapper.toResponse(request);
    }
}
