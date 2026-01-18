package com.potragis.FlexCart.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClientResponse {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;

    private List<String> roles;

    @JsonProperty("isActive")
    private boolean isActive;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.username = client.getUsername();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.isActive = client.isActive();
        this.roles = client.getRoles() != null
                ? client.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList())
                : List.of();
    }
}
