package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.TransactionModel;
import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;

import java.math.BigDecimal;

import java.util.List;
import java.util.ArrayList;



public class TransactionService{

	AccountRepository accountRepository = new AccountRepository();
	
	public List<AccountModel> transferAmount(TransactionModel transaction) throws MessageException{
		try{
			BigDecimal amount = transaction.getAmount();
			String fromAccountUsername = transaction.getfromAccountUsername();
			String toAccountUsername = transaction.gettoAccountUsername();
			List<AccountModel> accounts = new ArrayList<AccountModel>();

			AccountModel accountUserFrom = accountRepository.getAccount(fromAccountUsername);
			BigDecimal totalAmountUserFrom = accountUserFrom.getTotalBalance();
			BigDecimal newBalanceFrom = totalAmountUserFrom.subtract(amount);

			if(newBalanceFrom.compareTo(BigDecimal.ZERO) < 0){
				throw new MessageException("withdraw(): Error transaction - Not sufficient Fund for account");
			}

			AccountModel accountFrom = accountRepository.updateTotalBalance(fromAccountUsername, newBalanceFrom);

			if(newBalanceFrom.compareTo(accountFrom.getTotalBalance()) != 0){
				throw new MessageException("deposit(): Error transaction, could not withdraw from user");
			}

			accounts.add(accountFrom);

			AccountModel accountUserTo = accountRepository.getAccount(toAccountUsername);
			BigDecimal totalAmountUserTo = accountUserTo.getTotalBalance();
			BigDecimal newBalanceTo = totalAmountUserTo.add(amount);

			AccountModel accountTo = accountRepository.updateTotalBalance(toAccountUsername, newBalanceTo);

			if(newBalanceTo.compareTo(accountTo.getTotalBalance()) != 0){
				throw new MessageException("deposit(): Error transaction, could not withdraw from user");
			}

			accounts.add(accountTo);

			return accounts;
		}catch(Exception e){
			System.out.println(e);
			throw new MessageException("deposit(): Error transaction", e);
		}
	}
}