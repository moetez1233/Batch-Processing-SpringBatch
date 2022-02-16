package com.spBat.SpringBatchAppFirst;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.Getter;

//@Component
public class BankTransactionItemUpdateProcessor implements ItemProcessor<BankTransaction, BankTransaction> {


	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws ParseException {
		if(bankTransaction.getStrTransactionType().equals("visa-electron")) bankTransaction.setStrTransactionType("accepted");

		return bankTransaction;
	}

	

}
