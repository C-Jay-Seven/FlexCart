package com.potragis.FlexCart.mapper.client;

import com.potragis.FlexCart.dto.client.ClientRequest;
import com.potragis.FlexCart.dto.client.ClientResponse;
import com.potragis.FlexCart.model.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDTOMapper {

    public Client toEntity(ClientRequest clientRequest) {
        Client client = new Client();
        client.setFirstName(clientRequest.getFirstName());
        client.setLastName(clientRequest.getLastName());
        client.setUsername(clientRequest.getUsername());
        client.setPassword(clientRequest.getPassword());
        client.setEmail(clientRequest.getEmail());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setAddress(clientRequest.getAddress());
        return client;
    }

    public ClientResponse toDTO(Client client) {
        return new ClientResponse(client);
    }

    public List<ClientResponse> toDTO(List<Client> clients) {
        return clients.stream()
                .map(this::toDTO)
                .toList();
    }

}
