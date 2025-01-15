package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.service.impl.BankStatementServiceImpl;
import com.itextpdf.text.DocumentException;

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
    public ResponseEntity<List<Transaction>> get(@RequestParam String accountNumber, @RequestParam String startDate,
            @RequestParam String endDate) throws FileNotFoundException, DocumentException {
        return ResponseEntity.ok(bankStatementService.generateBankStatement(accountNumber, startDate, endDate));
    }

}
