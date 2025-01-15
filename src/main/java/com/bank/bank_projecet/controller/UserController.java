package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.DepositWithdrawRequest;
import com.bank.bank_projecet.dto.EnquiryRequest;
import com.bank.bank_projecet.dto.LoginDto;
import com.bank.bank_projecet.dto.TransferRequest;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management APIs")
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "Create New User Account", description = "Creating a new user and assigning an account ID")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @PostMapping("/create-account")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserDto entity) {

        return ResponseEntity.ok(userService.createNewUser(entity));
    }

    @Operation(summary = "Balance Enquiry", description = "Given ann account number ,and check how much the user has")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @GetMapping("/balance-enquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestParam String accountNum) {
        return ResponseEntity
                .ok(userService.balanceEnquiry(EnquiryRequest.builder().accountNumber(accountNum).build()));
    }

    @Operation(summary = "Name Enquiry", description = "Given ann account number ,and check user Account Name")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @GetMapping("/name-enquiry")
    public ResponseEntity<String> nameEnquiry(@RequestParam String accountNum) {
        return ResponseEntity.ok(userService.nameEnquiry(EnquiryRequest.builder().accountNumber(accountNum).build()));
    }

    @Operation(summary = "Deposit", description = "Given ann account number and amount of balance and add it in user account")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @PostMapping("/deposit")
    public ResponseEntity<BankResponse> depositAccount(@RequestBody DepositWithdrawRequest request) {

        return ResponseEntity.ok(userService.depositAccount(request));
    }

    @Operation(summary = "Withdraw", description = "Given ann account number and amount of balance and subtract it from user account")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @PostMapping("/withdraw")
    public ResponseEntity<BankResponse> withdrawAccount(@RequestBody DepositWithdrawRequest request) {

        return ResponseEntity.ok(userService.withdrawAccount(request));
    }

    @Operation(summary = "Transfer ", description = "Given ann accounts numbers for two users and amount of balance and transfer this amount from one to another")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {

        return ResponseEntity.ok(userService.transfer(request));
    }

    @Operation(summary = "Login ", description = "User  can make login usering email and password")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @PostMapping("/login")
    public ResponseEntity<BankResponse> login(@RequestBody LoginDto loginDto) {

        return ResponseEntity.ok(userService.login(loginDto));
    }

}
