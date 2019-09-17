package com.money_transfer_api.app.service;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;



public class AccountService{

	AccountRepository accountRepository = new AccountRepository();

    /**
	 * Create account
	 */
	public long createAccount(AccountModel account) throws MessageException{
		try{
		long idKey = accountRepository.createAccount(account);
		System.out.println(idKey);
		return idKey;
	}catch(Exception e){
		System.out.println(e);
		return 0;
	}
	
}
}