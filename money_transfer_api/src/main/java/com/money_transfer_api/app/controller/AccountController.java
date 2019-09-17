package com.money_transfer_api.app.controller;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.service.AccountService;
import com.money_transfer_api.app.exception.MessageException;

import java.util.List;


/**
 * Account Controller 
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    AccountService accountService = new AccountService();


    @POST
    @Path("/create")
    public long createAccount(AccountModel account) throws MessageException {
        long accountId = accountService.createAccount(account);
        return accountId;
    }

    @GET
    @Path("/")
    public List<AccountModel> getAllAccounts() throws MessageException {
        List<AccountModel> accounts = accountService.getAllAccounts();
        return accounts;
    }
}
