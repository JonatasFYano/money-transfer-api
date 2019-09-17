package com.money_transfer_api.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AccountModel {

    @JsonIgnore
    private long accountId;

    @JsonProperty(required = true)
    private String userName;

    @JsonProperty(required = true)
    private BigDecimal totalBalance;

    @JsonProperty(required = true)
    private String currency;

    public void Account() {
    }

    public void Account(String userName, BigDecimal totalBalance, String currency) {
        this.userName = userName;
        this.totalBalance = totalBalance;
        this.currency = currency;
    }

    public void Account(long accountId, String userName, BigDecimal totalBalance, String currency) {
        this.accountId = accountId;
        this.userName = userName;
        this.totalBalance = totalBalance;
        this.currency = currency;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public String getCurrency() {
        return currency;
    }
}
