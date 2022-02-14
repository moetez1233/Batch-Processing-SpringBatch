package com.spBat.SpringBatchAppFirst.dao.a;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spBat.SpringBatchAppFirst.BankTransaction;


public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {

}

