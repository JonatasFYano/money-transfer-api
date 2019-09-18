package com.money_transfer_api.app.controller;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.MovementAccountModel;
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

    @GET
    @Path("/{UsernameAccount}")
    public AccountModel getAccount(@PathParam("UsernameAccount") String userNameAccount) throws MessageException {
        AccountModel account = accountService.getAccount(userNameAccount);
        return account;
    }

    @PUT
    @Path("/{UsernameAccount}/deposit")
    public AccountModel deposit(@PathParam("UsernameAccount") String userNameAccount, 
                                        MovementAccountModel movementAccountModel) throws MessageException {
        AccountModel account = accountService.deposit(userNameAccount, movementAccountModel);
        return account;
    }

    @PUT
    @Path("/{UsernameAccount}/withdraw")
    public AccountModel withdraw(@PathParam("UsernameAccount") String userNameAccount, 
                                        MovementAccountModel movementAccountModel) throws MessageException {
        AccountModel account = accountService.withdraw(userNameAccount, movementAccountModel);
        return account;
    }
}
