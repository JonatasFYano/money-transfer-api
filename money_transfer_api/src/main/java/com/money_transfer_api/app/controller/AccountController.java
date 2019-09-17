package com.money_transfer_api.app.controller;

import javax.ws.rs.*;

/**
 * Account Controller 
 */
@Path("/account")
public class AccountController {

    @POST
    @Path("/create")
    public String createAccount(String hello) {
        return hello;
    }
}
