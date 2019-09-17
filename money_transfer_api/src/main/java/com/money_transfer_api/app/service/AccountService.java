package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;

import java.util.List;



public class AccountService{

	AccountRepository accountRepository = new AccountRepository();

    /**
	 * Create account
	 */
	public long createAccount(AccountModel account) throws MessageException{
		try{
			long idKey = accountRepository.createAccount(account);
			return idKey;
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}

	public List<AccountModel> getAllAccounts() throws MessageException{
		try{
			List<AccountModel> userAccounts = accountRepository.getAllAccounts();
			return userAccounts;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
}