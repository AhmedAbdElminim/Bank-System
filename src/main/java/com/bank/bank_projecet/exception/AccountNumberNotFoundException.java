package com.bank.bank_projecet.exception;

public class AccountNumberNotFoundException extends RuntimeException{
    public AccountNumberNotFoundException() {
        super();
    }

    public AccountNumberNotFoundException(String message) {
        super(message);
    }
}
