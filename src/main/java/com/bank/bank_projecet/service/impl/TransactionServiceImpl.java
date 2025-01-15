package com.bank.bank_projecet.service.impl;

import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.TransactionDto;
import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.repository.TransactionRepository;
import com.bank.bank_projecet.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .status("SUCCESS")
                .amount(transactionDto.getAmount())
                .build();
        transactionRepository.save(transaction);

    }

}
