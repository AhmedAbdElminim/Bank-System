package com.bank.bank_projecet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.bank_projecet.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
