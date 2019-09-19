package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.TransactionModel;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;

import java.math.BigDecimal;

import java.util.List;



public class TransactionService{

	AccountRepository accountRepository = new AccountRepository();
	
	public List<AccountModel> transferAmount(TransactionModel transaction) throws MessageException{
		try{
			BigDecimal amount = transaction.getAmount();
			String fromAccountUsername = transaction.getfromAccountUsername();
			String toAccountUsername = transaction.gettoAccountUsername();

			return accountRepository.transaction(fromAccountUsername, toAccountUsername, amount);

		}catch(Exception e){
			System.out.println(e);
			throw new MessageException("deposit(): Error transaction", e);
		}
	}
}