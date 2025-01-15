package com.bank.bank_projecet.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String accountNumber;
    @Schema(name = "User Credit or Debit Amount")
    private BigDecimal amount;

}
