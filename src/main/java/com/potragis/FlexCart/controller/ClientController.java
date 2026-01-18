package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.client.ClientRequest;
import com.potragis.FlexCart.dto.client.ClientResponse;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.service.client.IClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final IClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<ClientResponse> addClient(@Valid @RequestBody ClientRequest clientRequest) {
        ClientResponse saved = clientService.createClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResponse> fetchClientById(@PathVariable Long clientId) {
        ClientResponse client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> fetchAllClients() {
        return ResponseEntity.ok(clientService.getAllClient());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(
            @Valid @PathVariable Long id,
            @RequestBody ClientRequest clientRequest) {

        ClientResponse updated = clientService.updateClient(id, clientRequest);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ClientResponse> updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        boolean isActive = body.get("isActive") != null && body.get("isActive");
        ClientResponse updated = clientService.setIsActive(id, isActive);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/avatar")
    public ResponseEntity<String> updateAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        String avatarUrl = clientService.updateAvatar(id, file);
        return ResponseEntity.ok(avatarUrl);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(
            @PathVariable Long id,
            @RequestParam RoleName roleName) {

        try {
            clientService.deleteClient(id, roleName);
            return ResponseEntity.ok("Client deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/current-seller")
    public ResponseEntity<ClientResponse> getCurrentSeller(@RequestHeader("X-CLIENT-ID") Long clientId) {
        try {
            ClientResponse currentSeller = clientService.getCurrentSeller(clientId);
            return ResponseEntity.ok(currentSeller);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
