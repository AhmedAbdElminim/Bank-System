package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;



    @PostMapping("/create-account")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserDto entity) {
      
        
        return ResponseEntity.ok(userService.createNewUser(entity));
    }
    

}
