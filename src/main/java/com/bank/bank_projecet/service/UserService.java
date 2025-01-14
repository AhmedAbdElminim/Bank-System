package com.bank.bank_projecet.service;

import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.CreditDebitRequest;
import com.bank.bank_projecet.dto.EnquiryRequest;
import com.bank.bank_projecet.dto.LoginDto;
import com.bank.bank_projecet.dto.TransferRequest;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.entity.User;

import jakarta.transaction.Transactional;

public interface UserService {

    BankResponse createNewUser(UserDto userDto);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    @Transactional
    BankResponse transfer (TransferRequest request);

    User  findUserByAccountNumber(String accountNumber);
    BankResponse login(LoginDto loginDto);


}
