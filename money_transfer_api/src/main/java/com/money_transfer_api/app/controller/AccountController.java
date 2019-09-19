package com.money_transfer_api.app.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.MovementAccountModel;
import com.money_transfer_api.app.service.AccountService;
import com.money_transfer_api.app.exception.MessageException;


/**
 * Account Controller 
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

    AccountService accountService = new AccountService();
    public ObjectMapper mapper = new ObjectMapper();


    @POST
    @Path("/create")
    public Response createAccount(AccountModel account) throws MessageException, URISyntaxException {

        accountService.createAccount(account);
        URI u = new URI("localhost:8001/account");
        return Response.created(u).build();
    }

    @GET
    @Path("/")
    public Response getAllAccounts() throws MessageException, IOException {
        List<AccountModel> accounts = accountService.getAllAccounts();
        if(accounts == null || accounts.isEmpty()){
            return Response.noContent().build();
        }
        else{
            String jsonResult = mapper.writeValueAsString(accounts);
            return Response.ok(jsonResult).build();
        }
    }

    @GET
    @Path("/{UsernameAccount}")
    public Response getAccount(@PathParam("UsernameAccount") String userNameAccount) throws MessageException, IOException {
        AccountModel account = accountService.getAccount(userNameAccount);
        if(account == null){
            return Response.noContent().build();
        }else{
            String jsonResult = mapper.writeValueAsString(account);
            return Response.ok(jsonResult).build();
        }
    }

    @PUT
    @Path("/{UsernameAccount}/deposit")
    public Response deposit(@PathParam("UsernameAccount") String userNameAccount, 
                                        MovementAccountModel movementAccountModel) throws MessageException , IOException {
        AccountModel account = accountService.deposit(userNameAccount, movementAccountModel);
        if(account == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        String jsonResult = mapper.writeValueAsString(account);
        return Response.ok(jsonResult).build();
    }

    @PUT
    @Path("/{UsernameAccount}/withdraw")
    public Response withdraw(@PathParam("UsernameAccount") String userNameAccount, 
                                        MovementAccountModel movementAccountModel) throws MessageException , IOException {
        AccountModel account = accountService.withdraw(userNameAccount, movementAccountModel);
        if(account == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        String jsonResult = mapper.writeValueAsString(account);
        return Response.ok(jsonResult).build();
    }
}
