package com.money_transfer_api.app.controller;

import javax.ws.rs.*;

import com.money_transfer_api.app.repository.RepoFactory;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.service.AccountService;


/**
 * Account Controller 
 */
@Path("/account")
public class AccountController {

    private final RepoFactory repoFactory = RepoFactory.getRepoFactory(RepoFactory.H2);
    AccountService accountService = new AccountService();


    @POST
    @Path("/create")
    public String createAccount(AccountModel account) {
        final long accountId = accountService.createAccount(account);
        return "oi";
    }
}
