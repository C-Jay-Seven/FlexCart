package com.potragis.FlexCart.service.Auth;

import com.potragis.FlexCart.dto.login.LoginRequest;
import com.potragis.FlexCart.dto.login.LoginResponse;
import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.repository.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

    private final IClientRepository clientRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Client client = clientRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!client.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // 🔒 Block check
        if (!client.isActive()) {
            throw new RuntimeException("Your account is blocked. Contact admin.");
        }

        // Safely get the first role
        RoleName role;
        if (!client.getRoles().isEmpty()) {
            role = client.getRoles().getFirst().getRoleName();
        } else {
            role = RoleName.CLIENT; // default role
        }

        return new LoginResponse(client.getId(), client.getUsername(), role);
    }

}
