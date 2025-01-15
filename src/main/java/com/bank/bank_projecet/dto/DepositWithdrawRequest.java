package com.bank.bank_projecet.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
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
public class DepositWithdrawRequest {
    @Schema(name = "User Account Name")
    @Size(max = 10,min=10,message = "Please enter a valid account number")
    @NotBlank(message = "Please enter Account Nmuber")
    @NotNull(message = "Account number must not be null")
    private String accountNumber;
    @Schema(name = "User Credit or Debit Amount")
    @Digits(integer=6, fraction=2,message = "Please enter your amount of balance that you need to deposit or withdraw")
    @NotNull
    private BigDecimal amount;

}
