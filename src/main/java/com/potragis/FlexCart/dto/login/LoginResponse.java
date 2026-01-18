package com.potragis.FlexCart.dto.login;

import com.potragis.FlexCart.model.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long id;
    private String username;
    private RoleName role;

    public LoginResponse(Long id, String username, RoleName role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

}
