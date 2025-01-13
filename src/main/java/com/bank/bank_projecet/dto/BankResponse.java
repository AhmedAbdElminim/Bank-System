package com.bank.bank_projecet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    @Schema(name = "Bank Response Code")
    private String responseCode;
    @Schema(name = "Bank Response Message")
    private String responseMessage;
    @Schema(name = "Bank Response Account Information")
    private AccountInfo accountInfo;

}
