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

    public AccountModel() {
    }

    public AccountModel(String userName, BigDecimal totalBalance) {
        this.userName = userName;
        this.totalBalance = totalBalance;
    }

    public AccountModel(long accountId, String userName, BigDecimal totalBalance) {
        this.accountId = accountId;
        this.userName = userName;
        this.totalBalance = totalBalance;
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


}
