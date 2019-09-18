package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.MovementAccountModel;
import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;

import java.math.BigDecimal;

import java.util.List;



public class AccountService{

	AccountRepository accountRepository = new AccountRepository();

    /**
	 * Create account
	 */
	public long createAccount(AccountModel account) throws MessageException{
		try{
			return accountRepository.createAccount(account);
			
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}

	public List<AccountModel> getAllAccounts() throws MessageException{
		try{
			return accountRepository.getAllAccounts();

		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	public AccountModel getAccount(String userNameAccount) throws MessageException{
		try{
			return accountRepository.getAccount(userNameAccount);

		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}

	public AccountModel deposit(String userNameAccount, MovementAccountModel movementAccountModel) throws MessageException{
		try{
			AccountModel accountUser = getAccount(userNameAccount);
			BigDecimal totalAmountUser = accountUser.getTotalBalance();
			BigDecimal newBalance = totalAmountUser.add(movementAccountModel.getAmount());

			return accountRepository.updateTotalBalance(userNameAccount, newBalance);

		}catch(Exception e){
			System.out.println(e);
			throw new MessageException("deposit(): Error when depositing to user: " + userNameAccount, e);
		}
	}

	public AccountModel withdraw(String userNameAccount, MovementAccountModel movementAccountModel) throws MessageException{
		try{
			AccountModel accountUser = getAccount(userNameAccount);
			BigDecimal totalAmountUser = accountUser.getTotalBalance();
			BigDecimal newBalance = totalAmountUser.subtract(movementAccountModel.getAmount());

			if(newBalance.compareTo(BigDecimal.ZERO) < 0){
				throw new MessageException("withdraw(): Error when withdrawing from user - Not sufficient Fund for account");
			}

			return accountRepository.updateTotalBalance(userNameAccount, newBalance);

		}catch(Exception e){
			System.out.println(e);
			throw new MessageException("withdraw(): Error when withdrawing from user: " + userNameAccount, e);
		}
	}
}