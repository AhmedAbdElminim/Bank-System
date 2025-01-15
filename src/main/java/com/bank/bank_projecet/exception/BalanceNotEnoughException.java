package com.bank.bank_projecet.exception;

public class BalanceNotEnoughException extends RuntimeException {
    public BalanceNotEnoughException() {
        super();
    }

    public BalanceNotEnoughException(String msg) {
        super(msg);
    }
}
