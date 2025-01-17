package com.bank.bank_projecet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class EmailDetails {
    @Schema(name = "Reciver Email")
    private String recipient;
    @Schema(name = "Content of the Email")
    private String messageBody;
    @Schema(name = "Subject of Email")
    private String subject;
    @Schema(name = "Attachment of Email")
    private String attachment;
}
