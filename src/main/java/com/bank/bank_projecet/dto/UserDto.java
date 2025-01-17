package com.bank.bank_projecet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter 
@Getter
public class UserDto {
    @Schema(name = "User First Name")
    @NotBlank(message = "First Name must not be blank")
    @NotNull(message = "First Name must not be null")
    private String f_Name;
    @Schema(name = "User Last Name")
    @NotBlank(message = "Last Name must not be blank")
    @NotNull(message = "Last Name must not be null")
    private String l_Name;
    @Schema(name = "User Phone Number")
    @Pattern(regexp = "^(01)([0-2]|5)[0-9]{8}$", message = "Invalid mobile number")
    @NotBlank(message = "Phone Number must not be blank")
    @NotNull(message = "Phone Number must not be null")
    private String phone;
    @Schema(name = "User Email Address")
    @Email(message = "You Should enter a Valid email")
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    private String email;
    @Schema(name = "User Account Password")
    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    @Size(min = 5, message = "Password must not be less than 5 char")
    private String password;
    @Schema(name = "User Address")
    @NotBlank(message = "Address must not be blank")
    @NotNull(message = "Address must not be null")
    private String address;
    @Schema(name = "User Gender")
    @NotBlank(message = "Gender must not be blank")
    @NotNull(message = "Gender must not be null")
    @Pattern(regexp = "^(Male|male|Female|female)$", message = "Gender must be either 'Male' or 'Female'")
    private String gender;
}
