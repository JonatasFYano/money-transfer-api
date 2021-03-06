package com.money_transfer_api.app.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import com.money_transfer_api.app.model.TransactionModel;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.service.TransactionService;
import com.money_transfer_api.app.exception.MessageException;


/**
 * Account Controller 
 */
@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    TransactionService transactionService = new TransactionService();
    public ObjectMapper mapper = new ObjectMapper();


    @PUT
    @Path("/")
    public Response transferAmount(TransactionModel transaction) throws MessageException, IOException{
        try{
            List<AccountModel> accounts = transactionService.transferAmount(transaction);
            if(accounts == null || accounts.isEmpty()){
                return Response.noContent().build();
            }
            else{
                String jsonResult = mapper.writeValueAsString(accounts);
                return Response.ok(jsonResult).build();
            }
        }catch(Exception e){
            return Response.status(Status.BAD_REQUEST).build();
        }
    }
}
