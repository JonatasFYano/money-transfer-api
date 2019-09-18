package com.money_transfer_api.app.controller;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.money_transfer_api.app.model.TransactionModel;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.service.TransactionService;
import com.money_transfer_api.app.exception.MessageException;

import java.util.List;


/**
 * Account Controller 
 */
@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

    TransactionService transactionService = new TransactionService();


    @PUT
    @Path("/")
    public List<AccountModel> transferAmount(TransactionModel transaction) throws MessageException {
        List<AccountModel> accounts = transactionService.transferAmount(transaction);
        return accounts;
    }
}
