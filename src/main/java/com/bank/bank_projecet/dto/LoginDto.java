package com.bank.bank_projecet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @Email(message = "You Should enter a Valid email")
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    @Size(min = 5, message = "Password is incorrect")
    private String password;

}
