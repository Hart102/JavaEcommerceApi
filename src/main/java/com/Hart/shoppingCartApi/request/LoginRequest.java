package com.Hart.shoppingCartApi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank //Prevent user from submitting empty field
    private String email;
    @NotBlank
    private String password;
}
