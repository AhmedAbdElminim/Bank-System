package com.bank.bank_projecet.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {

     private String senderAccountNumber;
     private String reciverAcountNumber;
     private BigDecimal amount;

}
