package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.service.impl.BankStatementServiceImpl;
import com.itextpdf.text.DocumentException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/bank-statement")
@RequiredArgsConstructor
public class TransactionController {

    private final BankStatementServiceImpl bankStatementService;

    @GetMapping("/get")
    public ResponseEntity<List<Transaction>> get(
            @Valid @Size(min = 10, max = 10, message = "Please enter  valid account numbber") @RequestParam String accountNumber,
            @Valid @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Date must be in the format yyyy-MM-dd and valid") @RequestParam String startDate,
            @Valid @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Date must be in the format yyyy-MM-dd and valid") @RequestParam String endDate)
            throws FileNotFoundException, DocumentException {
        return ResponseEntity.ok(bankStatementService.generateBankStatement(accountNumber, startDate, endDate));
    }

}
