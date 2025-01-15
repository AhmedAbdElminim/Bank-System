package com.bank.bank_projecet.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ExceptionResponse {
    private String message;
    private Boolean status;
    private LocalDateTime date;
    private List<String> details;

    public ExceptionResponse(String message, List<String> details) {
        this.message = message;
        this.status = false;
        this.date = LocalDateTime.now();
        this.details = details;
    }
}
