package com.money_transfer_api.app.integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

import com.money_transfer_api.app.integration.TestService;
import com.money_transfer_api.app.model.AccountModel;
import com.money_transfer_api.app.model.TransactionModel;
import com.money_transfer_api.app.model.MovementAccountModel;

import static org.junit.Assert.assertTrue;


  //Configuration is made in TestService


public class TestTransactionService extends TestService{


       /*
    Positive Case = TransactionService
    Scenario: test withdraw from account
              return 200 OK
     */
    @Test
    public void testWithdrawAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/jonatas/withdraw").build();
        BigDecimal totalBalance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
        MovementAccountModel movementAccountModel = new MovementAccountModel(totalBalance);

        String jsonInString = mapper.writeValueAsString(movementAccountModel);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        AccountModel aAfterCreation = mapper.readValue(jsonString, AccountModel.class);
        assertTrue(aAfterCreation.getUserName().equals("jonatas"));
        assertTrue(aAfterCreation.getTotalBalance().equals(new BigDecimal(490.7000).setScale(4, RoundingMode.HALF_EVEN)));
    }

       /*
    Fail Case = TransactionService
    Scenario: test withdraw from account without fund
              return 200 OK
     */
    @Test
    public void testWithdrawNonFundAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/jonatas/withdraw").build();
        BigDecimal totalBalance = new BigDecimal(1000).setScale(4, RoundingMode.HALF_EVEN);
        MovementAccountModel movementAccountModel = new MovementAccountModel(totalBalance);

        String jsonInString = mapper.writeValueAsString(movementAccountModel);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());

        assertTrue(statusCode == 500);
    }

    /*
    Positive Case = TransactionService
    Scenario: test transaction from accounts
              return 200 OK
     */
    @Test
    public void testTransactionAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transaction").build();
        BigDecimal totalBalance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
        TransactionModel transaction = new TransactionModel(totalBalance, "jonatas", "adriano");

        String jsonInString = mapper.writeValueAsString(transaction);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        AccountModel[] accounts = mapper.readValue(jsonString, AccountModel[].class);
        assertTrue(accounts[0].getUserName().equals("jonatas"));
        assertTrue(accounts[1].getUserName().equals("adriano"));
        assertTrue(accounts[0].getTotalBalance().equals(new BigDecimal(480.7000).setScale(4, RoundingMode.HALF_EVEN)));
        assertTrue(accounts[1].getTotalBalance().equals(new BigDecimal(310.0000).setScale(4, RoundingMode.HALF_EVEN)));
    }


    /*
    Fail Case = TransactionService
    Scenario: test transaction from accounts without fund
              return 200 OK
     */
    @Test
    public void testTransactionWithoutFundAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/transaction").build();
        BigDecimal totalBalance = new BigDecimal(1000).setScale(4, RoundingMode.HALF_EVEN);
        TransactionModel transaction = new TransactionModel(totalBalance, "jonatas", "adriano");

        String jsonInString = mapper.writeValueAsString(transaction);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 400);
    }


}