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
public class EnquiryRequest {
    @Schema(name = "User Account Number")
    private String accountNumber;

}
