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
public class TransferRequest {
     @Schema(name = "Sender User Account Number")
     @NotBlank(message = "Please enter your account number")
     @NotNull(message = "Please enter your account number")
     @Size(min = 10, max = 10, message = "Please enter  valid account numbber")
     private String senderAccountNumber;
     @Schema(name = "Reciver User Account Number")
     @NotBlank(message = "Please enter receiver account number")
     @NotNull(message = "Please enter receiver account number")
     @Size(min = 10, max = 10, message = "Please enter valid reciver account numbber")
     private String reciverAcountNumber;
     @Schema(name = "The Amount of balance that transfer")
     private BigDecimal amount;

}
