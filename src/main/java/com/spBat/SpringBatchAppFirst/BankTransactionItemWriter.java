package com.spBat.SpringBatchAppFirst;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spBat.SpringBatchAppFirst.dao.a.BankTransactionRepository;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	@Override
	public void write(List<? extends BankTransaction> listItems) throws Exception {
		bankTransactionRepository.saveAll(listItems);

	}

}
