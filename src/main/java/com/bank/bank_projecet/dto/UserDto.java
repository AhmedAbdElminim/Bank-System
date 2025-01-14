package com.bank.bank_projecet.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
private String f_Name;
@Schema(name = "User Last Name")
private String l_Name;
@Schema(name = "User Phone Number")
private String phone;
@Schema(name = "User Email Address")
private String email;
@Schema(name = "User Account Password")
private String password;
@Schema(name = "User Address")
private String address;
@Schema(name = "User Gender")
private String gender;
}
