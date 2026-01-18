package com.potragis.FlexCart.service.Auth;

import com.potragis.FlexCart.dto.login.LoginRequest;
import com.potragis.FlexCart.dto.login.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest loginRequest);
}
