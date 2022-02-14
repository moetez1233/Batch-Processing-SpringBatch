package com.spBat.SpringBatchAppFirst;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
public class BankTransaction {
	@Id
	private Long id;
	private long accountID;
	private Date transactionDate;
	@Transient
	private String strTransactionDate;
	private String strTransactionType;
	private double amount ;
	
	public BankTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankTransaction(long accountID, Date transactionDate, String strTransactionDate, String strTransactionType,
			double amount) {
		super();
		this.accountID = accountID;
		this.transactionDate = transactionDate;
		this.strTransactionDate = strTransactionDate;
		this.strTransactionType = strTransactionType;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStrTransactionDate() {
		return strTransactionDate;
	}

	public void setStrTransactionDate(String strTransactionDate) {
		this.strTransactionDate = strTransactionDate;
	}

	public String getStrTransactionType() {
		return strTransactionType;
	}

	public void setStrTransactionType(String strTransactionType) {
		this.strTransactionType = strTransactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	

	

}
