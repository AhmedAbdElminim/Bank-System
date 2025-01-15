package com.bank.bank_projecet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.bank_projecet.entity.Transaction;

@Repository
public interface ScheduledTasksRepository extends JpaRepository<Transaction,String>{

    @Query("SELECT DISTINCT t.accountNumber FROM Transaction t")
    List<String> findDistinctAccountNumbers();

   // @Query("SELECT DISTINCT t.accountNumber FROM Transaction t")
    //Set<String> findAllUniqueAccountNumbers();

}
