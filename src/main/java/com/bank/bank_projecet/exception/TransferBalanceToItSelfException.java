package com.bank.bank_projecet.exception;

public class TransferBalanceToItSelfException extends RuntimeException {
    public TransferBalanceToItSelfException() {
        super();
    }

    public TransferBalanceToItSelfException(String msg) {
        super(msg);
    }

}
