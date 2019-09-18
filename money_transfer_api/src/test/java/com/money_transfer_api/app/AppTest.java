package com.money_transfer_api.app;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.money_transfer_api.app.service.AccountService;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.repository.H2DataFactory;
import com.money_transfer_api.app.exception.MessageException;
import com.money_transfer_api.app.repository.AccountRepository;
import com.money_transfer_api.app.repository.H2DataFactory;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    
    @Test
    public void shouldAnswerWithTrue()
    {
        try{
        AccountService accountService = mock(AccountService.class);

        AccountRepository accountRepository = mock(AccountRepository.class);
 
        AccountModel accountModel = new AccountModel("jonatas", new BigDecimal(449.9877).setScale(4, RoundingMode.HALF_EVEN));

        // define behavior of collaborating class
        when(accountRepository.createAccount(accountModel)).thenReturn(new Long(1)); 

        long result = accountService.createAccount(accountModel);
        System.out.println(result);

        assertTrue( result == 0 );
    
    }catch(Exception e){

    }
    }
}
