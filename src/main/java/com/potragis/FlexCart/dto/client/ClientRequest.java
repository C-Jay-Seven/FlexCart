package com.potragis.FlexCart.dto.client;


import com.potragis.FlexCart.model.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @NotBlank(message = "First name must not empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Username must not be empty")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    @NotBlank(message = "Email must not be empty")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    private String email;

    @NotBlank(message = "Phone number must not be empty")
    @Size(min = 2, max = 50, message = "Phone number must be between 2 and 50 characters")
    private String phoneNumber;

    @NotBlank(message = "Address must not be empty")
    @Size(min = 5, max = 250, message = "Address must be between 2 and 50 characters")
    private String address;

    public Role role;
}
