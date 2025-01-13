package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.CreditDebitRequest;
import com.bank.bank_projecet.dto.EnquiryRequest;
import com.bank.bank_projecet.dto.TransferRequest;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;



    @PostMapping("/create-account")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserDto entity) {
      
        
        return ResponseEntity.ok(userService.createNewUser(entity));
    }
    

    @GetMapping("/balance-enquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestParam String accountNum) {
        return ResponseEntity.ok(userService.balanceEnquiry(EnquiryRequest.builder().accountNumber(accountNum).build()));
    }

    @GetMapping("/name-enquiry")
    public ResponseEntity<String> nameEnquiry(@RequestParam String accountNum) {
        return ResponseEntity.ok(userService.nameEnquiry(EnquiryRequest.builder().accountNumber(accountNum).build()));
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody CreditDebitRequest request) {
       
        
        return ResponseEntity.ok(userService.creditAccount(request));
    }
    @PostMapping("/debit")
    public ResponseEntity<BankResponse> debitAccount(@RequestBody CreditDebitRequest request) {
       
        
        return ResponseEntity.ok(userService.debitAccount(request));
    }



    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {
       
        
        return ResponseEntity.ok(userService.transfer(request));
    }
    
}
