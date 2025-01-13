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
public class TransferRequest {
     @Schema(name = "Sender User Account Number")
     private String senderAccountNumber;
     @Schema(name = "Reciver User Account Number")
     private String reciverAcountNumber;
     @Schema(name = "The Amount of balance that transfer")
     private BigDecimal amount;

}
