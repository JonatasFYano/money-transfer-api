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
import com.money_transfer_api.app.model.MovementAccountModel;

import static org.junit.Assert.assertTrue;


  //Configuration is made in TestService


public class TestAccountService extends TestService{


    /*
    Positive Case = AccountService
    Scenario: test get All accounts
              return 200 OK
     */
    @Test
    public void testGetAllAccounts() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        System.out.println(jsonString);
        AccountModel[] accounts = mapper.readValue(jsonString, AccountModel[].class);
        assertTrue(accounts[0].getUserName().equals("jonatas"));
    }

        /*
    Positive Case = AccountService
    Scenario: test get account by username
              return 200 OK
     */
    @Test
    public void testGetAccountByUsername() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/jonatas").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        System.out.println(jsonString);
        AccountModel accounts = mapper.readValue(jsonString, AccountModel.class);
        assertTrue(accounts.getUserName().equals("jonatas"));
    }

        /*
    Positive Case = AccountService
    Scenario: test create new account
              return 200 OK
     */
    @Test
    public void testCreateAccount() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/create").build();
        BigDecimal totalBalance = new BigDecimal(10).setScale(4, RoundingMode.HALF_EVEN);
        AccountModel account = new AccountModel("test2", totalBalance);

        String jsonInString = mapper.writeValueAsString(account);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
    }
}