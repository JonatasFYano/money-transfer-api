package com.money_transfer_api.app;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.money_transfer_api.app.service.AccountService;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.MovementAccountModel;
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
        assertTrue( true );
    }
}
