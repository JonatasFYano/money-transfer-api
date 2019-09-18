package com.money_transfer_api.app.service;

import org.apache.http.HttpResponse;
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

import com.money_transfer_api.app.service.TestService;
import com.money_transfer_api.app.model.AccountModel;

import static org.junit.Assert.assertTrue;


  //Configuration is made in TestService


public class TestAccountService extends TestService{


    /*
    Positive Case = AccountService
    Scenario: test get All users
              return 200 OK
     */
    @Test
    public void testGetAccountByUserName() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/account/").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        System.out.println(jsonString);
        AccountModel[] accounts = mapper.readValue(jsonString, AccountModel[].class);
        assertTrue(accounts[0].getUserName().equals("yangluo"));
    }

}
