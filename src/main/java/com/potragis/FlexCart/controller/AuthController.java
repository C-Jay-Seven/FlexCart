package com.potragis.FlexCart.controller;

import com.potragis.FlexCart.dto.login.LoginRequest;
import com.potragis.FlexCart.dto.login.LoginResponse;
import com.potragis.FlexCart.service.Auth.AuthService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
