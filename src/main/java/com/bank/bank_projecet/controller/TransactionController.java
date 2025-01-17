package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.service.impl.BankStatementServiceImpl;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; 
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/bank-statement")
@RequiredArgsConstructor
@Tag(name = "Transaction Management APIs")
@Validated
public class TransactionController {

    private final BankStatementServiceImpl bankStatementService;

    @Operation(summary = "Generate Bank Account Statement", description = "Generate a pdf report for user transaction in specified period by start date and end date")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @GetMapping("/get")
    public ResponseEntity<List<Transaction>> get(
            @Valid @Size(min = 10, max = 10, message = "Please enter  valid account numbber") @RequestParam String accountNumber,
            @Valid @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Date must be in the format yyyy-MM-dd and valid") @RequestParam String startDate,
            @Valid @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Date must be in the format yyyy-MM-dd and valid") @RequestParam String endDate)
            throws FileNotFoundException, DocumentException {
        return ResponseEntity.ok(bankStatementService.generateBankStatement(accountNumber, startDate, endDate));
    }

}
