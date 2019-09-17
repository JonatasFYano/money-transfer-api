package com.money_transfer_api.app.controller;

import javax.ws.rs.*;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.service.AccountService;
import com.money_transfer_api.app.exception.MessageException;


/**
 * Account Controller 
 */
@Path("/account")
public class AccountController {

    AccountService accountService = new AccountService();


    @POST
    @Path("/create")
    public long createAccount(AccountModel account) throws MessageException {
        long accountId = accountService.createAccount(account);
        return accountId;
    }
}
