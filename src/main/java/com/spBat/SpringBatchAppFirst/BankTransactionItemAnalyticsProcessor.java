package com.spBat.SpringBatchAppFirst;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.Getter;

//@Component
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor<BankTransaction, BankTransaction> {

	private double totalDebit;
	
	private double totalCredit;

	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws ParseException {
		if(bankTransaction.getStrTransactionType().equals("visa-electron")) totalDebit+=bankTransaction.getAmount();
		else if(bankTransaction.getStrTransactionType().equals("americanexpress")) totalCredit+=bankTransaction.getAmount();

		return bankTransaction;
	}

	public Double getTotalCredit() {
		// TODO Auto-generated method stub
		return totalDebit;
	}

	public Double getTotalDebit() {
		// TODO Auto-generated method stub
		return totalCredit;
	}

}
