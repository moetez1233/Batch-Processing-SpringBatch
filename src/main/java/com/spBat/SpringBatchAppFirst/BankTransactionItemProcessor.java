package com.spBat.SpringBatchAppFirst;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction>{
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy-HH:mm");
	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws ParseException {
		/* get data A modifier et enregistrer dans B*/
		bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));
		return bankTransaction;
	}

}
